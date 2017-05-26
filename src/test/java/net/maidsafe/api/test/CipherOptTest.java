package net.maidsafe.api.test;

import net.maidsafe.api.SafeClient;
import net.maidsafe.api.model.CipherOpt;
import net.maidsafe.api.model.PublicEncryptKey;
import junit.framework.TestCase;

public class CipherOptTest extends TestCase {

	private final SafeClient client;

	public CipherOptTest() throws Exception {
		client = Utils.getTestAppWithAccess();
	}

	public void testGetPlainCipherOpt() throws Exception {
		CipherOpt opt = client.cipherOpt().getPlain().get();
		assert (opt != null);
	}

	public void testGetSymmetricCipherOpt() throws Exception {
		CipherOpt opt = client.cipherOpt().getSymmetric().get();
		assert (opt != null);
	}

	public void testGetAsymmetricCipherOpt() throws Exception {
		PublicEncryptKey pubKey = client.crypto().getAppPublicEncryptKey()
				.get();
		CipherOpt opt = client.cipherOpt().getAsymmetric(pubKey).get();
		assert (opt != null);
	}

}
