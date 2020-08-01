package com.wanshare.crush.account.model.bean;

/**
 * Created by Richard on 2018/9/11
 */
public class RegisterReq {

    /**
     * challenge : string
     * country : string
     * email : hello.world@email.com"
     * password : ABCD1234$#@!
     * promotionCode : 2NbYYJiEb4npQ
     * seccode : string
     * validate : string
     * verificationCode : asbcfg
     */

    private String challenge;
    private String country;
    private String email;
    private String password;
    private String promotionCode;
    private String seccode;
    private String validate;
    private String verificationCode;

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
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
