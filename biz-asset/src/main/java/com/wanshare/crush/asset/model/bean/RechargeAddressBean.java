package com.wanshare.crush.asset.model.bean;

/**
 * 充币地址
 * </br>
 * Date: 2018/8/25 15:40
 *
 * @author hemin
 */
public class RechargeAddressBean {

    /**
     * 币id
     */
    private String coinId;
    /**
     * 充币地址
     */
    private String coinAddress;

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCoinAddress() {
        return coinAddress;
    }

    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    @Override
    public String toString() {
        return "RechargeAddressBean{" +
                "coinId='" + coinId + '\'' +
                ", coinAddress='" + coinAddress + '\'' +
                '}';
    }
}
