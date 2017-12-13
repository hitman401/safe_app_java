package net.maidsafe.ffi.app;

/// Represents the FFI-safe account info.
public class AccountInfo {
	private long mutationsDone;

	public long getMutationsDone() {
		return mutationsDone;
	}

	public void setMutationsDone(final long val) {
		mutationsDone = val;
	}

	private long mutationsAvailable;

	public long getMutationsAvailable() {
		return mutationsAvailable;
	}

	public void setMutationsAvailable(final long val) {
		mutationsAvailable = val;
	}

}
