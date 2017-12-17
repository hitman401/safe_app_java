package net.maidsafe.api.mdata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class MDataEntryAction extends BaseApi {

    private NativeHandle actionHandle;

    public MDataEntryAction(NativeHandle appHandle, NativeHandle actionHandle) {
        super(appHandle);
        this.actionHandle = actionHandle;
    }

    public CompletableFuture<Void> insert(byte[] key, byte[] value) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataEntryActionsInsert(appHandle.toLong(), actionHandle.toLong(), key, value, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Void> update(byte[] key, byte[] value, long version) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataEntryActionsUpdate(appHandle.toLong(), actionHandle.toLong(), key, value, version, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Void> delete(byte[] key, long version) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataEntryActionsDelete(appHandle.toLong(), actionHandle.toLong(), key, version, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }
}
