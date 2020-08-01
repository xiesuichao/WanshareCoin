package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.constant.ThresholdConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.LoginContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.LoginAuthBean;
import com.wanshare.crush.account.model.bean.LoginBean;
import com.wanshare.crush.account.model.bean.LoginReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.wscomponent.logger.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.BiFunction;
import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;


/**
 * Presenter层实现
 * </br>
 * Date: 2018/7/21 17:47
 *
 * @author hemin
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    public LoginPresenter(LoginContract.View rootView) {
        super(rootView);
    }

    @Override
    public void login(LoginReq loginReq) {
        ApiClient.getInstance().create(AccountServer.class).login(BaseRequestBody.create(loginReq))
                .compose(RxSchedulers.<LoginBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<LoginBean>(this) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        super.onNext(loginBean);
                        UserInfoManager.getInstance().putToken(loginBean.getToken());
                        if (mRootView != null) {
                            mRootView.onLoginSuccess();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        super.onError(e);
                        String errorBody = getErrorBody(e);
                        if (!TextUtils.isEmpty(errorBody)) {
                            SecondVerifyParams secondVerifyParams = handleSecondVerifyResponse(errorBody);
                            if (secondVerifyParams != null) {
                                mRootView.onSecondVerify(secondVerifyParams);
                            }
                        } else {
                            super.onError(e);
                        }

                    }
                });
    }

    @Override
    public boolean check(String username, String password) {
        if (mRootView == null) {
            return false;
        }
        if (TextUtils.isEmpty(username)) {
            mRootView.showToast(R.string.account_please_input_username);
        } else if (TextUtils.isEmpty(password)) {
            mRootView.showToast(R.string.account_please_input_password);
        } else if (password.length() < ThresholdConstants.LOGIN_PASSWORD_SHORTEST_LENGTH) {
            mRootView.showToast(R.string.account_pwd_length_at_least_eight);
        } else {
            return true;
        }
        return false;
    }


    private SecondVerifyParams handleSecondVerifyResponse(String bodyStr) {
        SecondVerifyParams secondVerifyParams = new SecondVerifyParams();
        secondVerifyParams.setVerifyType(VerifyTypeConstants.LOGIN);
        try {
            LoginAuthBean loginAuthBean = new Gson().fromJson(bodyStr, LoginAuthBean.class);
            secondVerifyParams.setBaseToken(loginAuthBean.getBaseToken());
            if (loginAuthBean.getBindStatusBean() != null) {
                AccountCacheManager.getInstance().putBindStatusBean(loginAuthBean.getBindStatusBean());
            }
            if (loginAuthBean.getAuthMessage().getPhoneNumber() != null) {
                UserInfoManager.getInstance().putPhone(loginAuthBean.getAuthMessage().getPhoneNumber().getPhoneNumber());
            }
            if (loginAuthBean.getAuthMessage().getEmail() != null) {
                UserInfoManager.getInstance().putEmail(loginAuthBean.getAuthMessage().getEmail().getEmail());
            }
            UserInfoManager.getInstance().putAccountId(loginAuthBean.getAuthMessage().getAccountId());
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
            secondVerifyParams = null;
        }

        return secondVerifyParams;
    }

    private void getUserInfo() {
        ApiClient.getInstance().create(AccountServer.class).getAccountInfo()
                .zipWith(ApiClient.getInstance().create(AccountServer.class).getBindStatus(), new BiFunction<AccountInfoBean, BindStatusBean, List<Object>>() {
                    @Override
                    public List<Object> apply(AccountInfoBean accountInfoBean, BindStatusBean bindStatusBean) {
                        List<Object> result = new ArrayList<>();
                        result.add(accountInfoBean);
                        result.add(bindStatusBean);
                        return result;
                    }
                })
                .compose(RxSchedulers.<List<Object>>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<List<Object>>(this) {
                    @Override
                    public void onNext(List<Object> result) {
                        super.onNext(result);
//                        UserInfoManager.getInstance().putIsEmailAuthEnable(bindStatusBean.getEmailAuthenticator() == 1);
//                        UserInfoManager.getInstance().putIsPhoneAuthEnable(bindStatusBean.getPhoneAuthenticator() == 1);
//                        UserInfoManager.getInstance().putIsGoogleAuthEnable(bindStatusBean.getGoogleAuthenticator() == 1);
                    }
                });
    }

}
