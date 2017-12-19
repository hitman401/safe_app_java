package net.maidsafe.model;

import net.maidsafe.api.NativeHandle;

public class EncryptKeyPair {

    private NativeHandle secretEncryptKey;
    private NativeHandle publicEncryptKey;

    public EncryptKeyPair(NativeHandle publicEncryptKey, NativeHandle secretEncryptKey) {
        this.secretEncryptKey = secretEncryptKey;
        this.publicEncryptKey = publicEncryptKey;
    }

    public NativeHandle getPublicEncryptKey() {
        return publicEncryptKey;
    }

    public NativeHandle getSecretEncryptKey() {
        return secretEncryptKey;
    }
}
