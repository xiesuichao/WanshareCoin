package com.wanshare.crush.account.view;

import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.EditLoginPasswordContract;
import com.wanshare.crush.account.model.bean.GeetestVerifyBean;
import com.wanshare.crush.account.model.bean.SetLoginPasswordReq;
import com.wanshare.crush.account.presenter.EditLoginPasswordPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/24
 */
@Route(path = AccountArouterConstant.ACCOUNT_EDIT_LOGIN_PASSWORD)
public class EditLoginPasswordActivity extends BaseActivity<EditLoginPasswordContract.Presenter> implements EditLoginPasswordContract.View {
    static final int REQUEST_CODE_GEETEST_VERIFY = 1;
    @BindView(R2.id.et_old_password)
    ClearAbleEditText etOldPassword;
    @BindView(R2.id.et_new_password)
    ClearAbleEditText etNewPassword;
    @BindView(R2.id.et_confirm_password)
    ClearAbleEditText etConfirmPassword;

    @Override
    protected EditLoginPasswordContract.Presenter getPresenter() {
        return new EditLoginPasswordPresenter(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_modify_login_password);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_edit_login_password;
    }


    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void onEditLoginPasswordSuccess() {
        showToast(R.string.account_reset_password_success);
        finish();
    }

    @OnClick(R2.id.btn_commit)
    public void onViewClicked() {
        boolean isCheckoutOk = mPresenter.checkout(etOldPassword.getText().trim(), etNewPassword.getText().trim(), etConfirmPassword.getText().trim());
        if (isCheckoutOk) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GEETEST_VERIFY).navigation(this, REQUEST_CODE_GEETEST_VERIFY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && requestCode == REQUEST_CODE_GEETEST_VERIFY) {
            GeetestVerifyBean geetestVerifyBean = data.getParcelableExtra(IntentConstant.EXTRA_GEETEST_VERIFY_BEAN);
            SetLoginPasswordReq req = new SetLoginPasswordReq();
            req.setChallenge(geetestVerifyBean.getGeetest_challenge());
            req.setValidate(geetestVerifyBean.getGeetest_validate());
            req.setSeccode(geetestVerifyBean.getGeetest_seccode());
            req.setOldPassword(etOldPassword.getText().trim());
            req.setNewPassword(etNewPassword.getText().trim());
            mPresenter.setPassword(req);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
