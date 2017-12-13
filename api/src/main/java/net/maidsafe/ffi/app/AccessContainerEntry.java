package net.maidsafe.ffi.app;

/// Access container entry for a single app.
public class AccessContainerEntry {
	private ContainerInfo containersPtr;

	public ContainerInfo getContainersPtr() {
		return containersPtr;
	}

	public void setContainersPtr(final ContainerInfo val) {
		containersPtr = val;
	}

	private long containersLen;

	public long getContainersLen() {
		return containersLen;
	}

	public void setContainersLen(final long val) {
		containersLen = val;
	}

	private long containersCap;

	public long getContainersCap() {
		return containersCap;
	}

	public void setContainersCap(final long val) {
		containersCap = val;
	}

}

