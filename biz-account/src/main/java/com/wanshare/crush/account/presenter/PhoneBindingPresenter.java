package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.PhoneBindingContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.BindPhoneReq;
import com.wanshare.crush.account.model.bean.LoginAuthBean;
import com.wanshare.common.biz.account.model.SecondVerifyParams;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

public class PhoneBindingPresenter extends BaseCodePresenter<PhoneBindingContract.View> implements PhoneBindingContract.Presenter{


    public PhoneBindingPresenter(PhoneBindingContract.View rootView) {
        super(rootView);
    }



    @Override
    public void bindPhone(BindPhoneReq req) {
        ApiClient.getInstance().create(AccountServer.class).bindPhone(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.onBindPhoneResult();
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
                        params.setVerifyType(VerifyTypeConstants.BIND_PHONE);
                        params.setBaseToken(loginAuthBean.getBaseToken());
                        mRootView.resultBindSecond(params);
                    }
                });
    }
}
