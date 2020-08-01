package com.wanshare.crush.account.model.bean;

public class GoogleAuthenticatorReq {


    /**
     * password : string
     * verificationCode : string
     */

    private String password;
    private String verificationCode;

    public GoogleAuthenticatorReq() {
    }

    public GoogleAuthenticatorReq(String password, String verificationCode) {
        this.password = password;
        this.verificationCode = verificationCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
