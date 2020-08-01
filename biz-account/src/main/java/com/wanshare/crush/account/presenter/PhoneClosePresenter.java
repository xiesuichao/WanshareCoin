package com.wanshare.crush.account.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.contract.PhoneCloseContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


public class PhoneClosePresenter extends BasePresenter<PhoneCloseContract.View> implements PhoneCloseContract.Presenter{


    public PhoneClosePresenter(PhoneCloseContract.View rootView) {
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

    @Override
    public void closePhone(OperationVerifyReq req) {
        ApiClient.getInstance().create(AccountServer.class).closePhone(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultClosePhone();
                    }
                });
    }

    @Override
    public void sendVerificationCode(AccountVerificationReq req) {
        ApiClient.getInstance().create(AccountServer.class).sendVerificationCode(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object topExchange) {
                        super.onNext(topExchange);
                        mRootView.resultVerifyCode();
                    }
                });
    }


}
