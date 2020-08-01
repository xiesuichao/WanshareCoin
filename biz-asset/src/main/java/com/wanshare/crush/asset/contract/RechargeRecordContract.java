package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.RechargeRecordBean;

/**
 * @author wangdunwei
 * @date 2018/8/23
 */
public class RechargeRecordContract {
    public interface Presenter extends IPresenter{
        void requestRechargeRecord(int currentPage);
    }

    public interface View extends IView{
        void showRechargeRecord(RechargeRecordBean rechargeRecordBean);
    }
}
