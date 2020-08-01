package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.constant.ThresholdConstants;
import com.wanshare.crush.account.contract.SetPasswordContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;
import com.wanshare.wscomponent.logger.LogUtil;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Created by Richard on 2018/8/23
 */
public class SetPasswordPresenter extends BasePresenter<SetPasswordContract.View> implements SetPasswordContract.Presenter {

    public SetPasswordPresenter(SetPasswordContract.View rootView) {
        super(rootView);
    }

    @Override
    public boolean check(String password, String confirmPassword) {
        if (mRootView == null) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mRootView.showToast(R.string.account_please_input_new_password);
        } else if (TextUtils.isEmpty(confirmPassword)) {
            mRootView.showToast(R.string.account_please_confirm_new_password);
        } else if (password.length() < ThresholdConstants.LOGIN_PASSWORD_SHORTEST_LENGTH) {
            mRootView.showToast(R.string.account_pwd_length_at_least_eight);
        } else if (!TextUtils.equals(password, confirmPassword)) {
            mRootView.showToast(R.string.account_two_passwords_not_equal);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void secondVerify(SecondVerifyReq secondVerifyReq) {
        ApiClient.getInstance().create(AccountServer.class).verify(BaseRequestBody.create(secondVerifyReq))
                .compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this) {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        super.onNext(jsonObject);
                        mRootView.onVerifySuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorBody = getErrorBody(e);
                        if (!TextUtils.isEmpty(errorBody)) {
                            try {

                            } catch (Exception e1) {
                                mRootView.showHintMessage(e.getMessage());
                                LogUtil.e(e1.getMessage());
                            }
                        } else {
                            super.onError(e);
                        }
                    }
                });
    }
}
