package net.maidsafe.ffi.app;

public interface CallbackResultByteArrayLenLong {
	public void call(FfiResult result, byte[] contentPtr, long version);
}
