package net.maidsafe.api;

import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;


public class ImmutableDataReader extends BaseApi {

    private NativeHandle readerHandle;
    public ImmutableDataReader(NativeHandle appHandle, long writerHandle) {
        super(appHandle);
        this.readerHandle = new NativeHandle(writerHandle, (handle) -> {
            NativeBindings.idataSelfEncryptorReaderFree(appHandle.toLong(), handle, (result -> {
            }));
        });
    }

    public CompletableFuture<byte[]> read(long position, long length) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.idataReadFromSelfEncryptor(appHandle.toLong(), readerHandle.toLong(), position, length, (result, data) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
            }
            future.complete(data);
        });
        return future;
    }

    public CompletableFuture<Long> getSize() {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.idataSize(appHandle.toLong(), readerHandle.toLong(), (result, size) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
            }
            future.complete(size);
        });
        return future;
    }

    public static class PublicEncryptKey extends NativeHandle {
        private NativeHandle appHandle;

        public PublicEncryptKey(NativeHandle appHandle, long handle) {
            super(handle, (encKey) -> {
                NativeBindings.encPubKeyFree(appHandle.toLong(), encKey, (result) -> {});
            });
            this.appHandle = appHandle;
        }

        public CompletableFuture<byte[]> getKey() {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            NativeBindings.encPubKeyGet(appHandle.toLong(), toLong(), (result, key) -> {
                if (result.getErrorCode() != 0) {
                    future.completeExceptionally(Helper.ffiResultToException(result));
                    return;
                }
                future.complete(key);
            });
            return future;
        }
    }

    public static class EncryptKeyPair {

        private SecretEncryptKey secretEncryptKey;
        private PublicEncryptKey publicEncryptKey;

        public EncryptKeyPair(PublicEncryptKey publicEncryptKey, SecretEncryptKey secretEncryptKey) {
            this.secretEncryptKey = secretEncryptKey;
            this.publicEncryptKey = publicEncryptKey;
        }

        public PublicEncryptKey getPublicEncryptKey() {
            return publicEncryptKey;
        }

        public SecretEncryptKey getSecretEncryptKey() {
            return secretEncryptKey;
        }
    }

    public static class SignKeyPair {
        private ImmutableDataWriter.PublicSignKey publicSignKey;
        private SecretSignKey secretSignKey;

        public SignKeyPair(ImmutableDataWriter.PublicSignKey publicSignKey, SecretSignKey secretSignKey) {
            this.publicSignKey = publicSignKey;
            this.secretSignKey = secretSignKey;
        }

        public ImmutableDataWriter.PublicSignKey getPublicSignKey() {
            return publicSignKey;
        }

        public SecretSignKey getSecretSignKey() {
            return secretSignKey;
        }
    }
}

