package net.maidsafe.api.nfs;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class FileReader extends BaseApi {
    private long fileContextHandle;

    public FileReader(NativeHandle appHandle, long fileContextHandle) {
        super(appHandle);
        this.fileContextHandle = fileContextHandle;
    }

    public CompletableFuture<Long> getSize() {
        CompletableFuture<Long> future = new CompletableFuture<>();
        NativeBindings.fileSize(appHandle.toLong(), fileContextHandle, (result, size) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(size);
        });
        return future;
    }

    public CompletableFuture<byte[]> read(long position, long length) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.fileRead(appHandle.toLong(), fileContextHandle, position, length, (result, data) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(data);
        });
        return future;
    }

}
