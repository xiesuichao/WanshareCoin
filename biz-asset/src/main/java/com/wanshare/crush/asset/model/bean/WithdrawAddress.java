package com.wanshare.crush.asset.model.bean;

import java.util.List;

/**
 * @author admin
 */
public class WithdrawAddress {

    /**
     * meta : {"pageNo":1,"pageSize":10}
     * query : {"coinId":20,"accountId":"10086","status":"AVALIABLE"}
     * withdrawAddresses : [{"id":"1","coinId":"20","remark":"123","address":"123"}]
     */

    private MetaBean meta;
//    private QueryBean query;
    private List<Coin> withdrawAddresses;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

//    public QueryBean getQuery() {
//        return query;
//    }
//
//    public void setQuery(QueryBean query) {
//        this.query = query;
//    }

    public List<Coin> getWithdrawAddresses() {
        return withdrawAddresses;
    }

    public void setWithdrawAddresses(List<Coin> withdrawAddresses) {
        this.withdrawAddresses = withdrawAddresses;
    }

    public static class MetaBean {
        /**
         * pageNo : 1
         * pageSize : 10
         */

        private int pageNo;
        private int pageSize;

        public int getPageNo() {
            return pageNo;
        }

        public void setPageNo(int pageNo) {
            this.pageNo = pageNo;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

//    public static class QueryBean {
//        /**
//         * coinId : 20
//         * accountId : 10086
//         * status : AVALIABLE
//         */
//
//        private int coinId;
//        private String accountId;
//        private String status;
//
//        public int getCoinId() {
//            return coinId;
//        }
//
//        public void setCoinId(int coinId) {
//            this.coinId = coinId;
//        }
//
//        public String getAccountId() {
//            return accountId;
//        }
//
//        public void setAccountId(String accountId) {
//            this.accountId = accountId;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//    }
}
