package com.wanshare.crush.exchange.model.bean;

import java.util.List;

/**
 * 交易所排行接口返回json数据的bean类
 *
 * @author wangdunwei
 * @date 2018/8/25
 */
public class TopExchange {

    /**
     * meta : {"requestedPage":1,"page":0,"totalPage":0,"itemsPerPage":0,"totalCount":0,"links":[{"rel":"self","link":"string"}]}
     * tenant : [{"id":"string","logoUrl":"string","name":"string","volume":"string","tradingPairCount":0,"summary":"string","ConcernNumber":"string","tags":"string","nationality":"string"}]
     */

    private Meta meta;
    private List<Exchange> exchange;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Exchange> getExchange() {
        return exchange;
    }

    public void setExchange(List<Exchange> exchange) {
        this.exchange = exchange;
    }

    public static class Meta {
        /**
         * page : 1
         * itemsPerPage : 6
         * totalCount : 0
         */

        private int page;
        //每页最多多少条，pageSize
        private int itemsPerPage;
        //总共多少条
        private int totalCount;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
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
