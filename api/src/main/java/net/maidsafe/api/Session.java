package net.maidsafe.api;

import net.maidsafe.safe_app.CallbackVoid;
import net.maidsafe.model.NativeHandle;

public class Session {

	private Crypto crypto;
	private CallbackVoid disconnectedCb;

	public Session(NativeHandle appHandle, CallbackVoid disconnectedCb) {
		this.crypto = new Crypto(appHandle);
		this.disconnectedCb = disconnectedCb;
	}

	public Crypto getCrypto() {
		return crypto;
	}
}
