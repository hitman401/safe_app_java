package net.maidsafe.ffi.app;

public interface CallbackResultFileContextHandle {
	public void call(FfiResult result, long fileH);
}
