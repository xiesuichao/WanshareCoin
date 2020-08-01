package com.wanshare.crush.asset.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.asset.contract.WithdrawSelectAddressContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;
import com.wanshare.wscomponent.logger.LogUtil;

import com.wanshare.wscomponent.http.ApiClient;

/**
 * $todo类的描述$
 * </br>
 * Date: 2018/8/27 20:00
 *
 * @author hemin
 */
public class WithdrawSelectAddressPresenter extends BasePresenter<WithdrawSelectAddressContract.View> implements WithdrawSelectAddressContract.Presenter {
    public WithdrawSelectAddressPresenter(WithdrawSelectAddressContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getWithdrawAddresses(String coinId,int page) {
        ApiClient.getInstance().create(AssetServer.class)
                .getWithdrawAddressList(coinId,page)
                .compose(RxSchedulers.<WithdrawAddress>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<WithdrawAddress>(this) {

                    @Override
                    public void onNext(WithdrawAddress result) {
                        if (mRootView == null) {
                            return;
                        }
                        LogUtil.d("result:"+result);
                        mRootView.showWithdrawAddresses(result);
                    }
                });
    }
}
