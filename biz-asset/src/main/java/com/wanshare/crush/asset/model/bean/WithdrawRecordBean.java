package com.wanshare.crush.asset.model.bean;

import java.util.List;

/**
 * @author wangdunwei
 * @date 2018/8/24
 */
public class WithdrawRecordBean {

    /**
     * meta : {"totalCount":9,"pageNo":1,"pageSize":10}
     * query : {"accountId":"10086","coinId":20}
     * withdrawRecords : [{"id":"10","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-13T08:27:18Z","completedAt":"2018-09-13T08:27:18Z","amount":"9999.00000000","withdrawStatus":"reviewing"},{"id":"9","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-13T08:26:11Z","completedAt":"2018-09-13T08:26:11Z","amount":"999.00000000","withdrawStatus":"reviewing"},{"id":"8","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-13T08:25:33Z","completedAt":"2018-09-13T08:25:33Z","amount":"999.00000000","withdrawStatus":"reviewing"},{"id":"7","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-13T08:24:23Z","completedAt":"2018-09-13T08:24:23Z","amount":"999.00000000","withdrawStatus":"reviewing"},{"id":"5","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-07T03:54:18Z","completedAt":"2018-09-07T03:15:05Z","amount":"20.00000000","address":"1","txid":"1","withdrawStatus":"cancle"},{"id":"4","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-07T03:54:18Z","completedAt":"2018-09-07T03:15:05Z","amount":"20.00000000","address":"1","txid":"1","withdrawStatus":"cancle"},{"id":"3","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-07T03:54:18Z","completedAt":"2018-09-07T03:15:05Z","amount":"20.00000000","address":"1","txid":"1","withdrawStatus":"cancle"},{"id":"2","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-07T03:54:18Z","completedAt":"2018-09-07T03:15:05Z","amount":"20.00000000","address":"1","txid":"1","withdrawStatus":"reviewing"},{"id":"1","coinId":"20","shortName":"USDT","logo":"logo.usdt","confirmationNumber":20,"submittedAt":"2018-09-07T03:54:18Z","completedAt":"2018-09-07T03:15:05Z","amount":"20.00000000","address":"1","txid":"1","withdrawStatus":"auditfailure","confirmedTimes":10,"remark":"123"}]
     */

    private MetaBean meta;
    private List<AssetRecord> withdrawRecords;

    public MetaBean getMeta() {
        return meta;
    }

    public void setMeta(MetaBean meta) {
        this.meta = meta;
    }

    public List<AssetRecord> getWithdrawRecords() {
        return withdrawRecords;
    }

    public void setWithdrawRecords(List<AssetRecord> withdrawRecords) {
        this.withdrawRecords = withdrawRecords;
    }

    public static class MetaBean {
        /**
         * totalCount : 9
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
