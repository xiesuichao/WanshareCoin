package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;

public class PhoneCloseContract {

    public interface View extends IView{

        void resultBindStatus(BindStatusBean bean);

        void resultClosePhone();

        void resultVerifyCode();

    }

    public interface Presenter extends IPresenter {

        void getBindStatus();

        void closePhone(OperationVerifyReq req);

        void sendVerificationCode(AccountVerificationReq req);


    }

}
