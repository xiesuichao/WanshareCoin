package com.wanshare.crush.account.contract;

import com.google.gson.JsonObject;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.GoogleStartReq;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;

public class GoogleOperationContract {

    public interface View extends BaseCodeContract.View {

        void resultCloseGoogle(Object object);

        void resultBindStatus(BindStatusBean bean);

        void onVerifySuccess(JsonObject response);

        void resultModifyGoogle();
    }

    public interface Presenter extends BaseCodeContract.Presenter{

        void closeGoogleAuthenticator(OperationVerifyReq req);

        void getBindStatus();

        void verify(SecondVerifyReq req);

        void modifyGoogleAuthenticator(GoogleStartReq req, SecondVerifyReq second);
    }

}
