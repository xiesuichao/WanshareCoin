package com.wanshare.crush.account.view;

import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.crush.account.contract.ForgotPasswordContract;
import com.wanshare.crush.account.model.bean.GeetestVerifyBean;
import com.wanshare.crush.account.model.bean.ResetPasswordReq;
import com.wanshare.crush.account.presenter.ForgotPasswordPresenter;
import com.wanshare.wscomponent.utils.Preconditions;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/23
 */
@Route(path = AccountArouterConstant.ACCOUNT_FORGOT_PASSWORD)
public class ForgotPasswordActivity extends BaseActivity<ForgotPasswordContract.Presenter> implements ForgotPasswordContract.View {
    static final int REQUEST_CODE_VERIFY = 1;
    static final int REQUEST_CODE_GEETEST_VERIFY = 2;
    @BindView(R2.id.et_username)
    ClearAbleEditText etUsername;


    @Override
    protected ForgotPasswordContract.Presenter getPresenter() {
        return new ForgotPasswordPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_pwd_forget);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_forgot_password;
    }


    @OnClick(R2.id.btn_commit)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etUsername.getText())) {
            ToastUtil.showShort(this, R.string.account_please_input_phone_or_email);
        } else {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GEETEST_VERIFY).navigation(this, REQUEST_CODE_GEETEST_VERIFY);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_CODE_GEETEST_VERIFY:
                    GeetestVerifyBean geetestVerifyBean = data.getParcelableExtra(IntentConstant.EXTRA_GEETEST_VERIFY_BEAN);
                    ResetPasswordReq req = new ResetPasswordReq();
                    req.setChallenge(geetestVerifyBean.getGeetest_challenge());
                    req.setValidate(geetestVerifyBean.getGeetest_validate());
                    req.setSeccode(geetestVerifyBean.getGeetest_seccode());
                    String prefix = Preconditions.isEmail(etUsername.getText().trim()) ? ApiParamConstants.PREFIX_EMAIL : ApiParamConstants.PREFIX_PHONE;
                    String username = prefix + etUsername.getText().trim();
                    req.setUri(username);
                    mPresenter.resetPassword(req);
                    break;
                case REQUEST_CODE_VERIFY:
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSecondVerify(String baseToken, String uri) {
        String prefix = Preconditions.isEmail(etUsername.getText().trim()) ? ApiParamConstants.PREFIX_EMAIL : ApiParamConstants.PREFIX_PHONE;
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_FORGOT_PASSWORD_VERIFY)
                .withString(ForgotPasswordVerifyActivity.EXTRA_ACCOUNT_URI, prefix + uri)
                .withString(ForgotPasswordVerifyActivity.EXTRA_BASE_TOKEN, baseToken)
                .navigation(this, REQUEST_CODE_VERIFY);
    }
}
