package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.ResetPasswordReq;

/**
 * Created by Richard on 2018/9/18
 */
public class ForgotPasswordContract {

    public interface View extends IView {
        void onSecondVerify(String baseToken, String uri);

    }

    public interface Presenter extends IPresenter {
        void resetPassword(ResetPasswordReq req);
    }

}
