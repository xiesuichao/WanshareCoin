package com.wanshare.crush.market.order.model.bean;

import java.util.List;

/**
 * Created by yangwenwu on 2018/8/30.
 */

public class HistoryOrder {

    /**
     * meta : {"requestedPage":1,"page":0,"totalPage":0,"itemsPerPage":0,"totalCount":0,"links":[{"rel":"self","link":"string"}]}
     * query : {"exchangeId":"hashed-exchange-id","tradingPair":"BTC/ETH"}
     * items : [{"createdAt":"2017-01-22T09:28:33Z","exchange":"万象交易所","tradingPair":"ETH/USDT","price":"2223.88776644","volume":"2223.88776644","tradeAmount":"2223.88776644"}]
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
         * requestedPage : 1
         * page : 0
         * totalPage : 0
         * itemsPerPage : 0
         * totalCount : 0
         * links : [{"rel":"self","link":"string"}]
         */

        private int requestedPage;
        private int page;
        private int totalPage;
        private int itemsPerPage;
        private int totalCount;
        private List<LinksBean> links;

        public int getRequestedPage() {
            return requestedPage;
        }

        public void setRequestedPage(int requestedPage) {
            this.requestedPage = requestedPage;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getItemsPerPage() {
            return itemsPerPage;
        }

        public void setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<LinksBean> getLinks() {
            return links;
        }

        public void setLinks(List<LinksBean> links) {
            this.links = links;
        }

        public static class LinksBean {
            /**
             * rel : self
             * link : string
             */

            private String rel;
            private String link;

            public String getRel() {
                return rel;
            }

            public void setRel(String rel) {
                this.rel = rel;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }
    }

    public static class QueryBean {
        /**
         * exchangeId : hashed-exchange-id
         * tradingPair : BTC/ETH
         */

        private String exchangeId;
        private String tradingPair;

        public String getExchangeId() {
            return exchangeId;
        }

        public void setExchangeId(String exchangeId) {
            this.exchangeId = exchangeId;
        }

        public String getTradingPair() {
            return tradingPair;
        }

        public void setTradingPair(String tradingPair) {
            this.tradingPair = tradingPair;
        }
    }

    public static class ItemsBean {
        /**
         * createdAt : 2017-01-22T09:28:33Z
         * exchange : 万象交易所
         * tradingPair : ETH/USDT
         * price : 2223.88776644
         * volume : 2223.88776644
         * tradeAmount : 2223.88776644
         */

        private String createdAt;
        private String exchange;
        private String tradingPair;
        private String price;
        private String volume;
        private String tradeAmount;

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

        public String getTradingPair() {
            return tradingPair;
        }

        public void setTradingPair(String tradingPair) {
            this.tradingPair = tradingPair;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(String tradeAmount) {
            this.tradeAmount = tradeAmount;
        }
    }
}
