package com.wanshare.crush.account.model.bean;

public class BindStatueReq {


    /**
     * googleAuthenticator : 0
     * phoneAuthenticator : 0
     * emailAuthenticator : 0
     */

    private int googleAuthenticator;
    private int phoneAuthenticator;
    private int emailAuthenticator;

    public BindStatueReq() {
    }

    public BindStatueReq(int googleAuthenticator, int phoneAuthenticator, int emailAuthenticator) {
        this.googleAuthenticator = googleAuthenticator;
        this.phoneAuthenticator = phoneAuthenticator;
        this.emailAuthenticator = emailAuthenticator;
    }

    public int getGoogleAuthenticator() {
        return googleAuthenticator;
    }

    public void setGoogleAuthenticator(int GoogleAuthenticator) {
        this.googleAuthenticator = GoogleAuthenticator;
    }

    public int getPhoneAuthenticator() {
        return phoneAuthenticator;
    }

    public void setPhoneAuthenticator(int PhoneAuthenticator) {
        this.phoneAuthenticator = PhoneAuthenticator;
    }

    public int getEmailAuthenticator() {
        return emailAuthenticator;
    }

    public void setEmailAuthenticator(int EmailAuthenticator) {
        this.emailAuthenticator = EmailAuthenticator;
    }
}
