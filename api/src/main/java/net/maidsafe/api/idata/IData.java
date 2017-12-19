package net.maidsafe.api.idata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class IData extends BaseApi {

    public IData(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<IDataWriter> getWriter() {
        CompletableFuture<IDataWriter> future = new CompletableFuture<>();
        NativeBindings.idataNewSelfEncryptor(appHandle.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new IDataWriter(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<IDataReader> getReader(byte[] name) {
        CompletableFuture<IDataReader> future = new CompletableFuture<>();
        NativeBindings.idataFetchSelfEncryptor(appHandle.toLong(), name, (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new IDataReader(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<Long> getSerialisedSize(byte[] name) {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.idataSerialisedSize(appHandle.toLong(), name, (result, size) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(size);
        });
        return future;
    }
}
