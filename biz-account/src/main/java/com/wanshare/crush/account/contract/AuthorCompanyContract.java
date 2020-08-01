package com.wanshare.crush.account.contract;

import android.content.Context;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.CompanyBean;
import com.wanshare.crush.account.model.bean.CompanyReq;

import java.util.List;

public class AuthorCompanyContract {

    public interface View extends IView {

        void onCompanyResult();

        void resultUploadFile(List<String> list);

        void resultQueryCompany(CompanyBean bean);

    }

    public interface Presenter extends IPresenter {

        boolean isAuthor(String status);

        boolean isRefuse(String status);

        int getIvStatus(String status);

        int getTvStatus(String status);

        void company(CompanyReq req);

        void uploadFile(List<String> list);

        void queryCompany();
    }


}
