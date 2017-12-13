package net.maidsafe.model;

import net.maidsafe.ffi.app.AuthGranted;

public class AuthResponse extends DecodeResult {
    private AuthGranted authGranted;

    public AuthResponse(int reqId, AuthGranted authGranted) {
        super(reqId);
        this.authGranted = authGranted;
    }

    public AuthGranted getAuthGranted() {
        return authGranted;
    }
}
