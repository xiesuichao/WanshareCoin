package com.wanshare.crush.asset.model.bean;

/**
 * 当日已提币数量
 * </br>
 * Date: 2018/8/27 16:30
 *
 * @author hemin
 */
public class GetWithdrawTotalBean {

    private String coinId;
    /**
     * 当日已提币数量
     */
    private String total;

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
