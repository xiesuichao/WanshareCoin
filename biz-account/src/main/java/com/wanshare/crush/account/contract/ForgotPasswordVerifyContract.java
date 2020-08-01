package com.wanshare.crush.account.contract;

import com.wanshare.crush.account.model.bean.ResetPasswordReq;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;

/**
 * Created by Richard on 2018/8/23
 */
public class ForgotPasswordVerifyContract {

    public interface View extends BaseCodeContract.View {

        void showToast(int msgId);

        void onThirdVerify(String secondToken);
    }

    public interface Presenter extends BaseCodeContract.Presenter {

        void secondVerify(SecondVerifyReq req);

    }
}
