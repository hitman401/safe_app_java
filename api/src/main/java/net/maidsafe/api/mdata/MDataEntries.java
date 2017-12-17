package net.maidsafe.api.mdata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.model.MDataEntry;
import net.maidsafe.model.MDataValue;
import net.maidsafe.safe_app.CallbackByteArrayLenByteArrayLenLong;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MDataEntries extends BaseApi {
    private NativeHandle entriesHandle;

    public MDataEntries(NativeHandle appHandle, NativeHandle entriesHandle) {
        super(appHandle);
        this.entriesHandle = entriesHandle;
    }

    public NativeHandle getEntriesHandle() {
        return entriesHandle;
    }

    public CompletableFuture<Void> insert(byte[] key, byte[] value) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.mdataEntriesInsert(appHandle.toLong(), entriesHandle.toLong(), key, value, result -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Long> length() {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.mdataEntriesLen(appHandle.toLong(), entriesHandle.toLong(), (result, len) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(len);
        });
        return future;
    }

    public CompletableFuture<MDataValue> getValue(byte[] key) {
        CompletableFuture<MDataValue> future = new CompletableFuture<>();
        NativeBindings.mdataEntriesGet(appHandle.toLong(), entriesHandle.toLong(), key, (result, value, version) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new MDataValue(value, version));
        });
        return future;
    }

    public CompletableFuture<List<MDataEntry>> listEntries() {
        CompletableFuture<List<MDataEntry>> future = new CompletableFuture<>();
        List<MDataEntry> entries = new ArrayList<>();
        CallbackByteArrayLenByteArrayLenLong forEachCallback = (key, value, version) -> {
            entries.add(new MDataEntry(key, value, version));
        };
        NativeBindings.mdataEntriesForEach(appHandle.toLong(), entriesHandle.toLong(), forEachCallback, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(entries);
        });
        return future;
    }
}
