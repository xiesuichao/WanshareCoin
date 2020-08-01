package com.wanshare.crush.exchange.model.bean;

import com.wanshare.wscomponent.utils.ArithUtil;

/**
 * @author wangdunwei
 * @date 2018/8/27
 */
public class Market {

    /**
     * exchangeId : 10
     * marketId : 9
     * sellerCoin : i
     * sellerCoinLogoUrl : logo/abcd1322dg
     * buyerCoin : e
     * latestPrice : 6400
     * volume : 39
     * amount : 249600
     * changeExtent : -13
     * changePct : -0.0020
     * lowestPrice : 6283
     * highestPrice : 6517
     */

    private String exchangeId;
    private String marketId;
    private String sellerCoin;
    private String sellerCoinLogoUrl;
    private String buyerCoin;
    private String latestPrice;
    private String volume;
    private String amount;
    private String changeExtent;
    private String changePct;
    private String lowestPrice;
    private String highestPrice;

    public Market() {
    }

    public Market(String exchangeId, String marketId, String sellerCoin, String sellerCoinLogoUrl,
                  String buyerCoin, String latestPrice, String volume, String amount, String changeExtent,
                  String changePct, String lowestPrice, String highestPrice) {
        this.exchangeId = exchangeId;
        this.marketId = marketId;
        this.sellerCoin = sellerCoin;
        this.sellerCoinLogoUrl = sellerCoinLogoUrl;
        this.buyerCoin = buyerCoin;
        this.latestPrice = latestPrice;
        this.volume = volume;
        this.amount = amount;
        this.changeExtent = changeExtent;
        this.changePct = changePct;
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }

    public String getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getSellerCoin() {
        return sellerCoin;
    }

    public void setSellerCoin(String sellerCoin) {
        this.sellerCoin = sellerCoin;
    }

    public String getSellerCoinLogoUrl() {
        return sellerCoinLogoUrl;
    }

    public void setSellerCoinLogoUrl(String sellerCoinLogoUrl) {
        this.sellerCoinLogoUrl = sellerCoinLogoUrl;
    }

    public String getBuyerCoin() {
        return buyerCoin;
    }

    public void setBuyerCoin(String buyerCoin) {
        this.buyerCoin = buyerCoin;
    }

    public String getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getChangeExtent() {
        return changeExtent;
    }

    public void setChangeExtent(String changeExtent) {
        this.changeExtent = changeExtent;
    }

    public String getChangePct() {
        return changePct;
    }

    public void setChangePct(String changePct) {
        this.changePct = changePct;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getChangePctWithSymbol() {
        if(ArithUtil.isNatural(changePct)) {
            return "+" + changePct + "%";
        }

        return changePct + "%";
    }
}
