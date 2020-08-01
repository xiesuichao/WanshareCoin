package com.wanshare.crush.account.model.bean;

public class BindPhoneReq {


    /**
     * challenge : string
     * phoneNumber : 1864550666
     * seccode : string
     * validate : string
     * verificationCode : 11111
     */

    private String challenge;
    private String phoneNumber;
    private String seccode;
    private String validate;
    private String verificationCode;

    public BindPhoneReq() {
    }

    public BindPhoneReq(String challenge, String phoneNumber, String seccode, String validate, String verificationCode) {
        this.challenge = challenge;
        this.phoneNumber = phoneNumber;
        this.seccode = seccode;
        this.validate = validate;
        this.verificationCode = verificationCode;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
