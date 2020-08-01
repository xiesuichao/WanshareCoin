package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;

/**
 * 登录
 * </br>
 * Date: 2018/7/21 17:46
 */
public class BaseCodeContract {


    public interface View extends IView {
        void onCountDown(int time);

        void onCountDownFinish();

        void onSendVerifyCodeSuccess();
    }

    public interface Presenter extends IPresenter {


        void sendVerificationCode(AccountVerificationReq req);

        void startCountDown();

    }

}
