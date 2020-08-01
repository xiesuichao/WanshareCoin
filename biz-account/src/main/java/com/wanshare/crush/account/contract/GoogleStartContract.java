package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.GoogleStartReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;


public class GoogleStartContract {
    public interface View extends IView {

        void resultBindGoogle(Object entity);

        void resultUnBindGoogle(Object entity);

        void resultBindSecond(SecondVerifyParams params);

        void resultOpenGoogle();

    }


    public interface Presenter extends IPresenter {


        void bindGoogleAuthenticator(GoogleStartReq req);

        void unbindGoogleAuthenticator(String code);

        void openGoogleAuthenticator(GoogleStartReq req);


    }
}
