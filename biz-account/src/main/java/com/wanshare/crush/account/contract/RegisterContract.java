package com.wanshare.crush.account.contract;

import com.wanshare.crush.account.model.bean.RegisterReq;

/**
 * Created by Richard on 2018/8/22
 */
public class RegisterContract {

    public interface View extends BaseCodeContract.View {
        void showToast(int msgId);

        void onRegisterSuccess();

        void onRegisterFail();
    }

    public interface Presenter extends BaseCodeContract.Presenter {
        boolean checkAccount(String account);

        void register(RegisterReq registerReq, boolean isAgreeTerms);
    }
}
