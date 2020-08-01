package com.wanshare.crush.account.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.JsonObject;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.common.widget.CustomTabLayout;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.GoogleOperationContract;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.GoogleStartReq;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;
import com.wanshare.crush.account.model.bean.event.GoogleBindEvent;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.GoogleOperationPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AccountArouterConstant.ACCOUNT_GOOGLE_OPERATION)
public class GoogleOperationActivity extends BaseActivity<GoogleOperationContract.Presenter> implements GoogleOperationContract.View{


    @BindView(R2.id.tab_layout)
    CustomTabLayout mTabLayout;
    @BindView(R2.id.et_verification_code)
    EditText mEtVerificationCode;
    @BindView(R2.id.btn_get_verification_code)
    Button mBtnGetVerificationCode;
    @BindView(R2.id.underline_verification_code)
    View mUnderlineVerificationCode;
    @BindView(R2.id.ll_verification_code)
    LinearLayout mLlVerificationCode;
    @BindView(R2.id.et_google_operation_code_new)
    EditText mEtGoogleOperationCodeNew;
    @BindView(R2.id.et_google_operation_code_old)
    EditText mEtGoogleOperationCodeOld;
    @BindView(R2.id.btn_commit)
    Button mBtnCommit;

    //0:修改 1:关闭
    private int type;
    //0:邮箱 1:手机
    private int accountType;
    private List<String> mTabList = new ArrayList<>();
    private String email;
    private String phone;

    @Override
    protected GoogleOperationContract.Presenter getPresenter() {
        return new GoogleOperationPresenter(this);
    }

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 0);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_google_operation;
    }

    @Override
    protected void initView() {
        if (type == 1) {
            mMyToolbar.setTitle(R.string.account_google_close_title);
            mBtnCommit.setText(R.string.account_close);
            mEtGoogleOperationCodeOld.setVisibility(View.GONE);
            mEtGoogleOperationCodeNew.setHint(R.string.account_google_verify_code);
        } else {
            mMyToolbar.setTitle(R.string.account_google_verify_modify);
            mBtnCommit.setText(R.string.account_google_verify_modify);
            mEtGoogleOperationCodeOld.setVisibility(View.VISIBLE);
            mEtGoogleOperationCodeNew.setHint(R.string.account_google_verify_code_new);
        }
    }

    private void initTab(){
        mTabLayout.setTitleList(mTabList);
        mTabLayout.setOnTabClickListener(new CustomTabLayout.OnTabClickListener() {
            @Override
            public void tabClick(int position, String str) {
                if (getString(R.string.account_email_verify).equals(str)) {
                    accountType = 0;
                } else if (getString(R.string.account_phone_verify).equals(str)) {
                    accountType = 1;
                }
            }
        });
    }

    @Override
    protected void initData() {
        email = UserInfoManager.getInstance().getEmail();
        phone = UserInfoManager.getInstance().getPhone();
//        mPresenter.getBindStatus();
        if (AccountCacheManager.getInstance().getIsEmailAuthEnable()) {
            mTabList.add(getString(R.string.account_email_verify));
        }
        if (AccountCacheManager.getInstance().getIsPhoneAuthEnable()){
            mTabList.add(getString(R.string.account_phone_verify));
        }
        initTab();
    }

    @OnClick({R2.id.btn_get_verification_code, R2.id.btn_commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_get_verification_code) {
            sendVerify();
        } else if (i == R.id.btn_commit) {
            String code = mEtVerificationCode.getText().toString();
            String googleCode = mEtGoogleOperationCodeNew.getText().toString();
            String googleCodeOld = mEtGoogleOperationCodeOld.getText().toString();


            if (TextUtils.isEmpty(code)) {
                showLongToast(getString(R.string.account_code_null));
                return;
            }
            if (TextUtils.isEmpty(googleCode)) {
                showLongToast(getString(R.string.account_code_google_tips));
                return;
            }
            if (type == 2) {
                if (TextUtils.isEmpty(googleCodeOld)) {
                    showLongToast(getString(R.string.account_code_google_tips));
                    return;
                }
                SecondVerifyReq second = new SecondVerifyReq();
                if (accountType == 0) {
                    second.setAccount(ApiParamConstants.PREFIX_EMAIL + email);
                } else {
                    second.setAccount(ApiParamConstants.PREFIX_PHONE + phone);
                }
                second.setCode(code);
                second.setSecondtoken(googleCodeOld);
                mPresenter.modifyGoogleAuthenticator(new GoogleStartReq(googleCode), second);
            }else{
                OperationVerifyReq req = new OperationVerifyReq();
                req.setAccount(getAccount(), accountType);
                req.setCode(code);
                req.setSecondCode(googleCode);
                req.setType(type == 0 ? VerifyTypeConstants.BIND_GOOGLE : VerifyTypeConstants.UNBIND_GOOGLE);
                mPresenter.closeGoogleAuthenticator(req);
            }

        }
    }

    private void sendVerify(){
        AccountVerificationReq req = new AccountVerificationReq();
        req.setUri(getAccount(), accountType);
        req.setUseAge(type == 0 ? VerifyTypeConstants.BIND_GOOGLE : VerifyTypeConstants.UNBIND_GOOGLE);
        mPresenter.sendVerificationCode(req);
    }

    private String getAccount(){
        String account = "";
        if (accountType == 0) {
            account = UserInfoManager.getInstance().getEmail();
        } else if (accountType == 1) {
            AccountInfoBean.AccountInfo.PhoneNumberBean bean = AccountCacheManager.getInstance().getPhoneNumberBean();
            account = bean == null ? "" : bean.getPhoneNumber();
        }
        return account;
    }

    @Override
    public void resultCloseGoogle(Object object) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultBindStatus(BindStatusBean bean) {
        if (bean == null) {
            return;
        }

    }

    @Override
    public void onVerifySuccess(JsonObject response) {

    }

    @Override
    public void resultModifyGoogle() {
        EventBus.getDefault().post(new UserInfoEvent());
        EventBus.getDefault().post(new GoogleBindEvent());
        finish();
    }

    @Override
    public void onCountDown(final int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnGetVerificationCode.setEnabled(false);
                mBtnGetVerificationCode.setText(getString(R.string.account_code_tips, time + ""));
            }
        });
    }

    @Override
    public void onCountDownFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnGetVerificationCode.setText(R.string.account_get_code);
                mBtnGetVerificationCode.setEnabled(true);
            }
        });
    }


    @Override
    public void onSendVerifyCodeSuccess() {
        showShortToast(getString(R.string.account_verification_code_send_success));
        mPresenter.startCountDown();
    }
}
