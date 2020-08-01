package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.GoogleStartContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.GoogleStartReq;
import com.wanshare.crush.account.model.bean.LoginAuthBean;
import com.wanshare.common.biz.account.model.SecondVerifyParams;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


public class GoogleStartPresenter extends BasePresenter<GoogleStartContract.View> implements GoogleStartContract.Presenter {

    public GoogleStartPresenter(GoogleStartContract.View rootView) {
        super(rootView);
    }

    @Override
    public void bindGoogleAuthenticator(GoogleStartReq req) {
        ApiClient.getInstance().create(AccountServer.class).bindGoogleAuthenticator(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object google) {
                        super.onNext(google);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultBindGoogle(google);
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
                        SecondVerifyParams params = new SecondVerifyParams();
                        params.setVerifyType(VerifyTypeConstants.BIND_GOOGLE);
                        params.setBaseToken(loginAuthBean.getBaseToken());
                        mRootView.resultBindSecond(params);
                    }
                });
    }

    @Override
    public void unbindGoogleAuthenticator(String code) {

    }

    @Override
    public void openGoogleAuthenticator(GoogleStartReq req) {
        ApiClient.getInstance().create(AccountServer.class).openGoogleAuthenticator(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object google) {
                        super.onNext(google);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultOpenGoogle();
                    }

                });
    }


}
