package net.maidsafe.ffi.app;

public interface CallbackResultAsymSecretKey {
	public void call(FfiResult result, byte[] secEncKey);
}
