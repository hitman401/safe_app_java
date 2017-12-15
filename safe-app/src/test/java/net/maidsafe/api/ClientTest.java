package net.maidsafe.api;

import net.maidsafe.model.*;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
    @Test
    public void unregisteredAccessTest() throws Exception {
        Client client = new Client();
        App app = new App("net.maidsafe.java.test", "sample", "MaidSafe.net Ltd",  "0.1.0");
        Request request = client.getUnregisteredSessionRequest(app).get();
        Assert.assertTrue(request.getReqId() != 0);
        Assert.assertNotNull(request.getUri());
        Session session = client.connect(new byte[0], () -> {}).get();
        ImmutableDataReader.EncryptKeyPair encryptKeyPair = session.getCrypto().generateEncryptKeyPair().get();
        byte[] cipherText = session.getCrypto().encrypt(encryptKeyPair.getPublicEncryptKey(), encryptKeyPair.getSecretEncryptKey(), "Hello".getBytes()).get();
        Assert.assertEquals("Hello", new String(session.getCrypto().decrypt(encryptKeyPair.getPublicEncryptKey(), encryptKeyPair.getSecretEncryptKey(), cipherText).get()));
    }
}
