package com.wanshare.crush.asset.model.bean;

/**
 * 资产相关接口Request
 *
 * @author wangdunwei
 * @date 2018/8/24
 */
public class AssetReq {
    public AssetReq() {
    }

    public AssetReq(int coinId, String remark, String address) {
        this.coinId = String.valueOf(coinId);
        this.remark = remark;
        this.address = address;
    }

    public AssetReq(String coinId, String remark, String address) {
        this.coinId = coinId;
        this.remark = remark;
        this.address = address;
    }

    public static AssetReq of(Coin coin) {
        AssetReq req = new AssetReq();
        req.setCoinId(String.valueOf(coin.getId()));
        req.setRemark(coin.getRemark());
        req.setAddress(coin.getAddress());
        return req;
    }

    /**
     * coinId : afa5454
     * remark : string
     * address : string
     */

    private String coinId;
    private String remark;
    private String address;

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
