package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.LoginReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;

/**
 * 登录
 * </br>
 * Date: 2018/7/21 17:46
 */
public class LoginContract {


    public interface View extends IView {

        void showToast(int msg);

        void showToast(String msg);

        void onSecondVerify(SecondVerifyParams secondVerifyParams);

        void onLoginSuccess();

    }

    public interface Presenter extends IPresenter {
        void login(LoginReq loginReq);
        boolean check(String username,String password);
    }

}
