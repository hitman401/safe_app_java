package net.maidsafe.ffi.app;

public interface CallbackIntByteArrayLen {
	public void call(int reqId, byte[] serialisedCfgPtr);
}
