package net.maidsafe.ffi.app;

public interface CallbackResultAsymNonce {
	public void call(FfiResult result, byte[] nonce);
}
