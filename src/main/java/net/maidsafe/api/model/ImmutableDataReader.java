package net.maidsafe.api.model;

import java.util.concurrent.CompletableFuture;

import net.maidsafe.binding.BindingFactory;
import net.maidsafe.binding.ImmutableDataBinding;
import net.maidsafe.binding.model.FfiCallback.CallbackForData;
import net.maidsafe.binding.model.FfiCallback.HandleCallback;
import net.maidsafe.binding.model.FfiResult;
import net.maidsafe.binding.model.FfiCallback.ResultCallback;
import net.maidsafe.utils.CallbackMapper;

import com.sun.jna.Pointer;

public class ImmutableDataReader {

	private final Pointer appHandle;
	private final long handle;
	private final ImmutableDataBinding lib;

	public ImmutableDataReader(Pointer appHandle, long handle) {
		this.appHandle = appHandle;
		this.handle = handle;
		lib = BindingFactory.getInstance().getImmutableData();
	}

	public CompletableFuture<Long> getSize() {
		final CompletableFuture<Long> future = new CompletableFuture<>();
		HandleCallback cb = new HandleCallback() {

			@Override
			public void onResponse(Pointer userData, FfiResult.ByVal result,
					long handle) {
				System.out.println("Reader size callback invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(handle);
			}
		};
		CallbackMapper.getInstance().add(cb);
		lib.idata_size(appHandle, handle, Pointer.NULL, cb);
		return future;
	}

	public CompletableFuture<byte[]> read(long offset, long lengthToRead) {
		final CompletableFuture<byte[]> future = new CompletableFuture<>();
		CallbackForData cb = new CallbackForData() {

			@Override
			public void onResponse(Pointer userData,
					FfiResult.ByVal result, Pointer data, long dataLen) {
				System.out.println("Reader read callback invoked");
				if (result.isError()) {
					future.completeExceptionally(new Exception(result
							.errorMessage()));
					return;
				}
				future.complete(data.getByteArray(0, (int) dataLen));
			}
		};
		CallbackMapper.getInstance().add(cb);
		lib.idata_read_from_self_encryptor(appHandle, handle, offset,
				lengthToRead, Pointer.NULL, cb);
		return future;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
//		lib.idata_self_encryptor_reader_free(appHandle, handle, Pointer.NULL,
//				new ResultCallback() {
//
//					@Override
//					public void onResponse(Pointer userData,
//							FfiResult.ByVal result) {
//					}
//				});
	}

}
