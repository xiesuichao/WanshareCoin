package com.wanshare.crush.account.view;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.ForgotPasswordVerifyContract;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;
import com.wanshare.crush.account.presenter.ForgotPasswordVerifyPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/23
 */
@Route(path = AccountArouterConstant.ACCOUNT_FORGOT_PASSWORD_VERIFY)
public class ForgotPasswordVerifyActivity extends BaseActivity<ForgotPasswordVerifyContract.Presenter> implements ForgotPasswordVerifyContract.View {
    static final int REQUEST_CODE_SET_PASSWORD = 1;
    public static final String EXTRA_ACCOUNT_URI = "extra_account_uri";
    public static final String EXTRA_BASE_TOKEN = "extra_base_token";

    @BindView(R2.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R2.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R2.id.underline_verification_code)
    View underlineVerificationCode;

    private String accountUri;
    private String baseToken;

    @Override
    protected ForgotPasswordVerifyContract.Presenter getPresenter() {
        return new ForgotPasswordVerifyPresenter(this);
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        accountUri = getIntent().getStringExtra(EXTRA_ACCOUNT_URI);
        baseToken = getIntent().getStringExtra(EXTRA_BASE_TOKEN);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_pwd_forget);
        etVerificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                underlineVerificationCode.setSelected(hasFocus);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_forgot_password_verify;
    }

    @Override
    public void onCountDown(final int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnGetVerificationCode.setEnabled(false);
                btnGetVerificationCode.setText(getString(R.string.account_code_tips, time + ""));
            }
        });
    }

    @Override
    public void onCountDownFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnGetVerificationCode.setText(R.string.account_get_code);
                btnGetVerificationCode.setEnabled(true);
            }
        });
    }

    @Override
    public void onSendVerifyCodeSuccess() {
        showToast(R.string.account_verification_code_send_success);
        mPresenter.startCountDown();
        btnGetVerificationCode.setEnabled(false);
    }

    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void onThirdVerify(String secondToken) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SET_PASSWORD)
                .withString(SetPasswordActivity.EXTRA_SECOND_TOKEN, secondToken)
                .withString(EXTRA_ACCOUNT_URI, accountUri)
                .navigation(this, REQUEST_CODE_SET_PASSWORD);
    }


    @OnClick({R2.id.btn_get_verification_code, R2.id.btn_commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_get_verification_code) {
            //发送验证码
            AccountVerificationReq req = new AccountVerificationReq();
            req.setUseAge(VerifyTypeConstants.FORGET_PWD);
            req.setUri(accountUri);
            mPresenter.sendVerificationCode(req);
        } else if (i == R.id.btn_commit) {
            if (TextUtils.isEmpty(etVerificationCode.getText().toString())) {
                showToast(R.string.account_please_input_verification_code);
            } else {
                SecondVerifyReq secondVerifyReq = new SecondVerifyReq();
                secondVerifyReq.setType(VerifyTypeConstants.FORGET_PWD);
                secondVerifyReq.setBasetoken(baseToken);
                secondVerifyReq.setCode(etVerificationCode.getText().toString().trim());
                secondVerifyReq.setAccount(accountUri);
                mPresenter.secondVerify(secondVerifyReq);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && requestCode == REQUEST_CODE_SET_PASSWORD) {
            setResult(RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

