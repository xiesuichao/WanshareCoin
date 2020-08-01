package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.BindStatusBean;

public class PhoneManagerContract {

    public interface View extends IView{
        void resultSetBindStatus(Object object);
    }

    public interface Presenter extends IPresenter{
        void setBindStatus(BindStatusBean req);
    }


}
