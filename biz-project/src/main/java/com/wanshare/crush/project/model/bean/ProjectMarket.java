package com.wanshare.crush.project.model.bean;

import java.util.List;

/**
 * 项目上线交易所
 * Created by xiesuichao on 2018/9/14.
 */

public class ProjectMarket {


    /**
     * meta : {"requestedPage":0,"page":0,"totalPage":0,"itemsPerPage":10,"totalCount":0}
     * items : [{"logo":"logo/abcd1322dg","name":"199","marketId":"25","tradingPair":"H/Y","latestPrice":"6400","amount24H":"19200","chgPct24H":"0"},{"logo":"logo/abcd1322dg","name":"197","marketId":"24","tradingPair":"A/V","latestPrice":"6400","amount24H":"19200","chgPct24H":"0"},{"logo":"logo/abcd1322dg","name":"216","marketId":"23","tradingPair":"G/G","latestPrice":"6400","amount24H":"19200","chgPct24H":"0"},{"logo":"logo/abcd1322dg","name":"207","marketId":"22","tradingPair":"D/T","latestPrice":"6400","amount24H":"19200","chgPct24H":"0"},{"logo":"logo/abcd1322dg","name":"207","marketId":"21","tradingPair":"W/A","latestPrice":"6400","amount24H":"19200","chgPct24H":"0"}]
     */

    private MetaBean meta;
    private List<ItemsBean> items;

    public ProjectMarket() {
    }

    public ProjectMarket(MetaBean meta, List<ItemsBean> items) {
        this.meta = meta;
        this.items = items;
    }

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ProjectMarket{" +
                "meta=" + meta +
                ", items=" + items +
                '}';
    }

    public static class MetaBean {
        /**
         * requestedPage : 0
         * page : 0
         * totalPage : 0
         * itemsPerPage : 10
         * totalCount : 0
         */

        private int requestedPage;
        private int page;
        private int totalPage;
        private int itemsPerPage;
        private int totalCount;

        public MetaBean() {
        }

        public MetaBean(int requestedPage, int page, int totalPage, int itemsPerPage, int totalCount) {
            this.requestedPage = requestedPage;
            this.page = page;
            this.totalPage = totalPage;
            this.itemsPerPage = itemsPerPage;
            this.totalCount = totalCount;
        }

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

        @Override
        public String toString() {
            return "MetaBean{" +
                    "requestedPage=" + requestedPage +
                    ", page=" + page +
                    ", totalPage=" + totalPage +
                    ", itemsPerPage=" + itemsPerPage +
                    ", totalCount=" + totalCount +
                    '}';
        }
    }

    public static class ItemsBean {
        /**
         * logo : logo/abcd1322dg
         * name : 199
         * marketId : 25
         * tradingPair : H/Y
         * latestPrice : 6400
         * amount24H : 19200
         * chgPct24H : 0
         */

        private String logo;
        private String name;
        private String marketId;
        private String tradingPair;
        private String latestPrice;
        private String amount24H;
        private String chgPct24H;

        public ItemsBean() {
        }

        public ItemsBean(String logo, String name, String marketId, String tradingPair, String latestPrice, String amount24H, String chgPct24H) {
            this.logo = logo;
            this.name = name;
            this.marketId = marketId;
            this.tradingPair = tradingPair;
            this.latestPrice = latestPrice;
            this.amount24H = amount24H;
            this.chgPct24H = chgPct24H;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMarketId() {
            return marketId;
        }

        public void setMarketId(String marketId) {
            this.marketId = marketId;
        }

        public String getTradingPair() {
            return tradingPair;
        }

        public void setTradingPair(String tradingPair) {
            this.tradingPair = tradingPair;
        }

        public String getLatestPrice() {
            return latestPrice;
        }

        public void setLatestPrice(String latestPrice) {
            this.latestPrice = latestPrice;
        }

        public String getAmount24H() {
            return amount24H;
        }

        public void setAmount24H(String amount24H) {
            this.amount24H = amount24H;
        }

        public String getChgPct24H() {
            return chgPct24H;
        }

        public void setChgPct24H(String chgPct24H) {
            this.chgPct24H = chgPct24H;
        }

        @Override
        public String toString() {
            return "ItemsBean{" +
                    "logo='" + logo + '\'' +
                    ", name='" + name + '\'' +
                    ", marketId='" + marketId + '\'' +
                    ", tradingPair='" + tradingPair + '\'' +
                    ", latestPrice='" + latestPrice + '\'' +
                    ", amount24H='" + amount24H + '\'' +
                    ", chgPct24H='" + chgPct24H + '\'' +
                    '}';
        }
    }
}
