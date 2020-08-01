package com.wanshare.common.biz.market;

import java.util.List;

public class TradingCoins {

    private List<String> sellerCoin;
    private List<String> buyerCoin;

    public List<String> getSellerCoin() {
        return sellerCoin;
    }

    public void setSellerCoin(List<String> sellerCoin) {
        this.sellerCoin = sellerCoin;
    }

    public List<String> getBuyerCoin() {
        return buyerCoin;
    }

    public void setBuyerCoin(List<String> buyerCoin) {
        this.buyerCoin = buyerCoin;
    }
}
