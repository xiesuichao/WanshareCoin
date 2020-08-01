package com.weshare.wanxiang.main.model.bean;

import com.wanshare.crush.exchange.model.bean.Exchange;

import java.util.List;

/**
 * Created by Jason on 2018/8/27.
 */

public class ExchangeListBean {

    /**
     * meta : {"page":1,"itemsPerPage":3,"totalCount":2}
     * exchange : [{"id":"3","logoUrl":"logo/exchange.png","name":"exchangeName3","usdAmount":"576342.7040009786","cnyAmount":"3919130.387206655","btcAmount":"91.48296888904423","tradingPairCount":"17","summary":"aaaaa","tags":"biaoqian,dasdas,dasdasd,sdsaddasdsd","nationality":"China"},{"id":"4","logoUrl":"logo/exchange.png","name":"exchangeName4","usdAmount":"514507.39209878375","cnyAmount":"3498650.2662717295","btcAmount":"81.66784001567996","tradingPairCount":"0","summary":"aaaaa","tags":"biaoqian,dasdas,dasdasd,sdsaddasdsd","nationality":"China"},{"id":"6","logoUrl":"logo/exchange.png","name":"exchangeName6","usdAmount":"499794.13315969903","cnyAmount":"3398600.1054859534","btcAmount":"79.33240208884112","tradingPairCount":"10","summary":"aaaaa","tags":"biaoqian,dasdas,dasdasd,sdsaddasdsd","nationality":"China"}]
     */

    private MetaBean meta;
    private List<Exchange> exchange;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<Exchange> getExchange() {
        return exchange;
    }

    public void setExchange(List<Exchange> exchange) {
        this.exchange = exchange;
    }

    public static class MetaBean {
        /**
         * page : 1
         * itemsPerPage : 3
         * totalCount : 2
         */

        private int page;
        private int itemsPerPage;
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
