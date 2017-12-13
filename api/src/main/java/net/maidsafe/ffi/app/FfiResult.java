package net.maidsafe.ffi.app;

/// FFI result wrapper
public class FfiResult {
	private int errorCode;

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final int val) {
		errorCode = val;
	}

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(final String val) {
		description = val;
	}

}

