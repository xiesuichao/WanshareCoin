package com.wanshare.crush.asset.model.bean;

import java.util.List;

/**
 * @author wangdunwei
 * @date 2018/8/24
 */
public class RechargeRecordBean {

    /**
     * meta : {"totalCount":7,"pageNo":1,"pageSize":10}
     * query : {"coinId":20,"accountId":"10086"}
     * rechargeRecords : [{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-07T03:36:08Z","amount":"5.00000000","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"5","status":"successed","confirmedTimes":20},{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-07T03:36:08Z","amount":"4.00000000","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"4","status":"successed","confirmedTimes":20},{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-07T03:36:08Z","amount":"3.00000000","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"3","status":"successed","confirmedTimes":20},{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-07T03:36:08Z","amount":"2.00000000","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"2","status":"successed","confirmedTimes":20},{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-07T03:36:08Z","amount":"1.00000000","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"1","status":"successed","confirmedTimes":20},{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-07T03:36:08Z","amount":"0E-8","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"0","status":"successed","confirmedTimes":20},{"coinId":"20","shortName":"usdt","logo":"logo.usdt","confirmationNumber":20,"completedAt":"2018-09-13T03:55:31Z","amount":"100.00000000","address":"0x2ff7c9fa892672511cf54c1bb4f06d842314f3b5","txid":"0xcf2bd0bd53b46da216ad24095990e8e5e11a95d4b81662af1020b8af5e863264","status":"successed","confirmedTimes":30}]
     */

    private MetaBean meta;
    private List<AssetRecord> rechargeRecords;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<AssetRecord> getRechargeRecords() {
        return rechargeRecords;
    }

    public void setRechargeRecords(List<AssetRecord> rechargeRecords) {
        this.rechargeRecords = rechargeRecords;
    }

    public static class MetaBean {
        /**
         * totalCount : 7
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
}
