package net.maidsafe.api;

import net.maidsafe.api.PublicSignKey;

public class SignKeyPair {
    private PublicSignKey publicSignKey;
    private SecretSignKey secretSignKey;

    public SignKeyPair(PublicSignKey publicSignKey, SecretSignKey secretSignKey) {
        this.publicSignKey = publicSignKey;
        this.secretSignKey = secretSignKey;
    }

    public PublicSignKey getPublicSignKey() {
        return publicSignKey;
    }

    public SecretSignKey getSecretSignKey() {
        return secretSignKey;
    }
}