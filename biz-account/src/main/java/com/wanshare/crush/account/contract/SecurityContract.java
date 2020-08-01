package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.BindStatusBean;

public class SecurityContract {

    public interface View extends IView {
        void resultBindStatus(BindStatusBean bean);
    }

    public interface Presenter extends IPresenter {
        void getBindStatus();
    }
}
