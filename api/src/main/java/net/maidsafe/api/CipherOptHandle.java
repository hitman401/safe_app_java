package net.maidsafe.api;

import net.maidsafe.safe_app.NativeBindings;

public class CipherOptHandle extends NativeHandle {
    public CipherOptHandle(NativeHandle appHandle, long handle) {
        super(appHandle.toLong(), (cipherOpt) -> {
            NativeBindings.cipherOptFree(appHandle.toLong(), cipherOpt, (result) -> {
            });
        });
    }
}
