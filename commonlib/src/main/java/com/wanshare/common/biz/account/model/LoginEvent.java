package com.wanshare.common.biz.account.model;

/**
 * Created by huyuqi on 2018/9/29
 */
public class LoginEvent {
    public static final int LOGIN_SUCCESS = 1;

    public static final int LOGOUT_SUCCESS = 2;

    private int eventType;

    public LoginEvent(int eventType) {
        this.eventType = eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventType() {
        return eventType;
    }

    public boolean isLoginSuccess() {
        return LOGIN_SUCCESS == eventType;
    }
}
