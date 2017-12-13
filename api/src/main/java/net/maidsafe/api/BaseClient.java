package net.maidsafe.api;

import net.maidsafe.ffi.app.*;
import net.maidsafe.listener.OnDisconnected;
import net.maidsafe.model.*;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class BaseClient {

    private CallbackResultIntString handleRequestCallback(CompletableFuture<Request> future) {
        return (result, reqId, uri) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new Request(uri, reqId));
        };
    }

    public CompletableFuture<Request> getAuthRequest(AuthReq req) {
        CompletableFuture<Request> future = new CompletableFuture<>();
        NativeBindings.encodeAuthReq(req, handleRequestCallback(future));
        return future;
    }

    public CompletableFuture<Request> getContainerRequest(ContainersReq req) {
        CompletableFuture<Request> future = new CompletableFuture<>();
        NativeBindings.encodeContainersReq(req, handleRequestCallback(future));
        return future;
    }

    public CompletableFuture<Request> getShareMutableDataRequest(ShareMDataReq req) {
        CompletableFuture<Request> future = new CompletableFuture<>();
        NativeBindings.encodeShareMdataReq(req, handleRequestCallback(future));
        return future;
    }

    public CompletableFuture<Request> getUnregisteredSessionRequest(App app) {
        CompletableFuture<Request> future = new CompletableFuture<>();
        byte[] id = app.getId().getBytes();
        NativeBindings.encodeUnregisteredReq(id, handleRequestCallback(future));
        return future;
    }

    public CompletableFuture<DecodeResult> decodeResponse(String uri) {
        CompletableFuture<DecodeResult> future = new CompletableFuture<>();

        CallbackIntAuthGranted onAuthGranted = (reqId, authGranted) -> {
            future.complete(new AuthResponse(reqId, authGranted));
        };

        CallbackIntByteArrayLen onUnregistered = (reqId, serialisedCfgPtr) -> {
            future.complete(new UnregisteredClientResponse(reqId, serialisedCfgPtr));
        };

        CallbackInt onContainerCb = (reqId) -> {
            future.complete(new ContainerResponse(reqId));
        };

        CallbackInt onShareMdCb = (reqId) -> {
            future.complete(new ShareMutableDataResponse(reqId));
        };

        CallbackVoid onRevoked = () -> {
            future.complete(new RevokedResponse());
        };

        CallbackResultInt onErrorCb = (result, reqId) -> {
            future.complete(new DecodeError(reqId, result));
        };

        NativeBindings.decodeIpcMsg(uri, onAuthGranted, onUnregistered, onContainerCb, onShareMdCb, onRevoked, onErrorCb);
        return  future;
    }

    public CompletableFuture<Session> getUnregisteredSession(UnregisteredClientResponse response, OnDisconnected onDisconnected) {
        return getUnregisteredSession(response.getBootstrapConfig(), onDisconnected);
    }

    public CompletableFuture<Session> getUnregisteredSession(byte[] bootStrapConfig, OnDisconnected onDisconnected) {
        CompletableFuture<Session> future = new CompletableFuture<>();
        CallbackVoid onDisconnectCb = () -> {
            if (onDisconnected != null) {
                onDisconnected.disconnected();
            }
        };
        CallbackResultApp callback = (result, app) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(new Session(new NativeHandle(app, (handle) -> {
                NativeBindings.appFree(handle);
            }), onDisconnectCb));
        };
        NativeBindings.appUnregistered(bootStrapConfig, onDisconnectCb, callback);
        return future;
    }
}
