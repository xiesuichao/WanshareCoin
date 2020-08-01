package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.crush.account.contract.ForgotPasswordVerifyContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;
import com.wanshare.wscomponent.logger.LogUtil;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Created by Richard on 2018/8/23
 */
public class ForgotPasswordVerifyPresenter extends BaseCodePresenter<ForgotPasswordVerifyContract.View> implements ForgotPasswordVerifyContract.Presenter {

    public ForgotPasswordVerifyPresenter(ForgotPasswordVerifyContract.View rootView) {
        super(rootView);
    }


    @Override
    public void secondVerify(SecondVerifyReq req) {
        ApiClient.getInstance().create(AccountServer.class).verify(BaseRequestBody.create(req))
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
                                String secondToken = jsonObject.get(ApiParamConstants.KEY_SECOND_TOKEN).getAsString();
                                mRootView.onThirdVerify(secondToken);
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
