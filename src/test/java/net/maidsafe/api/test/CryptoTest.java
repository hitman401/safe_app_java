package net.maidsafe.api.test;

import java.util.Arrays;

import javax.xml.bind.DatatypeConverter;

import junit.framework.TestCase;
import net.maidsafe.api.SafeClient;
import net.maidsafe.api.model.EncryptKeyPair;
import net.maidsafe.api.model.PublicEncryptKey;
import net.maidsafe.api.model.PublicSignKey;
import net.maidsafe.utils.FfiConstant;

public class CryptoTest extends TestCase {

	private final SafeClient client;

	public CryptoTest() throws Exception {
		client = Utils.getTestAppWithAccess();
	}

	public void testSha3Hash() throws Exception {
		// System.out.println("testSha3Hash");
		String expected = "DDAD25FB24BD67C0AD883AC9C747943036EC068837C8A894E44F29244548F4ED";		
		byte[] hash = client.crypto().hashSHA3("Demo".getBytes()).get();
		assertEquals(expected, DatatypeConverter.printHexBinary(hash));
		// System.out.println("testSha3Hash END");
	}

	public void testAppPublicSignKey() throws Exception {
		// System.out.println("testAppPublicSignKey");
		PublicSignKey key = client.crypto().getAppPublicSignKey().get();
		// System.out.println("Key - 1");
		assert (key != null);
		byte[] raw = key.getRaw().get();
		// System.out.println("Raw");
		assertEquals(raw.length, FfiConstant.SIGN_PUBLICKEYBYTES);
		key = client.crypto().getPublicSignKey(raw).get();
		// System.out.println("From Raw key");
		assert (Arrays.equals(raw, key.getRaw().get()));
		// System.out.println("testAppPublicSignKey END");
	}

	public void testAppPublicSignKeyInvalidSize() throws Exception {
		// System.out.println("testAppPublicSignKeyInvalidSize");
		PublicSignKey key = client.crypto().getAppPublicSignKey().get();
		// System.out.println("111111111");
		assert (key != null);
		byte[] raw = key.getRaw().get();
		// System.out.println("2222222222222");
		assertEquals(raw.length, FfiConstant.SIGN_PUBLICKEYBYTES);
		try {
			client.crypto().getPublicSignKey(Arrays.copyOf(raw, 10)).get();
			// System.out.println("333333333");
		} catch (Exception e) {
			assert (e != null);
		}
		try {
			client.crypto().getPublicSignKey(null).get();
			// System.out.println("4444444444444");
		} catch (Exception e) {
			assert (e != null);
		}
		// System.out.println("testAppPublicSignKeyInvalidSize END");
	}

	public void testAppPublicEncryptKey() throws Exception {
		// System.out.println("testAppPublicEncKey");
		PublicEncryptKey key = client.crypto().getAppPublicEncryptKey().get();
		// System.out.println("1111111");
		assert (key != null);
		byte[] raw = key.getRaw().get();
		// System.out.println("2222222222");
		assertEquals(raw.length, FfiConstant.BOX_PUBLICKEYBYTES);
		key = client.crypto().getPublicEncryptKey(raw).get();
		// System.out.println("3333333333333333");
		assert (Arrays.equals(raw, key.getRaw().get()));
		// System.out.println("testAppPublicEncKey END");
	}

	public void testAppPublicEncryptKeyInvalidSize() throws Exception {
		// System.out.println("testAppPublicEncKeyInvalidSize");
		PublicEncryptKey key = client.crypto().getAppPublicEncryptKey().get();
		// System.out.println("11111111111111");
		assert (key != null);
		byte[] raw = key.getRaw().get();
		// System.out.println("22222222222222222");
		assertEquals(raw.length, FfiConstant.BOX_PUBLICKEYBYTES);
		try {
			client.crypto().getPublicEncryptKey(Arrays.copyOf(raw, 10)).get();
			// System.out.println("3333333333");
		} catch (Exception e) {
			assert (e != null);
		}
		try {
			client.crypto().getPublicEncryptKey(null).get();
			// System.out.println("444444444444444");
		} catch (Exception e) {
			assert (e != null);
		}
		// System.out.println("testAppPublicEncKeyInvalidSize - End");
	}

	public void testEncryptKeyPairGeneration() throws Exception {
		// System.out.println("testEncKeyPairGen");
		EncryptKeyPair pair = client.crypto().generateEncryptKeyPair().get();
		// System.out.println("11111111111111111111");
		assert (pair.getPublicKey() != null);
		assert (pair.getSecretKey() != null);
		byte[] kk = pair.getSecretKey().getRaw().get();
		client.crypto().getSecretEncryptKey(kk)
				.get();
		// System.out.println("222222222222222");
		// System.out.println("testEncKeyPairGen - END");
	}

	public void testBoxEncryption() throws Exception {
		// System.out.println("testBoxEnc");
		EncryptKeyPair senderKeys = client.crypto().generateEncryptKeyPair()
				.get();
		// System.out.println("1111111111");
		EncryptKeyPair recieverKeys = client.crypto().generateEncryptKeyPair()
				.get();
		// System.out.println("2222222222222");
		byte[] cipherText = recieverKeys.getPublicKey()
				.encrypt("message".getBytes(), senderKeys.getSecretKey()).get();
		// System.out.println("333333333333");
		byte[] plainText = recieverKeys.getSecretKey()
				.decrypt(cipherText, senderKeys.getPublicKey()).get();
		// System.out.println("444444444444444444");
		assertEquals(new String(plainText), "message");
		// System.out.println("testBoxEnc END");
	}

	public void testBoxSealedEncryption() throws Exception {
		// System.out.println("testSealedEnc");
		EncryptKeyPair recieverKeys = client.crypto().generateEncryptKeyPair()
				.get();
		// System.out.println("1111111111111");
		byte[] cipherText = recieverKeys.getPublicKey()
				.encryptSealed("message".getBytes()).get();
		// System.out.println("2222222222222222");
		byte[] plainText = recieverKeys.decryptSealed(cipherText).get();
		// System.out.println("33333333333333333333");
		assertEquals(new String(plainText), "message");
		// System.out.println("testSealedEnc - END");
	}

}
