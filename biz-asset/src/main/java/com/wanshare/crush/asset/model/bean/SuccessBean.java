package com.wanshare.crush.asset.model.bean;

import java.io.Serializable;

/**
 * 返回成功
 * </br>
 * Date: 2018/9/14 14:23
 *
 * @author hemin
 */
public class SuccessBean implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SuccessBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
