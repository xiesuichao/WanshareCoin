package com.wanshare.crush.account.contract;

import android.content.res.AssetManager;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.account.model.bean.Country;

import java.util.List;

/**
 * Created by Richard on 2018/8/27
 */
public class SelectContryContract {
    public interface View extends IView {
        void showCountryList(List<Country> list);
        void getCountryListFailed();
    }

    public interface Presenter extends IPresenter {
        void getCountryList(AssetManager assets);
    }
}
