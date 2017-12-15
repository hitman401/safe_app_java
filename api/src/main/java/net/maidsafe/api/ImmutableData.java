package net.maidsafe.api;

import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class ImmutableData extends BaseApi {

    public ImmutableData(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<ImmutableDataWriter> getWriter() {
        CompletableFuture<ImmutableDataWriter> future = new CompletableFuture<>();
        NativeBindings.idataNewSelfEncryptor(appHandle.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new ImmutableDataWriter(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<ImmutableDataReader> getReader(byte[] name) {
        CompletableFuture<ImmutableDataReader> future = new CompletableFuture<>();
        NativeBindings.idataFetchSelfEncryptor(appHandle.toLong(),name,  (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new ImmutableDataReader(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<Long> getSerialisedSize(byte[] name) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.idataSerialisedSize(appHandle.toLong(),name, (result, size) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(size);
        });
        return future;
    }
}
