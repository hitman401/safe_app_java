package net.maidsafe.api;

import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class Crypto extends BaseApi {

    public Crypto(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<NativeHandle> getAppPublicSignKey() {
        CompletableFuture<NativeHandle> future = new CompletableFuture<>();
        NativeBindings.appPubSignKey(appHandle.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new NativeHandle(handle, (key) -> {
                NativeBindings.signPubKeyFree(appHandle.toLong(), key, (freeResult) -> {

                });
            }));
        });
        return future;
    }

    public CompletableFuture<SignKeyPair> generateSignKeyPair() {
        CompletableFuture<SignKeyPair> future = new CompletableFuture<>();
        NativeBindings.signGenerateKeyPair(appHandle.toLong(), (result, pubSignKeyHandle, secSignKeyHandle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new SignKeyPair(new PublicSignKey(appHandle, pubSignKeyHandle), new SecretSignKey(appHandle, secSignKeyHandle)));
        });
        return future;
    }

    public CompletableFuture<PublicSignKey> getPublicSignKey(byte[] key) {
        CompletableFuture<PublicSignKey> future = new CompletableFuture<>();
        NativeBindings.signPubKeyNew(appHandle.toLong(), key, (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new PublicSignKey(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<SecretSignKey> getSecretSignKey(byte[] key) {
        CompletableFuture<SecretSignKey> future = new CompletableFuture<>();
        NativeBindings.signSecKeyNew(appHandle.toLong(), key, (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new SecretSignKey(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<EncryptKeyPair> generateEncryptKeyPair() {
        CompletableFuture<EncryptKeyPair> future = new CompletableFuture<>();
        NativeBindings.encGenerateKeyPair(appHandle.toLong(), (result, pubEncHandle, secEncHandle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new EncryptKeyPair(new PublicEncryptKey(appHandle, pubEncHandle), new SecretEncryptKey(appHandle, secEncHandle)));
        });
        return future;
    }

    public CompletableFuture<PublicEncryptKey> getAppPublicEncryptKey() {
        CompletableFuture<PublicEncryptKey> future = new CompletableFuture<>();
        NativeBindings.appPubEncKey(appHandle.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new PublicEncryptKey(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<PublicEncryptKey> getPublicEncryptKey(byte[] key) {
        CompletableFuture<PublicEncryptKey> future = new CompletableFuture<>();
        NativeBindings.encPubKeyNew(appHandle.toLong(), key, (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new PublicEncryptKey(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<SecretEncryptKey> getSecretEncryptKey(byte[] key) {
        CompletableFuture<SecretEncryptKey> future = new CompletableFuture<>();
        NativeBindings.encSecretKeyNew(appHandle.toLong(), key, (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new SecretEncryptKey(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<byte[]> sign(SecretSignKey secretSignKey, byte[] data) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.sign(appHandle.toLong(), data, secretSignKey.toLong(), (result, signedData) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(signedData);
        });
        return future;
    }

    public CompletableFuture<byte[]> verify(PublicSignKey publicSignKey, byte[] signedData) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.verify(appHandle.toLong(), signedData, publicSignKey.toLong(), (result, verifiedData) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(verifiedData);
        });
        return future;
    }

    public CompletableFuture<byte[]> encrypt(PublicEncryptKey recipientPublicEncryptKey, SecretEncryptKey senderSecretEncryptKey, byte[] data) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.encrypt(appHandle.toLong(), data, recipientPublicEncryptKey.toLong(), senderSecretEncryptKey.toLong(), (result, cipherText) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(cipherText);
        });
        return future;
    }

    public CompletableFuture<byte[]> decrypt(PublicEncryptKey senderPublicEncryptKey, SecretEncryptKey recipientSecretEncryptKey, byte[] cipherText) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.decrypt(appHandle.toLong(), cipherText, senderPublicEncryptKey.toLong(), recipientSecretEncryptKey.toLong(), (result, plainData) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(plainData);
        });
        return future;
    }

    public CompletableFuture<byte[]> encryptSealedBox(PublicEncryptKey recipientPublicEncryptKey, byte[] data) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.encryptSealedBox(appHandle.toLong(), data, recipientPublicEncryptKey.toLong(), (result, cipherText) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(cipherText);
        });
        return future;
    }

    public CompletableFuture<byte[]> decryptSealedBox(SecretEncryptKey senderSecretEncryptKey, byte[] cipherText) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.encryptSealedBox(appHandle.toLong(), cipherText, senderSecretEncryptKey.toLong(), (result, plainText) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(plainText);
        });
        return future;
    }

}
