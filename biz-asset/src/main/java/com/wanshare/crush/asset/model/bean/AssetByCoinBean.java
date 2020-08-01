package com.wanshare.crush.asset.model.bean;

/**
 * 单个币种资产
 * </br>
 * Date: 2018/9/22 17:08
 *
 * @author huyuqi
 */
public class AssetByCoinBean {


    /**
     * coinId : 4
     * coinName : test4
     * total : 5004.00000000
     * balance : 5004.00000000
     * frozen : 0E-8
     * estimates : {"usdt":0,"btc":0,"cny":0}
     */

    private String coinId;
    private String coinName;
    private String total;
    private String balance;
    private String frozen;
    private EstimatesBean estimates;

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public EstimatesBean getEstimates() {
        return estimates;
    }

    public void setEstimates(EstimatesBean estimates) {
        this.estimates = estimates;
    }

}
