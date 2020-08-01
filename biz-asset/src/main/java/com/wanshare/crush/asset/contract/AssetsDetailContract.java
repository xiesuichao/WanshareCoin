package com.wanshare.crush.asset.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;

/**
 * Created by Richard on 2018/8/29
 */
public class AssetsDetailContract {
    public interface View extends IView {
        void showAsset(AssetByCoinBean bean);

    }

    public interface Presenter extends IPresenter {
        void getAsset(String coinId);
    }
}
