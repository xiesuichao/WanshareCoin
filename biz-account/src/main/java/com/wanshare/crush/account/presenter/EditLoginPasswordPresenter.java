package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.constant.ThresholdConstants;
import com.wanshare.crush.account.contract.EditLoginPasswordContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.SetLoginPasswordReq;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Created by Richard on 2018/8/24
 */
public class EditLoginPasswordPresenter extends BasePresenter<EditLoginPasswordContract.View> implements EditLoginPasswordContract.Presenter {

    public EditLoginPasswordPresenter(EditLoginPasswordContract.View rootView) {
        super(rootView);
    }


    @Override
    public boolean checkout(String oldPassword, String newPassword, String confirmNewPassword) {
        if (mRootView == null) {
            return false;
        }
        if (TextUtils.isEmpty(oldPassword)) {
            mRootView.showToast(R.string.account_please_input_old_password);
        } else if (TextUtils.isEmpty(newPassword)) {
            mRootView.showToast(R.string.account_please_input_new_password);
        } else if (TextUtils.isEmpty(confirmNewPassword)) {
            mRootView.showToast(R.string.account_please_confirm_new_password);
        } else if (newPassword.length() < ThresholdConstants.LOGIN_PASSWORD_SHORTEST_LENGTH) {
            mRootView.showToast(R.string.account_pwd_length_at_least_eight);
        } else if (!TextUtils.equals(newPassword, confirmNewPassword)) {
            mRootView.showToast(R.string.account_two_passwords_not_equal);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void setPassword(SetLoginPasswordReq req) {
        ApiClient.getInstance().create(AccountServer.class).editLoginPassword(BaseRequestBody.create(req))
                .compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this) {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        super.onNext(jsonObject);
                        mRootView.onEditLoginPasswordSuccess();
                    }

                });
    }


}
