package com.wanshare.crush.market.trade.model.bean;

/**
 * 行情概要
 * </br>
 * Date: 2018/9/6 11:58
 *
 * @author hemin
 */
public class OverviewBean {

    // 最新价
    private String latestPrice;
    // 最新价对应人民币
    private String cnyLatestPrice;

    // 卖方币种
    private String sellerCoin;
    //买方币种
    private String buyerCoin;
    // 24小时涨跌幅 百分比
    private String changePct;
    // 24小时涨跌幅
    private String changeExtent;
    // 24小时最高价
    private String highestPrice;
    // 24小时最低价
    private String lowestPrice;
    //24小时成交量
    private String volume;

    public String getSellerCoin() {
        return sellerCoin;
    }

    public void setSellerCoin(String sellerCoin) {
        this.sellerCoin = sellerCoin;
    }

    public String getBuyerCoin() {
        return buyerCoin;
    }

    public void setBuyerCoin(String buyerCoin) {
        this.buyerCoin = buyerCoin;
    }

    public String getChangePct() {
        return changePct;
    }

    public void setChangePct(String changePct) {
        this.changePct = changePct;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getCnyLatestPrice() {
        return cnyLatestPrice;
    }

    public void setCnyLatestPrice(String cnyLatestPrice) {
        this.cnyLatestPrice = cnyLatestPrice;
    }

    public String getChangeExtent() {
        return changeExtent;
    }

    public void setChangeExtent(String changeExtent) {
        this.changeExtent = changeExtent;
    }
}
