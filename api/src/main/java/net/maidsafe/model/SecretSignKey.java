package net.maidsafe.model;

import net.maidsafe.ffi.app.NativeBindings;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class SecretSignKey extends NativeHandle {

    private NativeHandle appHandle;

    public SecretSignKey(NativeHandle appHandle, long handle) {
        super(handle, (signKey) -> {
            NativeBindings.signSecKeyFree(appHandle.toLong(), signKey, (result) -> {});
        });
        this.appHandle = appHandle;
    }

    public CompletableFuture<byte[]> getKey() {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.signSecKeyGet(appHandle.toLong(), toLong(), (result, key) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(key);
        });
        return future;
    }

}
