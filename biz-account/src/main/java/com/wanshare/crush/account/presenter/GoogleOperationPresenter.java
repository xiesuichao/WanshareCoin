package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.GoogleOperationContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.GoogleStartReq;
import com.wanshare.crush.account.model.bean.LoginAuthBean;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


public class GoogleOperationPresenter extends BaseCodePresenter<GoogleOperationContract.View> implements GoogleOperationContract.Presenter{

    public GoogleOperationPresenter(GoogleOperationContract.View rootView) {
        super(rootView);
    }

    @Override
    public void closeGoogleAuthenticator(OperationVerifyReq req) {
        ApiClient.getInstance().create(AccountServer.class).closeGoogleAuthenticator(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object google) {
                        super.onNext(google);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultCloseGoogle(google);
                    }
                });
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
    public void verify(SecondVerifyReq req) {
        ApiClient.getInstance().create(AccountServer.class).verify(BaseRequestBody.create(req))
                .compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this) {
                    @Override
                    public void onNext(JsonObject jsonElement) {
                        super.onNext(jsonElement);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultModifyGoogle();
                    }
                });
    }

    @Override
    public void modifyGoogleAuthenticator(final GoogleStartReq req, final SecondVerifyReq second) {
        ApiClient.getInstance().create(AccountServer.class).modifyGoogleAuthenticator(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultModifyGoogle();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMsg = getErrorBody(e);
                        if (TextUtils.isEmpty(errorMsg)) {
                            super.onError(e);
                            return;
                        }
                        LoginAuthBean loginAuthBean = new Gson().fromJson(errorMsg, LoginAuthBean.class);
                        if (loginAuthBean == null) {
                            return;
                        }
                        second.setType(VerifyTypeConstants.ALTER_GOOGLE);
                        second.setBasetoken(loginAuthBean.getBaseToken());
                        verify(second);
                    }
                });

    }
}
