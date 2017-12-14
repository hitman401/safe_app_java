package net.maidsafe.safe_app;

/// Information about a container (name, `MDataInfo` and permissions)
public class ContainerInfo {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String val) {
		name = val;
	}

	private MdataInfo mdataInfo;

	public MdataInfo getMdataInfo() {
		return mdataInfo;
	}

	public void setMdataInfo(final MdataInfo val) {
		mdataInfo = val;
	}

	private PermissionSet permissions;

	public PermissionSet getPermission() {
		return permissions;
	}

	public void setPermission(final PermissionSet val) {
		permissions = val;
	}

}

