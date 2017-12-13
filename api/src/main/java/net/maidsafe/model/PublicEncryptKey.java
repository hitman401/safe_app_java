package net.maidsafe.model;

import net.maidsafe.ffi.app.NativeBindings;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class PublicEncryptKey extends NativeHandle {
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
