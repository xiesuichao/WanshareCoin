package com.wanshare.crush.market.order.model.bean;

import java.util.List;

/**
 * Created by yangwenwu on 2018/8/30.
 * 委托订单数据模型
 */

public class EntrustsOrder {


    /**
     * items : [{"amount":"33333.33333","createdAt":"2017-01-22T09:28:33Z","exchange":"万象交易所","id":"hashed-entrust-id","price":"33.4444444","status":"entrusting","tradeAmount":"83838.83838","entrustVolume":"1.0","tradingPair":"ETH/BTC","volume":"1.00002"}]
     * meta : {"itemsPerPage":0,"links":[{"link":"string","rel":"self"}],"page":0,"requestedPage":1,"totalCount":0,"totalPage":0}
     * query : {"exchangeId":"hasded-exchange-id","status":"cancelled,done","tradeType":"both","tradingPair":"ETH/BTC","uid":"hashed-account-id"}
     */

    private MetaBean meta;
    private QueryBean query;
    private List<ItemsBean> items;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public QueryBean getQuery() {
        return query;
    }

    public void setQuery(QueryBean query) {
        this.query = query;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class MetaBean {
        /**
         * itemsPerPage : 0
         * links : [{"link":"string","rel":"self"}]
         * page : 0
         * requestedPage : 1
         * totalCount : 0
         * totalPage : 0
         */

        private int itemsPerPage;
        private int page;
        private int requestedPage;
        private int totalCount;
        private int totalPage;
        private List<LinksBean> links;

        public int getItemsPerPage() {
            return itemsPerPage;
        }

        public void setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getRequestedPage() {
            return requestedPage;
        }

        public void setRequestedPage(int requestedPage) {
            this.requestedPage = requestedPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<LinksBean> getLinks() {
            return links;
        }

        public void setLinks(List<LinksBean> links) {
            this.links = links;
        }

        public static class LinksBean {
            /**
             * link : string
             * rel : self
             */

            private String link;
            private String rel;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getRel() {
                return rel;
            }

            public void setRel(String rel) {
                this.rel = rel;
            }
        }
    }

    public static class QueryBean {
        /**
         * exchangeId : hasded-exchange-id
         * status : cancelled,done
         * tradeType : both
         * tradingPair : ETH/BTC
         * uid : hashed-account-id
         */

        private String exchangeId;
        private String status;
        private String tradeType;
        private String tradingPair;
        private String uid;

        public String getExchangeId() {
            return exchangeId;
        }

        public void setExchangeId(String exchangeId) {
            this.exchangeId = exchangeId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradingPair() {
            return tradingPair;
        }

        public void setTradingPair(String tradingPair) {
            this.tradingPair = tradingPair;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }

    public static class ItemsBean {
        /**
         * amount : 33333.33333
         * createdAt : 2017-01-22T09:28:33Z
         * exchange : 万象交易所
         * id : hashed-entrust-id
         * price : 33.4444444
         * status : entrusting
         * tradeAmount : 83838.83838
         * entrustVolume : 1.0
         * tradingPair : ETH/BTC
         * volume : 1.00002
         * tradeType buy:买入 sell:买出
         *
         */

        private String amount;
        private String createdAt;
        private String exchange;
        private String id;
        private String price;
        private String status;
        private String tradeAmount;
        private String entrustVolume;
        private String tradingPair;
        private String volume;
        private String tradeType;

        public ItemsBean() {
        }

        public ItemsBean(String amount, String createdAt, String price, String tradeAmount, String entrustVolume, String volume) {
            this.amount = amount;
            this.createdAt = createdAt;
            this.price = price;
            this.tradeAmount = tradeAmount;
            this.entrustVolume = entrustVolume;
            this.volume = volume;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(String tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public String getEntrustVolume() {
            return entrustVolume;
        }

        public void setEntrustVolume(String entrustVolume) {
            this.entrustVolume = entrustVolume;
        }

        public String getTradingPair() {
            return tradingPair;
        }

        public void setTradingPair(String tradingPair) {
            this.tradingPair = tradingPair;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }

        public String getTradeType() {
            return tradeType;
        }
    }
}
