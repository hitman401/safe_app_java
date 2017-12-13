package net.maidsafe.utils;

import net.maidsafe.ffi.app.FfiResult;

public class Helper {
    public static Exception ffiResultToException(FfiResult result) {
        return new Exception(result.getDescription() + " : " + result.getErrorCode());
    }
}
