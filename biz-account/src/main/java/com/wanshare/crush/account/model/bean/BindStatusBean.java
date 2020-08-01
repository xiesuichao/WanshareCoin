package com.wanshare.crush.account.model.bean;

import java.io.Serializable;

public class BindStatusBean implements Serializable {


    /**
     * emailAuthenticator : 0
     * googleAuthenticator : 0
     * phoneAuthenticator : 0
     */

    private int emailAuthenticator;
    private int googleAuthenticator;
    private int phoneAuthenticator;
    private String accountId;

    public BindStatusBean(int emailAuthenticator, int googleAuthenticator, int phoneAuthenticator) {
        this.emailAuthenticator = emailAuthenticator;
        this.googleAuthenticator = googleAuthenticator;
        this.phoneAuthenticator = phoneAuthenticator;
    }

    public int getEmailAuthenticator() {
        return emailAuthenticator;
    }

    public void setEmailAuthenticator(int EmailAuthenticator) {
        this.emailAuthenticator = EmailAuthenticator;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
