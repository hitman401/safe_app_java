package net.maidsafe.api;

import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class PublicSignKey extends NativeHandle {
    private NativeHandle appHandle;

    public PublicSignKey(NativeHandle appHandle, long handle) {
        super(handle, (signKey) -> {
            NativeBindings.signPubKeyFree(appHandle.toLong(), signKey, (result) -> {
            });
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
