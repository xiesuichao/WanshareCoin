package com.wanshare.common.gfw;

public class ReqAddress {

    /**
     * failure : 192.168.83.68,192.168.83.66
     * success : 192.168.83.68
     */

    private String failure;
    private String success;

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
