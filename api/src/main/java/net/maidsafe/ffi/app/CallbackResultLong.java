package net.maidsafe.ffi.app;

public interface CallbackResultLong {
	public void call(FfiResult result, long serialisedSize);
}
