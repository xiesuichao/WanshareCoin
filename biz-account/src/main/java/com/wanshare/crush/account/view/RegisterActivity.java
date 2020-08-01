package com.wanshare.crush.account.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.account.model.LoginEvent;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.RegisterContract;
import com.wanshare.crush.account.model.bean.Country;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.GeetestVerifyBean;
import com.wanshare.crush.account.model.bean.RegisterReq;
import com.wanshare.crush.account.presenter.RegisterPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/22
 * 注册界面
 */
@Route(path = AccountArouterConstant.ACCOUNT_REGISTER)
public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View {
    private static final int REQUEST_CODE_GEETEST_VERIFY = 1;
    private static final int REQUEST_CODE_SELECT_COUNTRY = 2;

    @BindView(R2.id.tv_zone)
    TextView tvZone;
    @BindView(R2.id.ll_zone)
    LinearLayout llZone;
    @BindView(R2.id.et_email)
    ClearAbleEditText etEmail;
    @BindView(R2.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R2.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R2.id.underline_verification_code)
    View underlineVerificationCode;
    @BindView(R2.id.underline_zone)
    View underlineZone;
    @BindView(R2.id.et_password)
    ClearAbleEditText etPassword;
    @BindView(R2.id.et_invitation_code)
    ClearAbleEditText etInvitationCode;
    @BindView(R2.id.cb_terms)
    CheckBox cbTerms;
    @BindView(R2.id.tv_terms)
    TextView tvTerms;
    @BindView(R2.id.btn_register)
    Button btnRegister;
    private GeetestVerifyBean mGeetestVerifyBean = new GeetestVerifyBean();

    @Override
    protected RegisterContract.Presenter getPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_register);
        etVerificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                underlineVerificationCode.setSelected(hasFocus);
            }
        });
        tvZone.setFocusableInTouchMode(true);
        tvZone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                underlineZone.setSelected(hasFocus);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_register;
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


    @OnClick({R2.id.ll_zone, R2.id.btn_get_verification_code, R2.id.tv_terms, R2.id.btn_register})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.ll_zone) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SELECT_COUNTRY)
                    .navigation(this, REQUEST_CODE_SELECT_COUNTRY);

        } else if (i == R.id.btn_get_verification_code) {
            if (mPresenter.checkAccount(etEmail.getText().toString().trim())) {
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GEETEST_VERIFY).navigation(this, REQUEST_CODE_GEETEST_VERIFY);
            }
        } else if (i == R.id.tv_terms) {
            //条款

        } else if (i == R.id.btn_register) {
            RegisterReq registerReq = new RegisterReq();
            registerReq.setChallenge(mGeetestVerifyBean.getGeetest_challenge());
            registerReq.setValidate(mGeetestVerifyBean.getGeetest_validate());
            registerReq.setSeccode(mGeetestVerifyBean.getGeetest_seccode());
            registerReq.setCountry(tvZone.getText().toString());
            registerReq.setEmail(etEmail.getText().toString().trim());
            registerReq.setVerificationCode(etVerificationCode.getText().toString().trim());
            registerReq.setPassword(etPassword.getText().toString().trim());
            registerReq.setPromotionCode(etInvitationCode.getText().toString().trim());
            mPresenter.register(registerReq, cbTerms.isChecked());
        }
    }

    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void onRegisterSuccess() {
        showToast(R.string.account_register_success);
        EventBus.getDefault().post(new LoginEvent(LoginEvent.LOGIN_SUCCESS));
        finish();
    }

    @Override
    public void onRegisterFail() {
        mGeetestVerifyBean = new GeetestVerifyBean();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_COUNTRY:
                    Country country = data.getParcelableExtra(SelectCountryActivity.EXTRA_COUNTY);
                    tvZone.setText(country.getCn());
                    break;
                case REQUEST_CODE_GEETEST_VERIFY:
                    mGeetestVerifyBean = data.getParcelableExtra(IntentConstant.EXTRA_GEETEST_VERIFY_BEAN);
                    mPresenter.sendVerificationCode(new AccountVerificationReq(ApiParamConstants.PREFIX_EMAIL + etEmail.getText().toString().trim(), VerifyTypeConstants.REGISTER));
                    break;
            }

        }
    }

}
