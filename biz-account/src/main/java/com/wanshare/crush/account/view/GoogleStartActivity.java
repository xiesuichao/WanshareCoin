package com.wanshare.crush.account.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.GoogleStartContract;
import com.wanshare.crush.account.model.bean.GoogleStartReq;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.crush.account.model.bean.event.GoogleBindEvent;
import com.wanshare.common.biz.account.model.SecondVerifyEvent;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.GoogleStartPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 开启Google验证界面
 */
@Route(path = AccountArouterConstant.ACCOUNT_GOOGLE_START)
public class GoogleStartActivity extends BaseActivity<GoogleStartContract.Presenter> implements GoogleStartContract.View {

    @BindView(R2.id.et_google_start_code_new)
    EditText mEtGoogleStartCodeNew;
    @BindView(R2.id.et_google_start_code_old)
    EditText mEtGoogleStartCodeOld;
    @BindView(R2.id.btn_google_start)
    Button mBtnGoogleStart;

    private boolean isStart;
    private boolean isModify;
    //0:修改 1:开关
    private int type;
    private String key;


    @Override
    protected GoogleStartContract.Presenter getPresenter() {
        return new GoogleStartPresenter(this);
    }

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 0);
            isStart = bundle.getBoolean("isStart", false);
            isModify = bundle.getBoolean("isModify", false);
            key = bundle.getString("key");
        }
    }

    @Override
    protected void initData() {
        initEvent();
    }

    @Override
    protected void initView() {

        if (type == 1) {
            mMyToolbar.setTitle(isStart ? R.string.account_google_verify_title : R.string.account_google_close_title);
            mBtnGoogleStart.setText(isStart ? R.string.account_start : R.string.account_close);
            mEtGoogleStartCodeOld.setVisibility(View.GONE);
            mEtGoogleStartCodeNew.setHint(R.string.account_google_verify_code);
        } else {
            mMyToolbar.setTitle(isModify ? R.string.account_google_verify_modify : R.string.account_google_verify_title);
            mBtnGoogleStart.setText(isModify ? R.string.account_google_verify_modify : R.string.account_start);
            mEtGoogleStartCodeOld.setVisibility(isModify ? View.VISIBLE : View.GONE);
            mEtGoogleStartCodeNew.setHint(isModify ? R.string.account_google_verify_code_new
                    : R.string.account_google_verify_code);
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_google_start;
    }



    @OnClick(R2.id.btn_google_start)
    public void onViewClicked() {
        String code = mEtGoogleStartCodeNew.getText().toString();
        if (TextUtils.isEmpty(code)) {
            showLongToast(getString(R.string.account_code_google_tips));
            return;
        }
        if (type == 1) {
            mPresenter.bindGoogleAuthenticator(new GoogleStartReq(code));
        } else {
            if (isModify) {
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY).navigation(this);
            } else {
                GoogleStartReq req = new GoogleStartReq();
                req.setVerificationCode(code);
                mPresenter.openGoogleAuthenticator(req);
            }

        }

    }

    @Override
    public void resultBindSecond(SecondVerifyParams params) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY)
                .withParcelable(IntentConstant.EXTRA_SECOND_VERIFY_PARAMS, params)
                .navigation(this);
    }

    @Override
    public void resultOpenGoogle() {
        AccountCacheManager.getInstance().putIsGoogleAuthEnable(true);
        EventBus.getDefault().post(new UserInfoEvent());
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void resultBindGoogle(Object entity) {
        resultSuccess();
    }

    @Override
    public void resultUnBindGoogle(Object entity) {
        setResult(RESULT_OK);
        finish();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SecondVerifyEvent event) {
        if (VerifyTypeConstants.BIND_GOOGLE.equals(event.getVerifyType())) {
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
        accountInfo.setGoogleAuthenticator("1");
        AccountCacheManager.getInstance().putAccountInfoBean(info);
        AccountCacheManager.getInstance().putAccountInfoBean(info);
        AccountCacheManager.getInstance().putIsGoogleAuthEnable(true);
        EventBus.getDefault().post(new UserInfoEvent());
        EventBus.getDefault().post(new GoogleBindEvent());
        finish();
    }
}
