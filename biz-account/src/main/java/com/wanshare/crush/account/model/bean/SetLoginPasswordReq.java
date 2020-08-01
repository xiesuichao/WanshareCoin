package com.wanshare.crush.account.model.bean;

/**
 * Created by Richard on 2018/9/19
 */
public class SetLoginPasswordReq {

    /**
     * challenge : string
     * newPassword : 1234ABCD$#@!
     * oldPassword : ABCD1234!@#$
     * seccode : string
     * validate : string
     */

    private String challenge;
    private String newPassword;
    private String oldPassword;
    private String seccode;
    private String validate;

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
}
