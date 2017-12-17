package net.maidsafe.api;

import net.maidsafe.api.idata.ImmutableDataReader;
import net.maidsafe.safe_app.NativeBindings;
import net.maidsafe.utils.BaseApi;

import java.util.concurrent.CompletableFuture;

public class CipherOpt extends BaseApi {

    public CipherOpt(NativeHandle appHandle) {
        super(appHandle);
    }

    public CompletableFuture<CipherOptHandle> getPlainCipherOpt() {
        CompletableFuture<CipherOptHandle> future = new CompletableFuture<>();
        NativeBindings.cipherOptNewPlaintext(appHandle.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                return;
            }
            future.complete(new CipherOptHandle(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<CipherOptHandle> getSymmetricCipherOpt() {
        CompletableFuture<CipherOptHandle> future = new CompletableFuture<>();
        NativeBindings.cipherOptNewSymmetric(appHandle.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                return;
            }
            future.complete(new CipherOptHandle(appHandle, handle));
        });
        return future;
    }

    public CompletableFuture<CipherOptHandle> getAsymmetricCipherOpt(ImmutableDataReader.PublicEncryptKey publicEncryptKey) {
        CompletableFuture<CipherOptHandle> future = new CompletableFuture<>();
        NativeBindings.cipherOptNewAsymmetric(appHandle.toLong(), publicEncryptKey.toLong(), (result, handle) -> {
            if (result.getErrorCode() != 0) {
                return;
            }
            future.complete(new CipherOptHandle(appHandle, handle));
        });
        return future;
    }
}
