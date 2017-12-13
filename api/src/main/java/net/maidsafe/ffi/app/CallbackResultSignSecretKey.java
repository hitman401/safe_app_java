package net.maidsafe.ffi.app;

public interface CallbackResultSignSecretKey {
	public void call(FfiResult result, byte[] pubSignKey);
}
