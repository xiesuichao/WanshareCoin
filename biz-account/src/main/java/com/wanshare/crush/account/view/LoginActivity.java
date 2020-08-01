package com.wanshare.crush.account.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.account.model.LoginEvent;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.LoginContract;
import com.wanshare.crush.account.model.bean.GeetestVerifyBean;
import com.wanshare.crush.account.model.bean.LoginReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.common.biz.account.model.SecondVerifyEvent;
import com.wanshare.crush.account.presenter.LoginPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 登录，View层实现
 * </br>
 * Date: 2018/7/21 17:45
 */
@Route(path = AccountArouterConstant.ACCOUNT_LOGIN)
public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {
    static final int REQUEST_CODE_GEETEST_VERIFY = 1;

    @BindView(R2.id.et_username)
    ClearAbleEditText etUsername;
    @BindView(R2.id.et_password)
    ClearAbleEditText etPassword;

    @Override
    protected LoginContract.Presenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        initEvent();
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_login;
    }

    @Override
    public void onLoginSuccess() {
        EventBus.getDefault().post(new LoginEvent(LoginEvent.LOGIN_SUCCESS));
    }

    @Override
    public void showToast(int msg) {
        ToastUtil.showShort(LoginActivity.this, msg);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(LoginActivity.this, msg);
    }

    @Override
    public void onSecondVerify(SecondVerifyParams secondVerifyParams) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY).withParcelable(IntentConstant.EXTRA_SECOND_VERIFY_PARAMS, secondVerifyParams).navigation(this);
    }

    @OnClick({R2.id.btn_login, R2.id.tv_forgot_pwd, R2.id.btn_register, R2.id.ic_back})
    public void onViewClicked(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn_login) {
            boolean checkResult = mPresenter.check(etUsername.getText().toString().trim(), etPassword.getText().toString().trim());
            if (checkResult) {
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GEETEST_VERIFY).navigation(this, REQUEST_CODE_GEETEST_VERIFY);
            }
        } else if (viewId == R.id.tv_forgot_pwd) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_FORGOT_PASSWORD).navigation(this);
        } else if (viewId == R.id.btn_register) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_REGISTER).navigation(this);
        } else if (viewId == R.id.ic_back) {
            finish();
        }
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SecondVerifyEvent event) {
        if (VerifyTypeConstants.LOGIN.equals(event.getVerifyType())) {
            JsonObject response = event.getResponse();
            if (response != null && response.has(ApiParamConstants.KEY_TOKEN)) {
                String token = response.get(ApiParamConstants.KEY_TOKEN).getAsString();
                if (!TextUtils.isEmpty(token)) {
                    UserInfoManager.getInstance().putToken(token);
                    showToast(R.string.account_login_success);
                    onLoginSuccess();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        if (event.isLoginSuccess()) {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GEETEST_VERIFY && resultCode == RESULT_OK) {
            GeetestVerifyBean geetestVerifyBean = data.getParcelableExtra(IntentConstant.EXTRA_GEETEST_VERIFY_BEAN);
            LoginReq loginReq = new LoginReq();
            loginReq.setChallenge(geetestVerifyBean.getGeetest_challenge());
            loginReq.setValidate(geetestVerifyBean.getGeetest_validate());
            loginReq.setSeccode(geetestVerifyBean.getGeetest_seccode());
            loginReq.setEmail(etUsername.getText().toString().trim());
            loginReq.setPassword(etPassword.getText().toString().trim());
            mPresenter.login(loginReq);
        }
    }
}
