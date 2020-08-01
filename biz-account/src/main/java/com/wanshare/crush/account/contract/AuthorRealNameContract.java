package com.wanshare.crush.account.contract;

import android.content.Context;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.IndividualBean;
import com.wanshare.crush.account.model.bean.IndividualReq;
import com.wanshare.crush.account.model.bean.UploadBean;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Multipart;

public class AuthorRealNameContract {

    public interface View extends IView {

        void onIndividualResult();

        void resultUploadFile(List<String> list);

        void resultQueryIndividual(IndividualBean bean);

    }

    public interface Presenter extends IPresenter {

        boolean isAuthor(String status);

        boolean isRefuse(String status);

        int getIvStatus(String status);

        int getTvStatus(String status);

        void individual(IndividualReq req);

        void uploadFile(List<String> list);

        void queryIndividual();

    }


}
