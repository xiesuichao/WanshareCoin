package com.wanshare.crush.account.model.bean;

public class GoogleQrReq {

//    {
//        "account": "mailto:hello.world@email.com, tel:18645511111"
//    }


    private String account;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account, int type) {
        if (type == 0) {
            this.account = "mail:" + account;
        }else if (type == 1){
            this.account = "tel:" + account;
        }else{

        }
    }

    public void setAccount(String account, String tell) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("mail:" + account);
        buffer.append(",");
        buffer.append("tel:" + tell);
        this.account = buffer.toString();
    }
}
