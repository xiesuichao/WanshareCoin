package com.wanshare.crush.asset.model.bean;

/**
 * 提币接口请求bean
 * </br>
 * Date: 2018/8/28 11:04
 *
 * @author hemin
 */
public class WithdrawReqBean {

    /**
     * 币种id
     */
    private String coinId;
    /**
     * 数量
     */
    private String amount;
    /**
     * 地址备注
     */
    private String addressTag;
    /**
     * 提币地址
     */
    private String address;
    /**
     * 是否保存地址
     */
    private boolean isSaveAddress;
    /**
     * 资金密码
     */
    private String traPassword;

    private String token;

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAddressTag() {
        return addressTag;
    }

    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIsSaveAddress() {
        return isSaveAddress;
    }

    public void setIsSaveAddress(boolean isSaveAddress) {
        this.isSaveAddress = isSaveAddress;
    }

    public String getTraPassword() {
        return traPassword;
    }

    public void setTraPassword(String traPassword) {
        this.traPassword = traPassword;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "WithdrawReqBean{" +
                "coinId='" + coinId + '\'' +
                ", amount='" + amount + '\'' +
                ", addressTag='" + addressTag + '\'' +
                ", address='" + address + '\'' +
                ", isSaveAddress=" + isSaveAddress +
                ", traPassword='" + traPassword + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
