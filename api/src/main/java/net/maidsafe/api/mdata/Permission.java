package net.maidsafe.api.mdata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.api.PublicSignKey;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.safe_app.PermissionSet;
import net.maidsafe.safe_app.UserPermissionSet;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Permission extends BaseApi {
    private NativeHandle permissionHandle;
    public Permission(NativeHandle appHandle, NativeHandle permissionHandle) {
        super(appHandle);
        this.permissionHandle = permissionHandle;
    }

    public NativeHandle getNativeHandle() {
        return permissionHandle;
    }

    public CompletableFuture<Long> getLength() {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.mdataPermissionsLen(appHandle.toLong(), permissionHandle.toLong(), (result, len) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(len);
        });
        return future;
    }

    public CompletableFuture<PermissionSet> getPermissionForUser(PublicSignKey signKey) {
        CompletableFuture<PermissionSet> future = new CompletableFuture<>();
        NativeBindings.mdataPermissionsGet(appHandle.toLong(), permissionHandle.toLong(), signKey.toLong(), (result, permsSet) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(permsSet);
        });
        return future;
    }

    public CompletableFuture<List<UserPermissionSet>> listAll() {
        CompletableFuture<List<UserPermissionSet>> future = new CompletableFuture<>();
        NativeBindings.mdataListPermissionSets(appHandle.toLong(), permissionHandle.toLong(), (result, permsArray) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(Arrays.asList(permsArray));
        });
        return future;
    }

    public CompletableFuture<Void> insert(PublicSignKey publicSignKey, PermissionSet permissionSet) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataPermissionsInsert(appHandle.toLong(), permissionHandle.toLong(), publicSignKey.toLong(), permissionSet, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

}
