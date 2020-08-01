package com.wanshare.common.biz.account.model;

import com.google.gson.JsonObject;

/**
 * @author huyuqi
 * @date 2018/8/21
 * 二次验证Event
 */
public class SecondVerifyEvent {

    private String verifyType;
    private JsonObject response;

    public SecondVerifyEvent(String verifyType, JsonObject response) {
        this.verifyType = verifyType;
        this.response = response;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    public JsonObject getResponse() {
        return response;
    }

    public void setResponse(JsonObject response) {
        this.response = response;
    }

}
