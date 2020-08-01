package com.wanshare.crush.account.presenter;

import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.contract.PhoneStartContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


public class PhoneStartPresenter extends BaseCodePresenter<PhoneStartContract.View> implements PhoneStartContract.Presenter{

    public PhoneStartPresenter(PhoneStartContract.View rootView) {
        super(rootView);
    }

    @Override
    public void openPhone(OperationVerifyReq req) {
        ApiClient.getInstance().create(AccountServer.class).openPhone(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultOpenPhone();
                    }
                });
    }
}
