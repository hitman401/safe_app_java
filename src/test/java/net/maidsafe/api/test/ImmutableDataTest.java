package net.maidsafe.api.test;

import net.maidsafe.api.SafeClient;
import net.maidsafe.api.model.CipherOpt;
import net.maidsafe.api.model.ImmutableDataReader;
import net.maidsafe.api.model.ImmutableDataWriter;
import net.maidsafe.api.model.XorName;
import junit.framework.TestCase;

public class ImmutableDataTest extends TestCase {

	private final SafeClient client;

	public ImmutableDataTest() throws Exception {
		client = Utils.getTestAppWithAccess();
	}

	public void testReadAndWriteAsPlain() throws Exception {
		ImmutableDataWriter writer;
		ImmutableDataReader reader;
		CipherOpt cipherOpt;
		XorName name;
		byte[] data;
		String sample = "sample data" + Math.random();

		
		// System.out.println("111111111111111111111");
		writer = client.immutableData().getWriter().get();
		cipherOpt = client.cipherOpt().getPlain().get();
		writer.write(sample.getBytes()).get();
		// System.out.println("111111111111111111111");
		name = writer.save(cipherOpt).get();
		// System.out.println("111111111111111111111");
		// System.out.println("GEt reader");
		reader = client.immutableData().getReader(name).get();
		// System.out.println("Got reader");
		long size = reader.getSize().get();
		// System.out.println("Got Size");
		data = reader.read(0, size).get();
		// System.out.println("Read");
		assertEquals(sample, new String(data));
	}

	public void testReadAndWriteAsSymetric() throws Exception {
		ImmutableDataWriter writer;
		ImmutableDataReader reader;
		CipherOpt cipherOpt;
		XorName name;
		byte[] data;
		String sample = "sample data 2" + Math.random();

		// System.out.println("222222222222222222222222");
		writer = client.immutableData().getWriter().get();
		// System.out.println("111111111111111111111");
		cipherOpt = client.cipherOpt().getSymmetric().get();
		// System.out.println("Writing");
		writer.write(sample.getBytes()).get();
		// System.out.println("Wrote");
		name = writer.save(cipherOpt).get();
		// System.out.println("Saved");
		reader = client.immutableData().getReader(name).get();
		// System.out.println("Reader");		
		long size = reader.getSize().get();
		// System.out.println("Size");
		data = reader.read(0, size).get();
		// System.out.println("ddata");
		assertEquals(sample, new String(data));
	}

	public void testReadAndWriteAsAsymetric() throws Exception {
		ImmutableDataWriter writer;
		ImmutableDataReader reader;
		CipherOpt cipherOpt;
		XorName name;
		byte[] data;
		String sample = "sample data 3" + Math.random();

		writer = client.immutableData().getWriter().get();
		cipherOpt = client.cipherOpt()
				.getAsymmetric(client.crypto().getAppPublicEncryptKey().get())
				.get();
		writer.write(sample.getBytes()).get();
		name = writer.save(cipherOpt).get();

		reader = client.immutableData().getReader(name).get();
		data = reader.read(0, reader.getSize().get()).get();

		assertEquals(sample, new String(data));
	}

}
