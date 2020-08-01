package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;

/**
 * Created by Richard on 2018/8/23
 */
public class SetPasswordContract {
    public interface View extends IView {
        void showToast(int msgId);

        void showToast(String msg);

        void onVerifySuccess();
    }

    public interface Presenter extends IPresenter {
        boolean check(String password, String confirmPassword);
        void secondVerify(SecondVerifyReq secondVerifyReq);
    }


}
