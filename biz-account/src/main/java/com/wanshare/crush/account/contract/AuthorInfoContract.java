package com.wanshare.crush.account.contract;

import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;

public class AuthorInfoContract {

    public interface View extends IView {
        void resultAccountInfo(AccountInfoBean bean);
    }


    public interface Presenter extends IPresenter {

        int[] getStatusConfig(String status);

        boolean isEnter(String status);

        void getAccountInfo();

    }


}
