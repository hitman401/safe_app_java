package net.maidsafe.safe_app;

/// Information about an application that has access to an MD through `sign_key`
public class AppAcce {
    private byte[] signKey;
    private PermissionSet permissions;
    private String name;
    private String appId;

    public byte[] getSignKey() {
        return signKey;
    }

    public void setSignKey(final byte[] val) {
        signKey = val;
    }

    public PermissionSet getPermission() {
        return permissions;
    }

    public void setPermission(final PermissionSet val) {
        permissions = val;
    }

    public String getName() {
        return name;
    }

    public void setName(final String val) {
        name = val;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(final String val) {
        appId = val;
    }

}

