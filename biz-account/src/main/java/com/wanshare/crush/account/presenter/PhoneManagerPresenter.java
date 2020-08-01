package com.wanshare.crush.account.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.contract.PhoneManagerContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.BindStatusBean;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


public class PhoneManagerPresenter extends BasePresenter<PhoneManagerContract.View> implements PhoneManagerContract.Presenter{

    public PhoneManagerPresenter(PhoneManagerContract.View rootView) {
        super(rootView);
    }

    @Override
    public void setBindStatus(BindStatusBean req) {
        ApiClient.getInstance().create(AccountServer.class).setBindStatue(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultSetBindStatus(object);
                    }
                });
    }
}
