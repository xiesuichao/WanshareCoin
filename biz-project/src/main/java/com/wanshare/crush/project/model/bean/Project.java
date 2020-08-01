package com.wanshare.crush.project.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 项目
 * Created by xiesuichao on 2018/9/11.
 */

public class Project implements Serializable{


    /**
     * meta : {"requestedPage":1,"page":1,"totalPage":1,"itemsPerPage":5,"totalCount":2}
     * items : [{"projectId":"6","coinId":"6","fullName":"TEST5","shortName":"test5","coinLogo":"xxx/xxx.png","issuedVolume":"1000000.00000000","latestPrice":"528910.2629552332","usdAmount24H":"3.269532149406433E9","chgPct24H":"99.87899648677187","listedExchange":"11","marketValue":"3.8465084110663925E10"},{"projectId":"8","coinId":"8","fullName":"TEST7","shortName":"test7","coinLogo":"xxx/xxx.png","issuedVolume":"1000000.00000000","latestPrice":"352252.6306735957","usdAmount24H":"1.7138814622149725E9","chgPct24H":"99.81831221564586","listedExchange":"13","marketValue":"2.0163311320176147E10"},{"projectId":"11","coinId":"11","fullName":"TEST10","shortName":"test10","coinLogo":"xxx/xxx.png","issuedVolume":"1000000.00000000","latestPrice":"478240.17684451","usdAmount24H":"2.1870744382803144E9","chgPct24H":"99.86617602807384","listedExchange":"9","marketValue":"2.5730287509180176E10"},{"projectId":"1","coinId":"1","fullName":"TEST1","shortName":"test1","coinLogo":"xxx/xxx.png","issuedVolume":"1000000.00000000","latestPrice":"275607.6017775765","usdAmount24H":"8.877772424668005E8","chgPct24H":"99.76778579550339","listedExchange":"11","marketValue":"1.044443814666824E10"},{"projectId":"5","coinId":"5","fullName":"TEST4","shortName":"test4","coinLogo":"xxx/xxx.png","issuedVolume":"1000000.00000000","latestPrice":"288580.56450841937","usdAmount24H":"5.309019816014871E8","chgPct24H":"99.77822484300349","listedExchange":"6","marketValue":"6.245905665899849E9"}]
     */

    private MetaBean meta;
    private List<ItemsBean> items;

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

    public static class MetaBean {
        /**
         * requestedPage : 1
         * page : 1
         * totalPage : 1
         * itemsPerPage : 5
         * totalCount : 2
         */

        private int requestedPage;
        private int page;
        private int totalPage;
        private int itemsPerPage;
        private int totalCount;

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
    }

    public static class ItemsBean {
        /**
         * projectId : 6
         * coinId : 6
         * fullName : TEST5
         * shortName : test5
         * coinLogo : xxx/xxx.png
         * issuedVolume : 1000000.00000000
         * latestPrice : 528910.2629552332
         * usdAmount24H : 3.269532149406433E9
         * chgPct24H : 99.87899648677187
         * listedExchange : 11
         * marketValue : 3.8465084110663925E10
         */

        private String projectId;
        private String coinId;
        private String fullName;
        private String shortName;
        private String coinLogo;
        private String issuedVolume;
        private String latestPrice;
        private String usdAmount24H;
        private String chgPct24H;
        private String listedExchange;
        private String marketValue;

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getCoinId() {
            return coinId;
        }

        public void setCoinId(String coinId) {
            this.coinId = coinId;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getCoinLogo() {
            return coinLogo;
        }

        public void setCoinLogo(String coinLogo) {
            this.coinLogo = coinLogo;
        }

        public String getIssuedVolume() {
            return issuedVolume;
        }

        public void setIssuedVolume(String issuedVolume) {
            this.issuedVolume = issuedVolume;
        }

        public String getLatestPrice() {
            return latestPrice;
        }

        public void setLatestPrice(String latestPrice) {
            this.latestPrice = latestPrice;
        }

        public String getUsdAmount24H() {
            return usdAmount24H;
        }

        public void setUsdAmount24H(String usdAmount24H) {
            this.usdAmount24H = usdAmount24H;
        }

        public String getChgPct24H() {
            return chgPct24H;
        }

        public void setChgPct24H(String chgPct24H) {
            this.chgPct24H = chgPct24H;
        }

        public String getListedExchange() {
            return listedExchange;
        }

        public void setListedExchange(String listedExchange) {
            this.listedExchange = listedExchange;
        }

        public String getMarketValue() {
            return marketValue;
        }

        public void setMarketValue(String marketValue) {
            this.marketValue = marketValue;
        }
    }
}
