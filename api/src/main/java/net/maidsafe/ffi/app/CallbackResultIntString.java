package net.maidsafe.ffi.app;

public interface CallbackResultIntString {
	public void call(FfiResult result, int reqId, String encodedPtr);
}
