package net.maidsafe.ffi.app;

public interface CallbackResultAsymPublicKey {
	public void call(FfiResult result, byte[] pubEncKey);
}
