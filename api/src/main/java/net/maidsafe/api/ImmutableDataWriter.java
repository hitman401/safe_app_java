package net.maidsafe.api;

import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class ImmutableDataWriter extends BaseApi {

    private NativeHandle writeHandle;
    public ImmutableDataWriter(NativeHandle appHandle, long writerHandle) {
        super(appHandle);
        this.writeHandle = new NativeHandle(writerHandle, (handle) -> {
            NativeBindings.idataSelfEncryptorWriterFree(appHandle.toLong(), handle, (result -> {
            }));
        });
    }

    public CompletableFuture<Void> write(byte[] data) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.idataWriteToSelfEncryptor(appHandle.toLong(), writeHandle.toLong(), data, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<byte[]> close(CipherOptHandle handle) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.idataCloseSelfEncryptor(appHandle.toLong(), writeHandle.toLong(), handle.toLong(), (result, name) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
            }
            future.complete(name);
        });
        return future;
    }

    public static class PublicSignKey extends NativeHandle {
        private NativeHandle appHandle;

        public PublicSignKey(NativeHandle appHandle, long handle) {
            super(handle, (signKey) -> {
                NativeBindings.signPubKeyFree(appHandle.toLong(), signKey, (result) -> {});
            });
            this.appHandle = appHandle;
        }

        public CompletableFuture<byte[]> getKey() {
            CompletableFuture<byte[]> future = new CompletableFuture<>();
            NativeBindings.signPubKeyGet(appHandle.toLong(), toLong(), (result, key) -> {
                if (result.getErrorCode() != 0) {
                    future.completeExceptionally(Helper.ffiResultToException(result));
                    return;
                }
                future.complete(key);
            });
            return future;
        }
    }
}
