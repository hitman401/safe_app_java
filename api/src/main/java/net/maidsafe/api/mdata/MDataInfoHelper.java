package net.maidsafe.api.mdata;

import net.maidsafe.api.NativeHandle;
import net.maidsafe.safe_app.MDataInfo;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;
import net.maidsafe.utils.Helper;

import java.util.concurrent.CompletableFuture;

public class MDataInfoHelper extends BaseApi {

    public MDataInfoHelper(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<MDataInfo> getPrivateMData(byte[] name, long typeTag, byte[] secretKey, byte[] nonce) {
        CompletableFuture<MDataInfo> future = new CompletableFuture<>();
        NativeBindings.mdataInfoNewPrivate(name, typeTag, secretKey, nonce, (result, mdInfo) ->{
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(mdInfo);
        });
        return future;
    }

    public CompletableFuture<MDataInfo> getRandomPrivateMData(long typeTag) {
        CompletableFuture<MDataInfo> future = new CompletableFuture<>();
        NativeBindings.mdataInfoRandomPrivate(typeTag, (result, mdInfo) ->{
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(mdInfo);
        });
        return future;
    }

    public CompletableFuture<MDataInfo> getRandomPublicMData(long typeTag) {
        CompletableFuture<MDataInfo> future = new CompletableFuture<>();
        NativeBindings.mdataInfoRandomPublic(typeTag, (result, mdInfo) ->{
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(mdInfo);
        });
        return future;
    }

    public CompletableFuture<byte[]> encryptEntryKey(MDataInfo mDataInfo, byte[] key) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.mdataInfoEncryptEntryKey(mDataInfo, key, (result, encryptedKey) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(encryptedKey);
        });
        return future;
    }

    public CompletableFuture<byte[]> encryptEntryValue(MDataInfo mDataInfo, byte[] value) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.mdataInfoEncryptEntryValue(mDataInfo, value, (result, encryptedValue) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(encryptedValue);
        });
        return future;
    }

    public CompletableFuture<byte[]> decrypt(MDataInfo mDataInfo, byte[] value) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.mdataInfoDecrypt(mDataInfo, value, (result, decryptedValue) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(decryptedValue);
        });
        return future;
    }

    public CompletableFuture<byte[]> serialise(MDataInfo mDataInfo) {
        CompletableFuture<byte[]> future = new CompletableFuture<>();
        NativeBindings.mdataInfoSerialise(mDataInfo, (result, serialisedData) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(serialisedData);
        });
        return future;
    }

    public CompletableFuture<MDataInfo> deserialise(byte[] serialisedMData) {
        CompletableFuture<MDataInfo> future = new CompletableFuture<>();
        NativeBindings.mdataInfoDeserialise(serialisedMData, (result, mDataInfo) -> {
            if (result.getErrorCode() != 0) {
                future.completeExceptionally(Helper.ffiResultToException(result));
                return;
            }
            future.complete(mDataInfo);
        });
        return future;
    }

}
