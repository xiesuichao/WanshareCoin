package com.wanshare.crush.market.trade.view;

/**
 * 通知klineFragmentg更新quota
 * </br>
 * Date: 2018/9/4 17:22
 *
 * @author hemin
 */
public class KlineQuotaData {
    private int type;
    private String quota;

    public KlineQuotaData() {
    }

    public KlineQuotaData(int type, String quota) {
        this.type = type;
        this.quota = quota;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }
}
