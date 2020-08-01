package com.wanshare.crush.asset.model.bean;

import java.util.List;

public class UserAsserts {


    /**
     * meta : {"totalCount":16,"pageNo":1,"pageSize":10}
     * query : {"accountId":"10086","hidingEmptyAsset":false}
     * estimates : {"usdt":0,"btc":0,"cny":0}
     * assetInfo : [{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"3","logo":"xxx/xxx.png","warning":"xxxxxxxxxxxxxx","shortName":"test2","fullName":"TEST2","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"4","logo":"xxx/xxx.png","warning":"","shortName":"test3","fullName":"TEST3","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"5","logo":"xxx/xxx.png","warning":"xxxxxxxxxxxxxx","shortName":"test4","fullName":"TEST4","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"6","logo":"xxx/xxx.png","warning":"","shortName":"test5","fullName":"TEST5","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"7","logo":"xxx/xxx.png","warning":"xxxxxxxxxxxxxx","shortName":"test6","fullName":"TEST6","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"8","logo":"xxx/xxx.png","warning":"","shortName":"test7","fullName":"TEST7","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"9","logo":"xxx/xxx.png","warning":"","shortName":"test8","fullName":"TEST8","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"999940.85430000","balance":"822756.85430000","frozen":"177184.00000000","coinId":"10","logo":"logo.btc","warning":"wqeqw","shortName":"test9","fullName":"TEST9","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"11","logo":"xxx/xxx.png","warning":"","shortName":"test10","fullName":"TEST10","isAllowRecharge":true,"isAllowWithdraw":true},{"total":"1003595.37268542","balance":"938089.22853000","frozen":"65506.14415542","coinId":"13","logo":"xxx/xxx.png","warning":"","shortName":"test11","fullName":"TEST11","isAllowRecharge":true,"isAllowWithdraw":true}]
     */

    private MetaBean meta;
    private QueryBean query;
    private EstimatesBean estimates;
    private List<AssetInfoBean> assetInfo;

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

    public EstimatesBean getEstimates() {
        return estimates;
    }

    public void setEstimates(EstimatesBean estimates) {
        this.estimates = estimates;
    }

    public List<AssetInfoBean> getAssetInfo() {
        return assetInfo;
    }

    public void setAssetInfo(List<AssetInfoBean> assetInfo) {
        this.assetInfo = assetInfo;
    }

    public static class MetaBean {
        /**
         * totalCount : 16
         * pageNo : 1
         * pageSize : 10
         */

        private int totalCount;
        private int pageNo;
        private int pageSize;

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

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

    public static class QueryBean {
        /**
         * accountId : 10086
         * hidingEmptyAsset : false
         */

        private String accountId;
        private boolean hidingEmptyAsset;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public boolean isHidingEmptyAsset() {
            return hidingEmptyAsset;
        }

        public void setHidingEmptyAsset(boolean hidingEmptyAsset) {
            this.hidingEmptyAsset = hidingEmptyAsset;
        }
    }

}
