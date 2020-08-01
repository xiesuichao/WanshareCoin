package com.wanshare.crush.account.model.bean;

/**
 * Created by Richard on 2018/9/19
 */
public class SetAssetPasswordReq {

    /**
     * baseToken : 1234ABCD$#@!
     * password : ABCD1234!@#$
     * traPassword : 1234ABCD$#@!
     */

    private String baseToken;
    private String password;
    private String traPassword;

    public String getBaseToken() {
        return baseToken;
    }

    public void setBaseToken(String baseToken) {
        this.baseToken = baseToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTraPassword() {
        return traPassword;
    }

    public void setTraPassword(String traPassword) {
        this.traPassword = traPassword;
    }
}
