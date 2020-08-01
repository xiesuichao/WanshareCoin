package com.wanshare.crush.account.view;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.common.biz.account.constant.ApiParamConstants;
import com.wanshare.common.biz.account.constant.VerifyTypeConstants;
import com.wanshare.crush.account.contract.EditAssetPasswordContract;
import com.wanshare.common.biz.account.model.SecondVerifyParams;
import com.wanshare.crush.account.model.bean.SetAssetPasswordReq;
import com.wanshare.common.biz.account.model.SecondVerifyEvent;
import com.wanshare.crush.account.presenter.EditAssetPasswordPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/25
 * 修改资金密码界面
 */
@Route(path = AccountArouterConstant.ACCOUNT_EDIT_ASSET_PASSWORD)
public class EditAssetsPasswordActivity extends BaseActivity<EditAssetPasswordContract.Presenter> implements EditAssetPasswordContract.View {

    @BindView(R2.id.et_login_password)
    ClearAbleEditText etLoginPassword;
    @BindView(R2.id.et_fund_password)
    ClearAbleEditText etFundPassword;
    @BindView(R2.id.et_confirm_fund_password)
    ClearAbleEditText etConfirmFundPassword;

    @Override
    protected EditAssetPasswordContract.Presenter getPresenter() {
        return new EditAssetPasswordPresenter(this);
    }

    @Override
    protected void initData() {
        initEvent();
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_modify_fund_password);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_edit_assets_password;
    }

    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void onEditAssetPasswordSuccess() {
        showToast(R.string.account_reset_asset_password_success);
        finish();
    }

    @OnClick(R2.id.btn_commit)
    public void onViewClicked() {
        boolean isCheckOk = mPresenter.checkout(etLoginPassword.getText().trim(), etFundPassword.getText().trim(), etConfirmFundPassword.getText().trim());
        if (isCheckOk) {
            SecondVerifyParams secondVerifyParams = new SecondVerifyParams();
            secondVerifyParams.setVerifyType(VerifyTypeConstants.EDIT_ASSET_PWD);
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECOND_VERIFY).withParcelable(IntentConstant.EXTRA_SECOND_VERIFY_PARAMS, secondVerifyParams).navigation(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SecondVerifyEvent event) {
        if (VerifyTypeConstants.EDIT_ASSET_PWD.equals(event.getVerifyType())) {
            SetAssetPasswordReq req = new SetAssetPasswordReq();
            String baseToken = event.getResponse().get(ApiParamConstants.KEY_BASE_TOKEN).getAsString();
            req.setBaseToken(baseToken);
            req.setPassword(etLoginPassword.getText().trim());
            req.setTraPassword(etFundPassword.getText().trim());
            mPresenter.editAssetPassword(req);
        }
    }
}
