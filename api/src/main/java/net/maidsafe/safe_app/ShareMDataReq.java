package net.maidsafe.safe_app;

/// Represents a request to share mutable data
public class ShareMDataReq {
    private AppExchangeInfo app;

    public AppExchangeInfo getApp() {
        return app;
    }

    public void setApp(final AppExchangeInfo val) {
        app = val;
    }

    private ShareMDaum mdata;

    public ShareMDaum getMdaum() {
        return mdata;
    }

    public void setMdaum(final ShareMDaum val) {
        mdata = val;
    }

    private long mdataLen;

    public long getMdataLen() {
        return mdataLen;
    }

    public void setMdataLen(final long val) {
        mdataLen = val;
    }

    private long mdataCap;

    public long getMdataCap() {
        return mdataCap;
    }

    public void setMdataCap(final long val) {
        mdataCap = val;
    }

}

