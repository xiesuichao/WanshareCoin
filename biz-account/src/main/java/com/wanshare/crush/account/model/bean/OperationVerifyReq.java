package com.wanshare.crush.account.model.bean;

public class OperationVerifyReq {


    /**
     * account : mail:hello.world@email.com, tel:18645511111 , google:XXXXXX
     * code : 666666
     * secondCode : string
     * type : login
     */

    private String account;
    private String code;
    private String secondCode;
    private String type;

    public OperationVerifyReq() {
    }

    public OperationVerifyReq(int typeAccount, String account, String code, String secondCode, String type) {
        setAccount(account, typeAccount);
        this.code = code;
        this.secondCode = secondCode;
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account, int type) {
        if (type == 0) {
            this.account = "mail:" + account;
        }else if (type == 1){
            this.account = "tel:" + account;
        }else {
            this.account = "google:" + account;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
