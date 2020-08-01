package com.weshare.wanxiang.main.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.UserAsserts;

public class MinepageContract {

    public interface View extends IView{
        void showUserAsserts(UserAsserts userAsserts);

        void showUserInfo();
    }

    public interface Presenter extends IPresenter{
        void getUserAsserts(int page,int pageSize, String orderRule, String order, boolean isHidingEmptyAsset, String search);

        void getUserInfo();
    }
}
