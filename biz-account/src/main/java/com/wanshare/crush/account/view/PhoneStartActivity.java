package com.wanshare.crush.account.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.PhoneStartContract;
import com.wanshare.crush.account.model.bean.AccountVerificationReq;
import com.wanshare.crush.account.model.bean.OperationVerifyReq;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.PhoneStartPresenter;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = AccountArouterConstant.ACCOUNT_PHONE_START)
public class PhoneStartActivity extends BaseActivity<PhoneStartContract.Presenter> implements PhoneStartContract.View {


    @BindView(R2.id.et_phone_start_number)
    TextView mTvPhoneStartNumber;
    @BindView(R2.id.et_phone_start_code)
    EditText mEtPhoneStartCode;
    @BindView(R2.id.btn_phone_start_get_code)
    Button mBtnPhoneStartGetCode;
    @BindView(R2.id.btn_phone_start_confirm)
    Button mBtnPhoneStartConfirm;

    private AccountInfoBean.AccountInfo.PhoneNumberBean mPhoneNumberBean;

    @Override
    protected PhoneStartContract.Presenter getPresenter() {
        return new PhoneStartPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_phone_start;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_start_verify);
    }

    @Override
    protected void initData() {
        AccountInfoBean bean = AccountCacheManager.getInstance().getAccountInfoBean();

        if (bean != null && bean.getAccountInfo() != null) {
            mPhoneNumberBean = bean.getAccountInfo().getPhoneNumber();
            mTvPhoneStartNumber.setText(mPhoneNumberBean == null ? "" : mPhoneNumberBean.getPhone());
        }

    }


    @OnClick({R2.id.btn_phone_start_get_code, R2.id.btn_phone_start_confirm})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_phone_start_get_code) {
            if (mPhoneNumberBean == null) {
                return;
            }
            mPresenter.sendVerificationCode(
                    new AccountVerificationReq("tel:" + mPhoneNumberBean.getPhoneNumber(), "bind_phone"));
        } else if (i == R.id.btn_phone_start_confirm) {
            String code = mEtPhoneStartCode.getText().toString();
            if (TextUtils.isEmpty(code)) {
                showLongToast(getString(R.string.account_code_null_tips));
                return;
            }
            OperationVerifyReq req = new OperationVerifyReq();
            req.setAccount(mPhoneNumberBean.getPhoneNumber(), 1);
            req.setType("bind_phone");
            req.setCode(code);
            mPresenter.openPhone(req);
        }
    }

    @Override
    public void onCountDown(final int time) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnPhoneStartGetCode.setEnabled(false);
                mBtnPhoneStartGetCode.setText(getString(R.string.account_code_tips, time + ""));
            }
        });
    }

    @Override
    public void onCountDownFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mBtnPhoneStartGetCode.setText(R.string.account_get_code);
                mBtnPhoneStartGetCode.setEnabled(true);
            }
        });
    }

    @Override
    public void onSendVerifyCodeSuccess() {
        showShortToast(getString(R.string.account_verification_code_send_success));
        mPresenter.startCountDown();
    }

    @Override
    public void resultOpenPhone() {
        setResult(RESULT_OK);
        finish();
    }
}
