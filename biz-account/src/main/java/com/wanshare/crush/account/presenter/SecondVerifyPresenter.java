package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.contract.SecondVerifyContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


public class SecondVerifyPresenter extends BaseCodePresenter<SecondVerifyContract.View> implements SecondVerifyContract.Presenter {


    public SecondVerifyPresenter(SecondVerifyContract.View rootView) {
        super(rootView);
    }

    @Override
    public void commit(SecondVerifyReq req) {
        if (mRootView == null) {
            return;
        }

        if (TextUtils.isEmpty(req.getCode())) {
            mRootView.showToast(R.string.account_please_input_verification_code);
        } else {
            verify(req);
        }
    }

    private void verify(final SecondVerifyReq req) {
        ApiClient.getInstance().create(AccountServer.class).verify(BaseRequestBody.create(req))
                .compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this) {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        super.onNext(jsonObject);
                        if (mRootView != null) {
                            mRootView.onVerifySuccess(jsonObject);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (mRootView == null) {
                            return;
                        }
                        String errorBody = getErrorBody(e);
                        if (!TextUtils.isEmpty(errorBody)) {
                            JsonObject jsonObject = new Gson().fromJson(errorBody, JsonObject.class);
                            if (jsonObject != null) {
                                mRootView.onVerifySuccess(jsonObject);
                            } else {
                                mRootView.showHintMessage(e.getMessage());
                            }
                        } else {
                            super.onError(e);
                        }
                    }
                });
    }
}
