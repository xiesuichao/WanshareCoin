package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.crush.account.contract.ForgotPasswordContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.ResetPasswordReq;
import com.wanshare.wscomponent.logger.LogUtil;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Created by Richard on 2018/8/23
 */
public class ForgotPasswordPresenter extends BasePresenter<ForgotPasswordContract.View> implements ForgotPasswordContract.Presenter {

    public ForgotPasswordPresenter(ForgotPasswordContract.View rootView) {
        super(rootView);
    }


    @Override
    public void resetPassword(ResetPasswordReq req) {
        ApiClient.getInstance().create(AccountServer.class).resetPassword(BaseRequestBody.create(req))
                .compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this) {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        super.onNext(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorBody = getErrorBody(e);
                        if (!TextUtils.isEmpty(errorBody)) {
                            try {
                                JsonObject jsonObject = new Gson().fromJson(errorBody, JsonObject.class);
                                String baseToken = jsonObject.get(ApiParamConstants.KEY_BASE_TOKEN).getAsString();
                                String uri = jsonObject.get(ApiParamConstants.KEY_URI).getAsString();
                                mRootView.onSecondVerify(baseToken, uri);
                            } catch (Exception e1) {
                                mRootView.showHintMessage(e.getMessage());
                                LogUtil.e(e1.getMessage());
                            }
                        }else{
                            super.onError(e);
                        }
                    }
                });
    }
}
