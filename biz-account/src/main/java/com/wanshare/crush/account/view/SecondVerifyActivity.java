package com.wanshare.crush.account.view;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.JsonObject;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.common.widget.CustomTabLayout;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.crush.account.contract.SecondVerifyContract;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;
import com.wanshare.common.biz.account.model.SecondVerifyEvent;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.SecondVerifyPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/25
 * 二次验证
 */
@Route(path = AccountArouterConstant.ACCOUNT_SECOND_VERIFY)
public class SecondVerifyActivity extends BaseActivity<SecondVerifyContract.Presenter> implements SecondVerifyContract.View {

    @BindView(R2.id.tab_layout)
    CustomTabLayout tabLayout;
    @BindView(R2.id.et_verification_code)
    EditText etVerificationCode;
    @BindView(R2.id.et_google_verification_code)
    ClearAbleEditText etGoogleVerificationCode;
    @BindView(R2.id.btn_get_verification_code)
    Button btnGetVerificationCode;
    @BindView(R2.id.ll_verification_code)
    LinearLayout llVerificationCode;
    @BindView(R2.id.underline_verification_code)
    View underlineVerificationCode;

    @BindString(R2.string.account_email_verify)
    String emailStr;
    @BindString(R2.string.account_phone_verify)
    String phoneStr;
    @BindString(R2.string.account_google_verify)
    String googleStr;

    private SecondVerifyParams mSecondVerifyParams;
    private List<String> mTitleList = new ArrayList<>();

    private String email;
    private String phone;

    @Override
    protected void initData() {
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        mSecondVerifyParams = getIntent().getParcelableExtra(IntentConstant.EXTRA_SECOND_VERIFY_PARAMS);
    }

    @Override
    protected SecondVerifyContract.Presenter getPresenter() {
        return new SecondVerifyPresenter(this);
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_secondary_verify);
        mTitleList.clear();
        email = UserInfoManager.getInstance().getEmail();
        phone = UserInfoManager.getInstance().getPhone();
        if (AccountCacheManager.getInstance().getIsEmailAuthEnable() && !TextUtils.isEmpty(email)) {
            mTitleList.add(emailStr);
        }

        if (AccountCacheManager.getInstance().getIsPhoneAuthEnable() && !TextUtils.isEmpty(phone)) {
            mTitleList.add(phoneStr);
        }

        if (AccountCacheManager.getInstance().getIsGoogleAuthEnable()) {
            mTitleList.add(googleStr);
        }
        String[] strings = new String[mTitleList.size()];
        tabLayout.setTitleArr(mTitleList.toArray(strings));
        tabLayout.setOnTabClickListener(new CustomTabLayout.OnTabClickListener() {
            @Override
            public void tabClick(int position, String str) {
                if (emailStr.equals(str)) {
                    etGoogleVerificationCode.setVisibility(View.GONE);
                    llVerificationCode.setVisibility(View.VISIBLE);
                } else if (phoneStr.equals(str)) {
                    etGoogleVerificationCode.setVisibility(View.GONE);
                    llVerificationCode.setVisibility(View.VISIBLE);
                } else if (googleStr.equals(str)) {
                    etGoogleVerificationCode.setVisibility(View.VISIBLE);
                    llVerificationCode.setVisibility(View.GONE);
                }
            }
        });

        etVerificationCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                underlineVerificationCode.setSelected(hasFocus);
            }
        });
    }


    @Override
    protected int getContentView() {
        return R.layout.account_activity_second_verify;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    public void onCountDown(final int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
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


    @OnClick({R2.id.btn_get_verification_code, R2.id.btn_commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_get_verification_code) {
            if (isEmailChecked()) {
                //发送邮箱验证码
                mPresenter.sendVerificationCode(new AccountVerificationReq(ApiParamConstants.PREFIX_EMAIL + email, mSecondVerifyParams.getVerifyType()));
            } else if (isPhoneChecked()) {
                // 发送手機验证码
                mPresenter.sendVerificationCode(new AccountVerificationReq(ApiParamConstants.PREFIX_PHONE + phone, mSecondVerifyParams.getVerifyType()));
            }
        } else if (i == R.id.btn_commit) {
            SecondVerifyReq secondVerifyReq = new SecondVerifyReq();
            secondVerifyReq.setType(mSecondVerifyParams.getVerifyType());
            secondVerifyReq.setBasetoken(mSecondVerifyParams.getBaseToken());
            secondVerifyReq.setSecondtoken(mSecondVerifyParams.getSecondToken());
            secondVerifyReq.setNewpassword(mSecondVerifyParams.getNewPassword());
            secondVerifyReq.setCode(etVerificationCode.getText().toString().trim());
            if (isEmailChecked()) {
                secondVerifyReq.setAccount(ApiParamConstants.PREFIX_EMAIL + email);
            } else if (isPhoneChecked()) {
                secondVerifyReq.setAccount(ApiParamConstants.PREFIX_PHONE + phone);
            } else {
                secondVerifyReq.setAccount(ApiParamConstants.PREFIX_GOOGLE + email);
                secondVerifyReq.setCode(etGoogleVerificationCode.getText().trim());
            }
            mPresenter.commit(secondVerifyReq);
        }
    }

    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void onVerifySuccess(JsonObject response) {
//        showToast(R.string.account_verify_success);
        EventBus.getDefault().post(new SecondVerifyEvent(mSecondVerifyParams.getVerifyType(), response));
        finish();
    }

    private boolean isEmailChecked() {
        return TextUtils.equals(emailStr, tabLayout.getCheckedText());
    }

    private boolean isPhoneChecked() {
        return TextUtils.equals(phoneStr, tabLayout.getCheckedText());
    }

}
