package com.wanshare.crush.account.model.bean;

/**
 * 账户验证-获取验证码请求体
 */
public class AccountVerificationReq {

    /**
     * uri : tel:+8618645511111
     * useAge : bind-phone
     */

    public AccountVerificationReq(String uri, String useAge) {
        this.uri = uri;
        this.useAge = useAge;
    }

    public AccountVerificationReq() {
    }

    private String uri;
    private String useAge;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setUri(String uri, int type) {
        if (type == 0) {
            this.uri = "mail:" + uri;
        }else if (type == 1){
            this.uri = "tel:" + uri;
        }else{

        }
    }

    public String getUseAge() {
        return useAge;
    }

    public void setUseAge(String useAge) {
        this.useAge = useAge;
    }


}
