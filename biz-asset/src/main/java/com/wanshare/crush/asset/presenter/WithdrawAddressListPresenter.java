package com.wanshare.crush.asset.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.asset.contract.WithdrawAddressListContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * @author wangdunwei
 * @date 2018/8/20
 */
public class WithdrawAddressListPresenter extends BasePresenter<WithdrawAddressListContract.View>
        implements WithdrawAddressListContract.Presenter {
    private AssetServer assetServer;

    public WithdrawAddressListPresenter(WithdrawAddressListContract.View view) {
        super(view);
        assetServer = ApiClient.getInstance().create(AssetServer.class);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void requestWithdrawAddressList(String coinId, int page) {
        assetServer.getWithdrawAddressList(coinId, page)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .unsubscribeOn(Schedulers.io())
                   .subscribe(new ErrorHandleObserver<WithdrawAddress>(this) {
                       @Override
                       public void onNext(WithdrawAddress withdrawAddress) {
                           super.onNext(withdrawAddress);
                           mRootView.showWithdrawAddressList(withdrawAddress);
                       }
                   });
    }

    @Override
    public void deleteWithdrawAddress(String coinId) {
        assetServer.deleteWithdrawAddress(coinId)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .unsubscribeOn(Schedulers.io())
                   .subscribe(new ErrorHandleObserver<Object>(this) {
                       @Override
                       public void onNext(Object obj) {
                           super.onNext(obj);
                           mRootView.onDeleteAddressSucceed();
                       }
                   });
    }
}
