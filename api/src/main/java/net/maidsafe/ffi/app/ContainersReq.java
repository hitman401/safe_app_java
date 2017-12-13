package net.maidsafe.ffi.app;

/// Containers request
public class ContainersReq {
	private AppExchangeInfo app;

	public AppExchangeInfo getApp() {
		return app;
	}

	public void setApp(final AppExchangeInfo val) {
		app = val;
	}

	private ContainerPermission containers;

	public ContainerPermission getContainer() {
		return containers;
	}

	public void setContainer(final ContainerPermission val) {
		containers = val;
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

