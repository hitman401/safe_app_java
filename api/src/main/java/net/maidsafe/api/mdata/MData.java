package net.maidsafe.api.mdata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.model.MDataValue;
import net.maidsafe.safe_app.*;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MData extends BaseApi {
    public MData(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<Void> put(MDataInfo mDataInfo, Permission permissions, MDataEntries entries) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataPut(appHandle.toLong(), mDataInfo, permissions.getNativeHandle().toLong(), entries.getEntriesHandle().toLong(), (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Long> getVersion(MDataInfo mDataInfo) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.mdataGetVersion(appHandle.toLong(), mDataInfo, (result, version) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(version);
        });
        return future;
    }

    public CompletableFuture<Long> getSerialisedSize(MDataInfo mDataInfo) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.mdataSerialisedSize(appHandle.toLong(), mDataInfo, (result, size) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(size);
        });
        return future;
    }

    public CompletableFuture<MDataValue> getValue(MDataInfo mDataInfo, byte[] key) {
        CompletableFuture<MDataValue> future = new CompletableFuture<>();
        NativeBindings.mdataGetValue(appHandle.toLong(), mDataInfo, key, (result, value, version) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new MDataValue(value, version));
        });
        return future;
    }

    public CompletableFuture<MDataEntries> getEntriesHandle(MDataInfo mDataInfo) {
        CompletableFuture<MDataEntries> future = new CompletableFuture<>();
        NativeBindings.mdataListEntries(appHandle.toLong(), mDataInfo, (result, entriesH) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            NativeHandle entriesHandle = new NativeHandle(entriesH, (handle) -> {
                NativeBindings.mdataEntriesFree(appHandle.toLong(), handle, (res) -> {});
            });
            future.complete(new MDataEntries(appHandle, entriesHandle));
        });
        return future;
    }

    public CompletableFuture<List<MDataKey>> getKeys(MDataInfo mDataInfo) {
        CompletableFuture<List<MDataKey>> future = new CompletableFuture<>();
        NativeBindings.mdataListKeys(appHandle.toLong(), mDataInfo, (result, keys) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(Arrays.asList(keys));
        });
        return future;
    }

    public CompletableFuture<List<MdataValue>> getValues(MDataInfo mDataInfo) {
        CompletableFuture<List<MdataValue>> future = new CompletableFuture<>();
        NativeBindings.mdataListValues(appHandle.toLong(), mDataInfo, (result, values) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(Arrays.asList(values));
        });
        return future;
    }

    public CompletableFuture<Void> mutateEntries(MDataInfo mDataInfo, NativeHandle actionHandle) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataMutateEntries(appHandle.toLong(), mDataInfo, actionHandle.toLong(), (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<MDataPermission> getPermission(MDataInfo mDataInfo) {
        CompletableFuture<MDataPermission> future = new CompletableFuture<>();
        NativeBindings.mdataListPermissions(appHandle.toLong(), mDataInfo, (result, permsHandle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            NativeHandle permissionHandle = new NativeHandle(permsHandle, (handle) -> {
                NativeBindings.mdataPermissionsFree(appHandle.toLong(), handle, res -> {});
            });
            future.complete(new MDataPermission(appHandle, permissionHandle, mDataInfo));
        });
        return future;
    }

    public CompletableFuture<MDataEntries> newEntries() {
        CompletableFuture<MDataEntries> future = new CompletableFuture<>();
        NativeBindings.mdataEntriesNew(appHandle.toLong(), (result, entriesH) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            NativeHandle entriesHandle = new NativeHandle(entriesH, handle -> {
                NativeBindings.mdataEntriesFree(appHandle.toLong(), handle, res -> {});
            });
            future.complete(new MDataEntries(appHandle, entriesHandle));
        });
        return future;
    }

    public CompletableFuture<MDataEntryAction> newEntryAction() {
        CompletableFuture<MDataEntryAction> future = new CompletableFuture<>();
        NativeBindings.mdataEntryActionsNew(appHandle.toLong(), (result, entriesH) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            NativeHandle entriesHandle = new NativeHandle(entriesH, handle -> {
                NativeBindings.mdataEntryActionsFree(appHandle.toLong(), handle, res -> {});
            });
            future.complete(new MDataEntryAction(appHandle, entriesHandle));
        });
        return future;
    }

    public CompletableFuture<Permission> newPermission() {
        CompletableFuture<Permission> future = new CompletableFuture<>();
        NativeBindings.mdataPermissionsNew(appHandle.toLong(), (result, permissionsHandle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            NativeHandle entriesHandle = new NativeHandle(permissionsHandle, handle -> {
                NativeBindings.mdataPermissionsFree(appHandle.toLong(), handle, res -> {});
            });
            future.complete(new Permission(appHandle, entriesHandle));
        });
        return future;
    }

    public CompletableFuture<byte[]> encodeMetadata(MetadataResponse metadataResponse) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.mdataEncodeMetadata(metadataResponse, (result, encodedMetadata) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(encodedMetadata);
        });
        return future;
    }
}
