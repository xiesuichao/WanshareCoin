package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.constant.ThresholdConstants;
import com.wanshare.crush.account.contract.RegisterContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.LoginBean;
import com.wanshare.crush.account.model.bean.RegisterReq;
import com.wanshare.wscomponent.utils.Preconditions;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Created by Richard on 2018/8/22
 */
public class RegisterPresenter extends BaseCodePresenter<RegisterContract.View> implements RegisterContract.Presenter {

    public RegisterPresenter(RegisterContract.View rootView) {
        super(rootView);
    }


    @Override
    public boolean checkAccount(String account) {
        if (mRootView == null) {
            return false;
        }
        if (TextUtils.isEmpty(account)) {
            mRootView.showToast(R.string.account_email_null_tips);
            return false;
        }
        if (!Preconditions.isEmail(account)) {
            mRootView.showToast(R.string.account_email_is_illegal);
            return false;
        }
        return true;
    }


    @Override
    public void register(RegisterReq registerReq, boolean isAgreeTerms) {
        if (mRootView == null) {
            return;
        }
        if (TextUtils.isEmpty(registerReq.getCountry())) {
            mRootView.showToast(R.string.account_please_select_zone);
            return;
        }
        if (TextUtils.isEmpty(registerReq.getEmail())) {
            mRootView.showToast(R.string.account_email_null_tips);
            return;
        }
        if (!Preconditions.isEmail(registerReq.getEmail())) {
            mRootView.showToast(R.string.account_email_is_illegal);
            return;
        }
        if (TextUtils.isEmpty(registerReq.getVerificationCode())) {
            mRootView.showToast(R.string.account_code_email_null_tips);
            return;
        }
        if (!checkPassword(registerReq.getPassword())) {
            return;
        }
        if (!isAgreeTerms) {
            mRootView.showToast(R.string.account_please_agree_to_terms);
            return;
        }
        if (TextUtils.isEmpty(registerReq.getChallenge()) || TextUtils.isEmpty(registerReq.getValidate()) || TextUtils.isEmpty(registerReq.getSeccode())) {
            mRootView.showToast(R.string.account_please_click_get_verification_code);
            return;
        }
        postRegister(registerReq);
    }

    private void postRegister(RegisterReq registerReq) {
        ApiClient.getInstance().create(AccountServer.class).register(BaseRequestBody.create(registerReq))
                .compose(RxSchedulers.<LoginBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<LoginBean>(this) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        super.onNext(loginBean);
                        UserInfoManager.getInstance().putToken(loginBean.getToken());
                        if (mRootView != null) {
                            mRootView.onRegisterSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (mRootView != null) {
                            mRootView.onRegisterFail();
                        }
                    }
                });
    }

    private boolean checkPassword(String password) {
        String regexZ = "\\d*";
        String regexS = "[a-zA-Z]+";
        String regexT = "\\W+$";
        String regexZT = "\\D*";
        String regexST = "[\\d\\W]*";
        String regexZS = "\\w*";
        String regexZST = "[\\w\\W]*";

        if (TextUtils.isEmpty(password)) {
            mRootView.showToast(R.string.account_please_input_password);
            return false;
        }

        if (password.length() < ThresholdConstants.LOGIN_PASSWORD_SHORTEST_LENGTH) {
            mRootView.showToast(R.string.account_pwd_length_at_least_eight);
            return false;
        }

        if (password.matches(regexZ)) {
            mRootView.showToast(R.string.account_password_is_weak);
            return false;
        }
        if (password.matches(regexS)) {
            mRootView.showToast(R.string.account_password_is_weak);
            return false;
        }
        if (password.matches(regexT)) {
            mRootView.showToast(R.string.account_password_is_weak);
            return false;
        }
//        if (password.matches(regexZT)) {
//            return Strength.MIDUM;
//        }
//        if (password.matches(regexST)) {
//            return Strength.MIDUM;
//        }
//        if (password.matches(regexZS)) {
//            return Strength.MIDUM;
//        }
//        if (password.matches(regexZST)) {
//            return Strength.STRONG;
//        }
        return true;
    }

}
