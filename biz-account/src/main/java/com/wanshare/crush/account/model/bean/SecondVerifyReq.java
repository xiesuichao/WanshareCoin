package com.wanshare.crush.account.model.bean;

/**
 * Created by Richard on 2018/9/14
 */
public class SecondVerifyReq {

    /**
     * account : mail:hello.world@email.com, tel:18645511111 , google:XXXXXX
     * baseToken : asadaf
     * code : 666666
     * newpassword : Las122
     * secondtoken : asadaf
     * type : login
     */

    private String account;
    private String baseToken;
    private String code;
    private String newpassword;
    private String secondtoken;
    private String type;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBasetoken() {
        return baseToken;
    }

    public void setBasetoken(String basetoken) {
        this.baseToken = basetoken;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getSecondtoken() {
        return secondtoken;
    }

    public void setSecondtoken(String secondtoken) {
        this.secondtoken = secondtoken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
