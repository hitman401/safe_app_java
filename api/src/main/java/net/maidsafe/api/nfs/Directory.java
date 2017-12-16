package net.maidsafe.api.nfs;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.model.NFSFileMetadata;
import net.maidsafe.model.OpenMode;
import net.maidsafe.safe_app.File;
import net.maidsafe.safe_app.MDataInfo;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class Directory extends BaseApi {

    private final long OPEN_MODE_READ = 4;

    public Directory(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<NFSFileMetadata> getFileMetadata(MDataInfo parentInfo, String fileName) {
        CompletableFuture<NFSFileMetadata> future = new CompletableFuture<>();
        NativeBindings.dirFetchFile(appHandle.toLong(), parentInfo, fileName, (result, ffiFile, version) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new NFSFileMetadata(ffiFile, version));
        });
        return future;
    }

    public CompletableFuture<Void> insertFileMetadata(MDataInfo parentInfo, String fileName, NFSFileMetadata file) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.dirInsertFile(appHandle.toLong(), parentInfo, fileName, (File) file, (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Void> updateFileMetadata(MDataInfo parentInfo, String fileName, NFSFileMetadata file) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.dirUpdateFile(appHandle.toLong(), parentInfo, fileName, (net.maidsafe.safe_app.File) file, file.getVersion(), (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Void> deleteFileMetadata(MDataInfo parentInfo, String fileName, NFSFileMetadata file) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.dirDeleteFile(appHandle.toLong(), parentInfo, fileName, file.getVersion(), (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<FileWriter> getFileWriter(MDataInfo parentInfo, File file, OpenMode openMode) {
        CompletableFuture<FileWriter> future = new CompletableFuture<>();
        NativeBindings.fileOpen(appHandle.toLong(), parentInfo, file, openMode.getValue(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new FileWriter(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<FileReader> getFileReader(MDataInfo parentInfo, File file) {
        CompletableFuture<FileReader> future = new CompletableFuture<>();
        NativeBindings.fileOpen(appHandle.toLong(), parentInfo, file, OPEN_MODE_READ, (result, handle) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new FileReader(appHandle, handle));
        });
        return future;
    }

}
