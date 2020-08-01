package com.wanshare.crush.asset.presenter;

import com.google.gson.JsonObject;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.asset.contract.AssetsDetailContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.logger.LogUtil;



/**
 * Created by Richard on 2018/8/29
 */
public class AssetsDetailPresenter extends BasePresenter<AssetsDetailContract.View> implements AssetsDetailContract.Presenter {
    public AssetsDetailPresenter(AssetsDetailContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getAsset(String coinId) {
        ApiClient.getInstance().create(AssetServer.class)
                .getAssetByCoinId(coinId)
                .compose(RxSchedulers.<AssetByCoinBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<AssetByCoinBean>(this) {

                    @Override
                    public void onNext(AssetByCoinBean result) {
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.showAsset(result);
                    }
                });
    }
}
