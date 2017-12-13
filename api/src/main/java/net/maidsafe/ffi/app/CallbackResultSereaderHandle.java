package net.maidsafe.ffi.app;

public interface CallbackResultSereaderHandle {
	public void call(FfiResult result, long seH);
}
