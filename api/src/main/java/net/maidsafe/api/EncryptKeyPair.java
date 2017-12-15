package net.maidsafe.api;

import net.maidsafe.api.PublicEncryptKey;

public class EncryptKeyPair {

    private SecretEncryptKey secretEncryptKey;
    private PublicEncryptKey publicEncryptKey;

    public EncryptKeyPair(PublicEncryptKey publicEncryptKey, SecretEncryptKey secretEncryptKey) {
        this.secretEncryptKey = secretEncryptKey;
        this.publicEncryptKey = publicEncryptKey;
    }

    public PublicEncryptKey getPublicEncryptKey() {
        return publicEncryptKey;
    }

    public SecretEncryptKey getSecretEncryptKey() {
        return secretEncryptKey;
    }
}
