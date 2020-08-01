package com.wanshare.crush.asset.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.asset.contract.WithdrawRecordContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.WithdrawRecordBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * @author wangdunwei
 * @date 2018/8/23
 */
public class WithdrawRecordPresenter extends BasePresenter<WithdrawRecordContract.View>
        implements WithdrawRecordContract.Presenter {
    private final AssetServer assetServer;

    public WithdrawRecordPresenter(WithdrawRecordContract.View view) {
        super(view);
        assetServer = ApiClient.getInstance().create(AssetServer.class);
    }

    @Override
    public void requestWithdrawRecord(int currentPage) {
        assetServer.getWithdrawRecord(currentPage)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .unsubscribeOn(Schedulers.io())
                   .subscribe(new ErrorHandleObserver<WithdrawRecordBean>(this) {
                       @Override
                       public void onNext(WithdrawRecordBean entity) {
                           super.onNext(entity);
                           mRootView.showWithdrawRecord(entity);
                       }
                   });
    }
}
