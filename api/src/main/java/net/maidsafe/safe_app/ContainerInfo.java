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

    private MDataInfo MDataInfo;

    public MDataInfo getMDataInfo() {
        return MDataInfo;
    }

    public void setMDataInfo(final MDataInfo val) {
        MDataInfo = val;
    }

    private PermissionSet permissions;

    public PermissionSet getPermission() {
        return permissions;
    }

    public void setPermission(final PermissionSet val) {
        permissions = val;
    }

}

