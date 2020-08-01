package com.wanshare.crush.account.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.GoogleInfoEntity;
import com.wanshare.crush.account.model.bean.GoogleQrReq;

import okhttp3.RequestBody;

public class GoogleQrContract {


    public interface View extends IView{

        void initGoogleauthResult(GoogleInfoEntity entity);

    }


    public interface Presenter extends IPresenter{


        void initGoogleauth(GoogleQrReq req);



    }





}
