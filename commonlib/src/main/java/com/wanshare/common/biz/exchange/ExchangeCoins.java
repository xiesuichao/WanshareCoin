package com.wanshare.common.biz.exchange;

import java.util.List;

/**
 * @author admin
 */
public class ExchangeCoins {

    private List<BuyerCoinBean> buyerCoin;
    private List<SellerCoinBean> sellerCoin;

    public List<BuyerCoinBean> getBuyerCoin() {
        return buyerCoin;
    }

    public void setBuyerCoin(List<BuyerCoinBean> buyerCoin) {
        this.buyerCoin = buyerCoin;
    }

    public List<SellerCoinBean> getSellerCoin() {
        return sellerCoin;
    }

    public void setSellerCoin(List<SellerCoinBean> sellerCoin) {
        this.sellerCoin = sellerCoin;
    }

    public static class BuyerCoinBean {
        /**
         * id : 0
         * name : string
         */

        private int id;
        private String name;

        public BuyerCoinBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class SellerCoinBean {
        /**
         * id : 0
         * name : string
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
