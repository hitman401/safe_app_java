package net.maidsafe.api;

import net.maidsafe.model.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
    @Test
    public void unregisteredAccessTest() throws Exception {
        Session.load();
        App app = new App("net.maidsafe.java.test", "sample", "MaidSafe.net Ltd",  "0.1.0");
        Request request = Session.getUnregisteredSessionRequest(app).get();
        Assert.assertTrue(request.getReqId() != 0);
        Assert.assertNotNull(request.getUri());
        Session.connect(new byte[0], () -> {}).get();
        EncryptKeyPair encryptKeyPair = Crypto.generateEncryptKeyPair().get();
        byte[] cipherText = Crypto.encrypt(encryptKeyPair.getPublicEncryptKey(), encryptKeyPair.getSecretEncryptKey(), "Hello".getBytes()).get();
        Assert.assertEquals("Hello", new String(Crypto.decrypt(encryptKeyPair.getPublicEncryptKey(), encryptKeyPair.getSecretEncryptKey(), cipherText).get()));
    }

}
