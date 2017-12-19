package net.maidsafe.api;

import net.maidsafe.safe_app.*;
import net.maidsafe.api.mdata.MData;
import net.maidsafe.utils.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Session {

    private NativeHandle appHandle;
    private CallbackVoid disconnectedCb;
    private Crypto crypto;
    private CipherOpt cipherOpt;
    private MData mData;

    public Session(NativeHandle appHandle, CallbackVoid disconnectedCb) {
        this.appHandle = appHandle;
        this.disconnectedCb = disconnectedCb;
        this.crypto = new Crypto(appHandle);
        this.cipherOpt = new CipherOpt(appHandle);
    }

    public Crypto getCrypto() {
        return crypto;
    }

    public CipherOpt getCipherOpt() {
        return cipherOpt;
    }

    public MData getmData() {
        return mData;
    }

    public CompletableFuture<AccountInfo> getAccountInfo() {
        CompletableFuture<AccountInfo> future = new CompletableFuture<>();
        NativeBindings.appAccountInfo(appHandle.toLong(), (result, accountInfo) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(accountInfo);
        });
        return future;
    }

    public CompletableFuture<Void> resetObjectCache() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.appResetObjectCache(appHandle.toLong(), (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<Void> refreshAccessInfo() {
        CompletableFuture<Void> future = new CompletableFuture<>();
        NativeBindings.accessContainerRefreshAccessInfo(appHandle.toLong(), (result) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(null);
        });
        return future;
    }

    public CompletableFuture<MDataInfo> getContainerMDataInfo(String containerName) {
        CompletableFuture<MDataInfo> future = new CompletableFuture<>();
        NativeBindings.accessContainerGetContainerMdataInfo(appHandle.toLong(), containerName, (result, mDataInfo) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(mDataInfo);
        });
        return future;
    }

    public CompletableFuture<List<ContainerPermission>> getContainerPermissions() {
        CompletableFuture<List<ContainerPermission>> future = new CompletableFuture<>();
        NativeBindings.accessContainerFetch(appHandle.toLong(), ((result, containerPerms) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(Arrays.asList(containerPerms));
        }));
        return future;
    }

}
