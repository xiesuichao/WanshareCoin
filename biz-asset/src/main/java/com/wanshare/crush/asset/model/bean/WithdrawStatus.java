package com.wanshare.crush.asset.model.bean;

import android.content.Context;

import com.wanshare.crush.asset.R;

/**
 * 交易状态
 *
 * @author wangdunwei
 * @date 2018/8/25
 */
public enum WithdrawStatus {
    SUCCEED(R.string.withdraw_succeed),
    FAILED(R.string.withdraw_failed),
    REVIEWING(R.string.withdraw_reviewing),
    CONFIRMING(R.string.confirming),
    AUDIT_FAILURE(R.string.withdraw_audit_failed),
    CANCEL(R.string.withdraw_cancel),
    WAIT_REVIEW(R.string.withdraw_wait_review),
    UNDEFINED(0);

    private int status;

    WithdrawStatus(int status) {
        this.status = status;
    }

    public static WithdrawStatus of(String status) {
        switch(status) {
            case "successed":
                return SUCCEED;
            case "failed":
                return FAILED;
            case "reviewing":
                return REVIEWING;
            case "confirming":
                return CONFIRMING;
            case "auditfailure":
                return AUDIT_FAILURE;
            case "cancel":
                return CANCEL;
            case "pendingreview":
                return WAIT_REVIEW;
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
