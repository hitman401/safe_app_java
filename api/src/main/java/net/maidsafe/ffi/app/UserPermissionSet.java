package net.maidsafe.ffi.app;

/// FFI object representing a (User, Permission Set) pair.
public class UserPermissionSet {
	private long userH;

	public long getUserH() {
		return userH;
	}

	public void setUserH(final long val) {
		userH = val;
	}

	private PermissionSet permSet;

	public PermissionSet getPermSet() {
		return permSet;
	}

	public void setPermSet(final PermissionSet val) {
		permSet = val;
	}

}

