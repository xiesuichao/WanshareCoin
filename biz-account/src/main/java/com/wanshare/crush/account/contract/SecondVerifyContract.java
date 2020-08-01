package com.wanshare.crush.account.contract;

import com.google.gson.JsonObject;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;

public class SecondVerifyContract {

    public interface View extends BaseCodeContract.View {
        void showToast(int msgId);
        void onVerifySuccess(JsonObject response);
    }

    public interface Presenter extends BaseCodeContract.Presenter {
        void commit(SecondVerifyReq req);

    }

}
