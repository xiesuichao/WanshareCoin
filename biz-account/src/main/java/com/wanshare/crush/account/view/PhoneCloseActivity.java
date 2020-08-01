package com.wanshare.crush.account.view;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.widget.CustomTabLayout;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.PhoneCloseContract;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.PhoneClosePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AccountArouterConstant.ACCOUNT_PHONE_CLOSE)
public class PhoneCloseActivity extends BaseActivity<PhoneCloseContract.Presenter> implements PhoneCloseContract.View {

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
    @BindView(R2.id.et_phone_close_google_code)
    EditText mEtPhoneCloseGoogleCode;
    @BindView(R2.id.et_phone_close_sms_code)
    EditText mEtPhoneCloseSmsCode;
    @BindView(R2.id.btn_phone_close_get_code)
    Button mBtnPhoneCloseGetCode;
    @BindView(R2.id.btn_phone_close_confirm)
    Button mBtnPhoneCloseConfirm;

    private List<String> mTabList = new ArrayList<>();
    private String mType;

    private Handler mHandler = new Handler();
    private Runnable mRunnableEmail;
    private Runnable mRunnablePhone;
    private int timeEmail = MAX_TIME;
    private int timePhone = MAX_TIME;
    private final static int MAX_TIME = 60;
    //0:sms 1：邮箱
    private int type;
    private AccountInfoBean.AccountInfo.PhoneNumberBean mPhoneNumberBean;
    private AccountInfoBean.AccountInfo.EmailBean mEmailBean;

    @Override
    protected PhoneCloseContract.Presenter getPresenter() {
        return new PhoneClosePresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_phone_close;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_close_verify);

        mEtVerificationCode.setHint(R.string.account_email_verify);
        mEmailBean = AccountCacheManager.getInstance().getEmailBean();
        mPhoneNumberBean = AccountCacheManager.getInstance().getPhoneNumberBean();
    }

    private void initTab(){
        mTabLayout.setTitleList(mTabList);
        mTabLayout.setOnTabClickListener(new CustomTabLayout.OnTabClickListener() {
            @Override
            public void tabClick(int position, String str) {
                changeTab(str);
            }
        });
        if (mTabList != null && mTabList.size() >= 0) {
            changeTab(mTabList.get(0));
        }
    }

    private void changeTab(String str){
        mType = str;
        if (getString(R.string.account_google_verify).equals(str)) {
            mEtPhoneCloseGoogleCode.setVisibility(View.VISIBLE);
            mLlVerificationCode.setVisibility(View.GONE);
        } else if (getString(R.string.account_email_verify).equals(str)) {
            mEtPhoneCloseGoogleCode.setVisibility(View.GONE);
            mLlVerificationCode.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
//        mPresenter.getBindStatus();
        if (AccountCacheManager.getInstance().getIsGoogleAuthEnable()){
            mTabList.add(getString(R.string.account_google_verify));
        }
        if (AccountCacheManager.getInstance().getIsEmailAuthEnable()) {
            mTabList.add(getString(R.string.account_email_verify));
        }
        initTab();
    }

    @OnClick({R2.id.btn_get_verification_code, R2.id.btn_phone_close_get_code, R2.id.btn_phone_close_confirm})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_get_verification_code) {
            sendVerify(0);
        } else if (i == R.id.btn_phone_close_get_code) {
            sendVerify(1);
        } else if (i == R.id.btn_phone_close_confirm) {
            close();
        }
    }

    private void sendVerify(int type){
        this.type = type;
        String account = "";
        AccountVerificationReq req = new AccountVerificationReq();
        if (type == 1) {
            account = mPhoneNumberBean == null ? "" : mPhoneNumberBean.getPhoneNumber();
        }else{
            account = mEmailBean == null ? "" : mEmailBean.getEmail();
        }
        req.setUri(account, type);
        req.setUseAge(VerifyTypeConstants.UNBIND_PHONE);
        mPresenter.sendVerificationCode(req);
    }

    private void close(){
        String emailCode = mEtVerificationCode.getText().toString();
        String smsCode = mEtPhoneCloseSmsCode.getText().toString();
        String googleCode = mEtPhoneCloseGoogleCode.getText().toString();
        if (TextUtils.isEmpty(smsCode)) {
            showLongToast(getString(R.string.account_sms_verify_code_tips));
            return;
        }
        if (nullCode(emailCode, googleCode)) {
            return;
        }

        OperationVerifyReq req = new OperationVerifyReq();

        req.setType(VerifyTypeConstants.UNBIND_PHONE);
        req.setSecondCode(smsCode);
        if (mType.equals(getString(R.string.account_google_verify))){
            req.setAccount("", 2);
            req.setCode(googleCode);
        }else {
            req.setAccount(mEmailBean == null ? "" : mEmailBean.getEmail(), 0);
            req.setCode(emailCode);
        }
        mPresenter.closePhone(req);
    }

    private boolean nullCode(String email, String googleCode){
        if (TextUtils.isEmpty(mType)) {
            return true;
        }
        if (mType.equals(getString(R.string.account_google_verify))) {
            if (TextUtils.isEmpty(googleCode)) {
                showLongToast(getString(R.string.account_code_google_tips));
                return true;
            }
        } else if (mType.equals(getString(R.string.account_email_verify))) {
            if (TextUtils.isEmpty(email)) {
                showLongToast(getString(R.string.account_email_verify_tips));
                return true;
            }
        }
        return false;
    }

    @Override
    public void resultBindStatus(BindStatusBean bean) {
        if (bean == null) {
            return;
        }

        if (bean.getGoogleAuthenticator() ==1){
            mTabList.add(getString(R.string.account_google_verify));
        }
        if (bean.getEmailAuthenticator() == 1) {
            mTabList.add(getString(R.string.account_email_verify));
        }
        initTab();
    }

    @Override
    public void resultClosePhone() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultVerifyCode() {
        if (type == 1) {
            countdownTimePhone();
        }else{
            countdownTimeEmail();
        }
    }

    protected void countdownTimeEmail() {
        mRunnableEmail = new Runnable() {
            @Override
            public void run() {
                if (timeEmail == 0) {
                    timeEmail = MAX_TIME;
                    completeTime(mBtnGetVerificationCode);
                    mHandler.removeCallbacks(this);
                    return;
                }
                updateTime(mBtnGetVerificationCode, timeEmail);
                timeEmail--;
                mHandler.postDelayed(mRunnableEmail, 1000);
            }
        };
        mHandler.post(mRunnableEmail);
    }

    protected void countdownTimePhone() {

        mRunnablePhone = new Runnable() {
            @Override
            public void run() {
                if (timePhone == 0) {
                    timePhone = MAX_TIME;
                    completeTime(mBtnPhoneCloseGetCode);
                    mHandler.removeCallbacks(this);
                    return;
                }
                updateTime(mBtnPhoneCloseGetCode, timePhone);
                timePhone--;
                //设置1秒执行一次
                mHandler.postDelayed(mRunnablePhone, 1000);
            }
        };
        mHandler.post(mRunnablePhone);
    }

    private void updateTime(TextView btn, int time){
        btn.setEnabled(false);
        btn.setText(getString(R.string.account_code_tips, time + ""));
    }

    private void completeTime(TextView btn){
        btn.setText(R.string.account_get_code);
        btn.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeHandler(mRunnableEmail);
        removeHandler(mRunnablePhone);
    }

    private void removeHandler(Runnable runnable){
        if (mHandler == null || runnable == null) {
            return;
        }
        mHandler.removeCallbacks(runnable);
    }
}
