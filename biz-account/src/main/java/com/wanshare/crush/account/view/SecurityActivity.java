package com.wanshare.crush.account.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.contract.SecurityContract;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.account.presenter.SecurityPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 安全界面
 */
@Route(path = AccountArouterConstant.ACCOUNT_SECURITY)
public class SecurityActivity extends BaseActivity<SecurityContract.Presenter> implements SecurityContract.View {

    @BindView(R2.id.btn_author_security_modify_pass)
    RelativeLayout mBtnAuthorSecurityModifyPass;
    @BindView(R2.id.btn_author_security_modify_founds)
    RelativeLayout mBtnAuthorSecurityModifyFounds;
    @BindView(R2.id.btn_author_security_gesture_password)
    RelativeLayout mBtnAuthorSecurityGesturePassword;
    @BindView(R2.id.tv_author_security_gesture_password_status)
    TextView mTvAuthorSecurityGesturePasswordStatus;
    @BindView(R2.id.btn_author_security_phone_binding)
    RelativeLayout mBtnAuthorSecurityPhoneBinding;
    @BindView(R2.id.btn_author_security_google_binding)
    RelativeLayout mBtnAuthorSecurityGoogleBinding;

    @BindView(R2.id.tv_author_security_phone_binding_status)
    TextView mTvAuthorSecurityPhoneBindingStatus;
    @BindView(R2.id.tv_author_security_google_binding_status)
    TextView mTvAuthorSecurityGoogleBindingStatus;
    @BindView(R2.id.tv_author_security_modify_founds_status)
    TextView mTvAuthorSecurityModifyFoundsStatus;

    private boolean isStartGoogle;
    private boolean isStartPhone;
    private BindStatusBean mBindStatusBean;

    private String phone;
    private String google;

    @Override
    protected SecurityContract.Presenter getPresenter() {
        return new SecurityPresenter(this);
    }

    @Override
    protected void initData() {
        initEvent();
        phone = UserInfoManager.getInstance().getPhone();
        update();
//        mPresenter.getBindStatus();
    }

    private void update() {
        AccountInfoBean.AccountInfo info = AccountCacheManager.getInstance().getAccountInfo();
        google = info == null ? "" : info.getGoogleAuthenticator();
        isStartPhone = AccountCacheManager.getInstance().getIsPhoneAuthEnable();
        isStartGoogle = AccountCacheManager.getInstance().getIsGoogleAuthEnable();
        if (info.getPhoneNumber() == null) {
            mTvAuthorSecurityPhoneBindingStatus.setText(getString(R.string.account_setting));
        }else{
            mTvAuthorSecurityPhoneBindingStatus.setText(getString(isStartPhone ?
                    R.string.account_turned_on : R.string.account_turned_off));
        }

        if ("1".equals(info.getGoogleAuthenticator())) {
            mTvAuthorSecurityGoogleBindingStatus.setText(getString(isStartGoogle ?
                    R.string.account_turned_on : R.string.account_turned_off));
        }else{
            mTvAuthorSecurityGoogleBindingStatus.setText(getString(R.string.account_setting));
        }

        mTvAuthorSecurityModifyFoundsStatus.setText(
                getString(UserInfoManager.getInstance().getHasAssetPassword() ?
                        R.string.account_modify : R.string.account_setting));

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_security);
    }


    @Override
    protected int getContentView() {
        return R.layout.account_activity_security;
    }


    @OnClick({R2.id.btn_author_security_modify_pass, R2.id.btn_author_security_modify_founds,
            R2.id.btn_author_security_gesture_password, R2.id.btn_author_security_phone_binding,
            R2.id.btn_author_security_google_binding})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_author_security_modify_pass) {

            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_EDIT_LOGIN_PASSWORD).navigation(this);

        } else if (i == R.id.btn_author_security_modify_founds) {
            if (UserInfoManager.getInstance().getHasAssetPassword()) {
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_EDIT_ASSET_PASSWORD).navigation(this);
            } else {
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SET_ASSET_PASSWORD).navigation(this);
            }

        } else if (i == R.id.btn_author_security_gesture_password) {
        } else if (i == R.id.btn_author_security_phone_binding) {
            if (!TextUtils.isEmpty(phone)) {
                ARouter.getInstance()
                        .build(AccountArouterConstant.ACCOUNT_PHONE_MANAGER)
                        .withSerializable("bean", mBindStatusBean)
                        .navigation(this);
            } else {
                ARouter.getInstance().
                        build(AccountArouterConstant.ACCOUNT_PHONE_BINDING).
                        withInt("type", 0).
                        navigation(this);
            }

        } else if (i == R.id.btn_author_security_google_binding) {
            if ("1".equals(google)) {
                ARouter.getInstance().
                        build(AccountArouterConstant.ACCOUNT_GOOGLE_SET).
                        withBoolean("isStart", isStartGoogle).
                        navigation(this);

            } else {
                ARouter.getInstance().
                        build(AccountArouterConstant.ACCOUNT_GOOGLE_VERIFY).
                        navigation(this);
            }
        }
    }


    @Override
    public void resultBindStatus(BindStatusBean bean) {
        if (bean == null) {
            return;
        }
        mBindStatusBean = bean;
        isStartPhone = mBindStatusBean.getPhoneAuthenticator() == 1;
        isStartGoogle = mBindStatusBean.getGoogleAuthenticator() == 1;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoEvent event) {
        update();
    }


}
