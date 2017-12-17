package net.maidsafe.api.mdata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.api.PublicSignKey;
import net.maidsafe.safe_app.MDataInfo;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.safe_app.PermissionSet;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class MDataPermission extends Permission {
    private MDataInfo mDataInfo;

    public MDataPermission(NativeHandle appHandle, NativeHandle permissionsHandle, MDataInfo mDataInfo) {
        super(appHandle, permissionsHandle);
        this.mDataInfo = mDataInfo;
    }

    public CompletableFuture<PermissionSet> getPermissionForUser(PublicSignKey publicSignKey) {
        CompletableFuture<PermissionSet> future = new CompletableFuture<>();
        NativeBindings.mdataListUserPermissions(appHandle.toLong(), mDataInfo, publicSignKey.toLong(), (result, permissionSet) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(permissionSet);
        });
        return future;
    }

    public CompletableFuture<Void> setUserPermission(PublicSignKey publicSignKey, PermissionSet permissionSet, long version) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataSetUserPermissions(appHandle.toLong(), mDataInfo, publicSignKey.toLong(), permissionSet, version, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Void> deleteUserPermission(PublicSignKey publicSignKey, long version) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataDelUserPermissions(appHandle.toLong(), mDataInfo, publicSignKey.toLong(), version, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }
}
