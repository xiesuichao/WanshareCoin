package com.wanshare.crush.account.model.bean;

public class GoogleStartReq {



    private String verificationCode;


    public GoogleStartReq(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public GoogleStartReq() {
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
