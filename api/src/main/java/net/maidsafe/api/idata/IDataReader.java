package net.maidsafe.api.idata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;


public class IDataReader extends BaseApi {

    private NativeHandle readerHandle;

    public IDataReader(NativeHandle appHandle, long writerHandle) {
        super(appHandle);
        this.readerHandle = new NativeHandle(writerHandle, (handle) -> {
            NativeBindings.idataSelfEncryptorReaderFree(appHandle.toLong(), handle, (result -> {
            }));
        });
    }

    public CompletableFuture<byte[]> read(long position, long length) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.idataReadFromSelfEncryptor(appHandle.toLong(), readerHandle.toLong(), position, length, (result, data) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
            }
            future.complete(data);
        });
        return future;
    }

    public CompletableFuture<Long> getSize() {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.idataSize(appHandle.toLong(), readerHandle.toLong(), (result, size) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
            }
            future.complete(size);
        });
        return future;
    }

}

