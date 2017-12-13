package net.maidsafe.ffi.app;

public interface CallbackResultByteArrayLen {
	public void call(FfiResult result, byte[] signedDataPtr);
}
