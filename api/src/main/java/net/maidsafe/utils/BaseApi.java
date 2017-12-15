package net.maidsafe.utils;

import net.maidsafe.api.NativeHandle;

public class BaseApi {

    protected NativeHandle appHandle;

    public BaseApi(NativeHandle appHandle) {
        this.appHandle = appHandle;
    }

    private NativeHandle getAppHandle() {
        return appHandle;
    }
}
