package com.wanshare.crush.account.model.bean;

/**
 * Created by Richard on 2018/9/17
 */
public class ResetPasswordReq {

    /**
     * challenge : string
     * seccode : string
     * uri : tel:18645511111
     * validate : string
     */

    private String challenge;
    private String seccode;
    private String uri;
    private String validate;

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}
