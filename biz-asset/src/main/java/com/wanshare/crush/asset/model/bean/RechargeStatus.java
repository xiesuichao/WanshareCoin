package com.wanshare.crush.asset.model.bean;

import android.content.Context;

import com.wanshare.crush.asset.R;

/**
 * 交易状态
 *
 * @author wangdunwei
 * @date 2018/8/25
 */
public enum RechargeStatus {
    SUCCEED(R.string.recharge_succeed),
    FAILED(R.string.recharge_failed),
    CONFIRMING(R.string.confirming),
    UNDEFINED(0);

    private int status;

    RechargeStatus(int status) {
        this.status = status;
    }

    public static RechargeStatus of(String status) {
        switch(status) {
            case "successed":
                return SUCCEED;
            case "confirming":
                return CONFIRMING;
            case "failed":
                return FAILED;
            default:
                return UNDEFINED;
        }
    }

    /**
     * 转换为界面上显示的文字
     */
    public String toVisibleString(Context context) {
        if(this == UNDEFINED) {
            return "";
        }

        return context.getString(status);
    }
}
