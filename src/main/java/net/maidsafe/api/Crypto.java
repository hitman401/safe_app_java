package net.maidsafe.api;

import java.util.concurrent.CompletableFuture;

import com.sun.jna.Pointer;

import net.maidsafe.api.model.App;
import net.maidsafe.api.model.EncryptKeyPair;
import net.maidsafe.api.model.PublicEncryptKey;
import net.maidsafe.api.model.PublicSignKey;
import net.maidsafe.api.model.SecretEncryptKey;
import net.maidsafe.binding.BindingFactory;
import net.maidsafe.binding.CryptoBinding;
import net.maidsafe.binding.model.FfiCallback;
import net.maidsafe.binding.model.FfiResult;
import net.maidsafe.binding.model.FfiCallback.CallbackForData;
import net.maidsafe.binding.model.FfiCallback.HandleCallback;
import net.maidsafe.binding.model.FfiCallback.TwoHandleCallback;
import net.maidsafe.utils.CallbackMapper;
import net.maidsafe.utils.FfiConstant;

public class Crypto {

	private App app;
	private CryptoBinding lib = BindingFactory.getInstance().getCrypto();

	public Crypto(App app) {
		this.app = app;
	}
	
	private HandleCallback getHandleCallback(final CompletableFuture<PublicSignKey> future) {
		HandleCallback cb = new HandleCallback() {
			@Override
			public void onResponse(Pointer userData, FfiResult.ByVal result,
					long handle) {
				System.out.println("Handle CB invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(new PublicSignKey(app.getAppHandle(), handle));
			}
		};
		CallbackMapper.getInstance().add(cb);
		return cb;
	}
	
	private HandleCallback getHandleCallbackEnc(final CompletableFuture<PublicEncryptKey> future) {
		HandleCallback cb = new HandleCallback() {
			@Override
			public void onResponse(Pointer userData, FfiResult.ByVal result,
					long handle) {
				System.out.println("Handle CB invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(new PublicEncryptKey(app.getAppHandle(), handle));
			}
		};
		CallbackMapper.getInstance().add(cb);
		return cb;
	}
	private HandleCallback getHandleCallbackSec(final CompletableFuture<SecretEncryptKey> future) {
		HandleCallback cb = new HandleCallback() {
			@Override
			public void onResponse(Pointer userData, FfiResult.ByVal result,
					long handle) {
				System.out.println("Handle CB invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(new SecretEncryptKey(app.getAppHandle(), handle));
			}
		};
		CallbackMapper.getInstance().add(cb);
		return cb;
	}

	public CompletableFuture<PublicSignKey> getAppPublicSignKey() {
		final CompletableFuture<PublicSignKey> future;
		future = new CompletableFuture<>();
		final HandleCallback cb = getHandleCallback(future);
		lib.app_pub_sign_key(app.getAppHandle(), Pointer.NULL, cb);
		return future;
	}

	public CompletableFuture<PublicSignKey> getPublicSignKey(byte[] raw) {
		final CompletableFuture<PublicSignKey> future;
		future = new CompletableFuture<>();
		if (raw == null || raw.length != FfiConstant.SIGN_PUBLICKEYBYTES) {
			future.completeExceptionally(new Exception(
					"Invalid argument - Invalid size or null"));
			return future;
		}
		final HandleCallback cb = getHandleCallback(future);
		lib.sign_key_new(app.getAppHandle(), raw, Pointer.NULL,
				cb);
		return future;
	}

	public CompletableFuture<PublicEncryptKey> getAppPublicEncryptKey() {
		final CompletableFuture<PublicEncryptKey> future;
		future = new CompletableFuture<>();
		final HandleCallback cb = getHandleCallbackEnc(future);
		lib.app_pub_enc_key(app.getAppHandle(), Pointer.NULL,
				cb);
		return future;
	}

	public CompletableFuture<PublicEncryptKey> getPublicEncryptKey(byte[] raw) {
		final CompletableFuture<PublicEncryptKey> future;
		future = new CompletableFuture<>();
		if (raw == null || raw.length != FfiConstant.BOX_PUBLICKEYBYTES) {
			future.completeExceptionally(new Exception(
					"Invalid size or null argument"));
			return future;
		}
		final HandleCallback cb = getHandleCallbackEnc(future);
		lib.enc_pub_key_new(app.getAppHandle(), raw, Pointer.NULL,
				cb);
		return future;
	}

	public CompletableFuture<SecretEncryptKey> getSecretEncryptKey(byte[] raw) {
		final CompletableFuture<SecretEncryptKey> future;
		future = new CompletableFuture<>();
		final HandleCallback cb = getHandleCallbackSec(future);
		lib.enc_secret_key_new(app.getAppHandle(), raw, Pointer.NULL,
				cb);
		return future;
	}

	// generate keys
	public CompletableFuture<EncryptKeyPair> generateEncryptKeyPair() {
		final CompletableFuture<EncryptKeyPair> future;
		future = new CompletableFuture<EncryptKeyPair>();
		final TwoHandleCallback cb = new FfiCallback.TwoHandleCallback() {

			@Override
			public void onResponse(Pointer userData,
					FfiResult.ByVal result, long publicKey,
					long secretKey) {
				System.out.println("CB invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(new EncryptKeyPair(app.getAppHandle(),
						new SecretEncryptKey(app.getAppHandle(),
								secretKey), new PublicEncryptKey(app
								.getAppHandle(), publicKey)));
			}
		};
		CallbackMapper.getInstance().add(cb);
		lib.enc_generate_key_pair(app.getAppHandle(), Pointer.NULL,
				cb);
		return future;
	}

	public CompletableFuture<byte[]> hashSHA3(byte[] data) {
		final CompletableFuture<byte[]> future;
		future = new CompletableFuture<byte[]>();

		final CallbackForData cb = new CallbackForData() {

			@Override
			public void onResponse(Pointer userData, FfiResult.ByVal result,
					Pointer data, long dataLen) {
				System.out.println("CB invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(data.getByteArray(0, (int) dataLen));
			}
		};

		CallbackMapper.getInstance().add(cb);
		lib.sha3_hash(data, data.length, Pointer.NULL, cb);

		return future;
	}

}