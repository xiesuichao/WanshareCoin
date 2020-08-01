package com.wanshare.crush.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.android.arouter.utils.TextUtils;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.PhoneBindingContract;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.BindPhoneReq;
import com.wanshare.crush.account.model.bean.Country;
import com.wanshare.crush.account.model.bean.GeetestVerifyBean;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.common.biz.account.model.SecondVerifyEvent;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.PhoneBindingPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wanshare.crush.account.view.SelectCountryActivity.EXTRA_COUNTY;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 手机绑定界面
 * */
@Route(path = AccountArouterConstant.ACCOUNT_PHONE_BINDING)
public class PhoneBindingActivity extends BaseActivity<PhoneBindingContract.Presenter> implements PhoneBindingContract.View {


    @BindView(R2.id.btn_phone_binding_prefix)
    LinearLayout mBtnPhoneBindingPrefix;
    @BindView(R2.id.tv_phone_binding_prefix)
    TextView mTvPhoneBindingPrefix;
    @BindView(R2.id.et_phone_binding)
    EditText mEtPhoneBinding;
    @BindView(R2.id.et_phone_binding_code)
    EditText mEtPhoneBindingCode;
    @BindView(R2.id.et_phone_binding_password)
    EditText mEtPhoneBindingPassword;
    @BindView(R2.id.btn_phone_binding_get_code)
    Button mBtnPhoneBindingGetCode;
    @BindView(R2.id.btn_phone_binding_next_steps)
    Button mBtnPhoneBindingNextSteps;

