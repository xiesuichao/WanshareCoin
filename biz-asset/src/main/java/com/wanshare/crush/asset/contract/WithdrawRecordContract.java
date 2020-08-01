package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.WithdrawRecordBean;

/**
 * @author wangdunwei
 * @date 2018/8/23
 */
public class WithdrawRecordContract {
    public interface Presenter extends IPresenter{
        void requestWithdrawRecord(int currentPage);
    }

    public interface View extends IView{
        void showWithdrawRecord(WithdrawRecordBean withdrawRecordBean);
    }
}
