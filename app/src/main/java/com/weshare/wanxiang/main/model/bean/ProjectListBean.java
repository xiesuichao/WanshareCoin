package com.weshare.wanxiang.main.model.bean;

import java.util.List;

/**
 * Created by Jason on 2018/9/4.
 */

public class ProjectListBean {

    /**
     * meta : {"requestedPage":1,"page":1,"totalPage":2,"itemsPerPage":1,"totalCount":2}
     * items : [{"projectId":"6","coinId":"6","fullName":"xxxxBTC","shortName":"BTC","coinLogo":"xxx/xxx.png","issuedVolume":"1000000.00000000","latestPrice":"528910.2629552332","usdAmount24H":"3.269532149406433E9","chgPct24H":"99.87899648677187","listedExchange":"11","marketValue":"3.8465084110663925E10"}]
     */

    private MetaBean meta;
    private List<ProjectBean> items;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<ProjectBean> getItems() {
        return items;
    }

    public void setItems(List<ProjectBean> items) {
        this.items = items;
    }

    public static class MetaBean {
        /**
         * requestedPage : 1
         * page : 1
         * totalPage : 2
         * itemsPerPage : 1
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
}