    // 0:设置手机 1:修改手机 2:设置邮箱 3:修改邮箱
    private int mType = 0;
    private String mStrPrefix = "+86";
    private BindPhoneReq mBindPhoneReq;
    private String account;
    private Country mCountry;

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt("type", 0);
        }
    }

    @Override
    protected void initData() {
    }

    @Override
    protected PhoneBindingContract.Presenter getPresenter() {
        return new PhoneBindingPresenter(this);
    }

    @Override
    protected void initView() {
        initEvent();
        changeView();
    }

    @Override
    protected void onRightButton(View view) {
        super.onRightButton(view);
        account = mEtPhoneBinding.getText().toString();
        String code = mEtPhoneBindingCode.getText().toString();
        if (isPhone()) {
            if (isPhoneNull(account, code)) {
                return;
            }
            mBindPhoneReq = new BindPhoneReq();
            mBindPhoneReq.setPhoneNumber(mStrPrefix + account);
            mBindPhoneReq.setVerificationCode(code);
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GEETEST_VERIFY).navigation(this, 1);
        } else{
            if (isPhoneNull(account, code)) {
               return;
            }
        }
    }

    protected void changeView() {
        if (mType == 0) {//设置手机
            mBtnPhoneBindingNextSteps.setVisibility(View.GONE);
            mBtnPhoneBindingPrefix.setVisibility(View.VISIBLE);
            mEtPhoneBinding.setHint(R.string.account_phone_hint);
            mMyToolbar.setTitle(R.string.account_phone_binding_title);
            mMyToolbar.setRightButtonText(R.string.account_phone_binding_ok);

        } else if (mType == 1) {//修改手机
            mBtnPhoneBindingNextSteps.setVisibility(View.VISIBLE);
            mBtnPhoneBindingPrefix.setVisibility(View.VISIBLE);
            mEtPhoneBinding.setHint(R.string.account_phone_new_hint);
            mMyToolbar.setTitle(R.string.account_phone_modify_title);

        } else if (mType == 2) {//设置邮箱
            mBtnPhoneBindingNextSteps.setVisibility(View.GONE);
            mBtnPhoneBindingPrefix.setVisibility(View.GONE);
            mEtPhoneBinding.setHint(R.string.account_email_hint);
            mMyToolbar.setTitle(R.string.account_email_binding_title);
            mMyToolbar.setRightButtonText(R.string.account_phone_binding_ok);
        } else if (mType == 3) {//修改邮箱
            mBtnPhoneBindingNextSteps.setVisibility(View.VISIBLE);
            mBtnPhoneBindingPrefix.setVisibility(View.GONE);
            mEtPhoneBinding.setHint(R.string.account_email_new_hint);
            mMyToolbar.setTitle(R.string.account_email_modify_title);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_phone_binding;
    }


    @Override
    public void onCountDown(final int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnPhoneBindingGetCode.setEnabled(false);
                mBtnPhoneBindingGetCode.setText(getString(R.string.account_code_tips, time + ""));
            }
        });
    }

    @Override
    public void onCountDownFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnPhoneBindingGetCode.setText(R.string.account_get_code);
                mBtnPhoneBindingGetCode.setEnabled(true);
            }
        });
    }

    @Override
    public void onSendVerifyCodeSuccess() {
        showShortToast(getString(R.string.account_verification_code_send_success));
        mPresenter.startCountDown();
    }

    @OnClick({R2.id.btn_phone_binding_get_code, R2.id.btn_phone_binding_next_steps, R2.id.btn_phone_binding_prefix})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_phone_binding_get_code) {
            String account = mEtPhoneBinding.getText().toString();
            if (TextUtils.isEmpty(account)) {
                showLongToast(getString(R.string.account_phone_null_tips));
                return;
            }
            mPresenter.sendVerificationCode(new AccountVerificationReq(ApiParamConstants.PREFIX_PHONE + mStrPrefix + account, "bind_phone"));

        } else if (i == R.id.btn_phone_binding_prefix){
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SELECT_COUNTRY).navigation(this, 2);
        }else if (i == R.id.btn_phone_binding_next_steps) {
            String account = mEtPhoneBinding.getText().toString();
            String code = mEtPhoneBindingCode.getText().toString();
            String password = mEtPhoneBindingPassword.getText().toString();
            if (isPhone()) {
                if (isPhoneNull(account, code)) {
                    return;
                }
            } else {
                if (isPhoneNull(account, code)) {
                    return;
                }
            }

            ARouter.getInstance().
                    build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY).
                    withInt("type", getSecondType()).
                    withString("account", account).
                    withString("code", code).
                    navigation(this);
        }
    }

    private boolean isPhoneNull(String phone, String code){
        if (TextUtils.isEmpty(phone)) {
            showLongToast(getString(R.string.account_phone_null_tips));
            return true;
        }

        if (TextUtils.isEmpty(code)) {
            showLongToast(getString(R.string.account_code_null_tips));
            return true;
        }
        return false;
    }

    /**
     * 获取二次验证类型
     */
    protected int getSecondType() {
        return mType == 2 || mType == 3 ? 0 : 1;
    }

    /**
     * 是否是手机号码
     * */
    protected boolean isPhone(){
        return mType == 0 || mType == 1;
    }


    @Override
    public void onBindPhoneResult() {
        resultSuccess();
    }

    @Override
    public void resultBindSecond(SecondVerifyParams params) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY)
                .withParcelable(IntentConstant.EXTRA_SECOND_VERIFY_PARAMS, params)
                .navigation(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            GeetestVerifyBean geetestVerifyBean = data.getParcelableExtra(IntentConstant.EXTRA_GEETEST_VERIFY_BEAN);
            if (geetestVerifyBean == null || mBindPhoneReq == null) {
                return;
            }
            mBindPhoneReq.setChallenge(geetestVerifyBean.getGeetest_challenge());
            mBindPhoneReq.setSeccode(geetestVerifyBean.getGeetest_seccode());
            mBindPhoneReq.setValidate(geetestVerifyBean.getGeetest_validate());

            mPresenter.bindPhone(mBindPhoneReq);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            mCountry = data.getParcelableExtra(EXTRA_COUNTY);
            mStrPrefix = mCountry == null ? "" : "+" + mCountry.getNo();
            mTvPhoneBindingPrefix.setText(mStrPrefix);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SecondVerifyEvent event) {
        if (VerifyTypeConstants.BIND_PHONE.equals(event.getVerifyType())) {
            resultSuccess();
        }
    }

    private void resultSuccess(){
        AccountInfoBean info = AccountCacheManager.getInstance().getAccountInfoBean();
        if (info == null) {
            return;
        }
        AccountInfoBean.AccountInfo accountInfo = info.getAccountInfo();
        if (accountInfo == null) {
            return;
        }
        AccountInfoBean.AccountInfo.PhoneNumberBean bean = accountInfo.getPhoneNumber();
        if (bean == null) {
            bean = new AccountInfoBean.AccountInfo.PhoneNumberBean();
        }
        bean.setAreaCode(mStrPrefix);
        bean.setPhone(account);
        if (mCountry != null) {
            bean.setGeocoderEN(mCountry.getEn());
            bean.setGeocoderCH(mCountry.getCn());
        }
        bean.setPhoneNumber(mStrPrefix + account);
        AccountCacheManager.getInstance().putAccountInfoBean(info);
        EventBus.getDefault().post(new UserInfoEvent());
        finish();
    }
}
