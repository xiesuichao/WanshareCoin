package com.wanshare.crush.asset.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.asset.contract.RechargeRecordContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.RechargeRecordBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * @author wangdunwei
 * @date 2018/8/23
 */
public class RechargeRecordPresenter extends BasePresenter<RechargeRecordContract.View>
        implements RechargeRecordContract.Presenter {
    private final AssetServer assetServer;

    public RechargeRecordPresenter(RechargeRecordContract.View view) {
        super(view);
        assetServer = ApiClient.getInstance().create(AssetServer.class);
    }

    @Override
    public void requestRechargeRecord(int currentPage) {
        assetServer.getRechargeRecord(currentPage)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .unsubscribeOn(Schedulers.io())
                   .subscribe(new ErrorHandleObserver<RechargeRecordBean>(this) {
                       @Override
                       public void onNext(RechargeRecordBean rechargeRecordBean) {
                           super.onNext(rechargeRecordBean);
                           mRootView.showRechargeRecord(rechargeRecordBean);
                       }
                   });
    }
}
