package com.wanshare.crush.account.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.contract.SecurityContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.BindStatusBean;

import com.wanshare.wscomponent.http.ApiClient;


public class SecurityPresenter extends BasePresenter<SecurityContract.View> implements SecurityContract.Presenter{

    public SecurityPresenter(SecurityContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getBindStatus() {
        ApiClient.getInstance().create(AccountServer.class).getBindStatus()
                .compose(RxSchedulers.<BindStatusBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<BindStatusBean>(this) {
                    @Override
                    public void onNext(BindStatusBean google) {
                        super.onNext(google);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultBindStatus(google);
                    }
                });
    }
}
