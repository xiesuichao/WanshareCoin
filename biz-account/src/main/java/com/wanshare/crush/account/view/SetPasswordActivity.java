package com.wanshare.crush.account.view;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.SetPasswordContract;
import com.wanshare.crush.account.model.bean.SecondVerifyReq;
import com.wanshare.crush.account.presenter.SetPasswordPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/23
 */
@Route(path = AccountArouterConstant.ACCOUNT_SET_PASSWORD)
public class SetPasswordActivity extends BaseActivity<SetPasswordContract.Presenter> implements SetPasswordContract.View {
    public static final String EXTRA_SECOND_TOKEN = "extra_second_token";

    @BindView(R2.id.et_new_password)
    ClearAbleEditText etNewPassword;
    @BindView(R2.id.et_confirm_password)
    ClearAbleEditText etConfirmPassword;
    private String secondToken;
    private String accountUri;

    @Override
    protected SetPasswordContract.Presenter getPresenter() {
        return new SetPasswordPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initIntent() {
        super.initIntent();
        secondToken = getIntent().getStringExtra(EXTRA_SECOND_TOKEN);
        secondToken = getIntent().getStringExtra(EXTRA_SECOND_TOKEN);
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_set_password);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_set_password;
    }

    @OnClick(R2.id.btn_commit)
    public void onViewClicked() {
        boolean isCheckOk = mPresenter.check(etNewPassword.getText().trim(), etConfirmPassword.getText().trim());
        if (isCheckOk) {
            SecondVerifyReq req = new SecondVerifyReq();
            req.setAccount(accountUri);
            req.setType(VerifyTypeConstants.FORGET_PWD);
            req.setSecondtoken(secondToken);
            req.setNewpassword(etNewPassword.getText().trim());
            mPresenter.secondVerify(req);
        }
    }

    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

    @Override
    public void onVerifySuccess() {
        ToastUtil.showLong(this,R.string.account_reset_password_success);
        setResult(RESULT_OK);
        finish();
    }
}
