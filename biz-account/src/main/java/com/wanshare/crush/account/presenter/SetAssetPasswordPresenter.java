package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.constant.ThresholdConstants;
import com.wanshare.crush.account.contract.SetAssetPasswordContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.SetAssetPasswordReq;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Created by Richard on 2018/8/25
 */
public class SetAssetPasswordPresenter extends BasePresenter<SetAssetPasswordContract.View> implements SetAssetPasswordContract.Presenter {

    public SetAssetPasswordPresenter(SetAssetPasswordContract.View rootView) {
        super(rootView);
    }

    @Override
    public boolean checkout(String loginPassword, String assetPassword, String confirmAssetPassword) {
        if (mRootView == null) {
            return false;
        }
        if (TextUtils.isEmpty(loginPassword)) {
            mRootView.showToast(R.string.account_please_input_login_password);
        } else if (TextUtils.isEmpty(assetPassword)) {
            mRootView.showToast(R.string.account_please_input_fund_password);
        } else if (TextUtils.isEmpty(confirmAssetPassword)) {
            mRootView.showToast(R.string.account_please_confirm_fund_password);
        } else if (assetPassword.length() < ThresholdConstants.ASSET_PASSWORD_SHORTEST_LENGTH) {
            mRootView.showToast(R.string.account_asset_pwd_length_at_least_eight);
        } else if (!TextUtils.equals(assetPassword, confirmAssetPassword)) {
            mRootView.showToast(R.string.account_asset_password_not_equal);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void setAssetPassword(SetAssetPasswordReq setAssetPasswordReq) {
        ApiClient.getInstance().create(AccountServer.class).setAssetPassword(BaseRequestBody.create(setAssetPasswordReq))
                .compose(RxSchedulers.<JsonObject>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<JsonObject>(this) {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        super.onNext(jsonObject);
                        UserInfoManager.getInstance().putHasAssetPassword(true);
                        mRootView.onSetAssetPasswordSuccess();
                    }

                });
    }
}
