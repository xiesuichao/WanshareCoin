package com.wanshare.crush.setting.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.JsonObject;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.model.LoginEvent;
import com.wanshare.common.biz.app.AppArouterConstant;
import com.wanshare.common.biz.app.MainTabChangeEvent;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.setting.R;
import com.wanshare.crush.setting.R2;
import com.wanshare.crush.setting.contract.SettingContract;
import com.wanshare.crush.setting.model.bean.LanguageBean;
import com.wanshare.crush.setting.model.bean.ValuationBean;
import com.wanshare.crush.setting.presenter.SettingPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/8/23.
 * <p>
 * 设置页面
 */

@Route(path = SettingArouterConstant.SETTING_MAIN)
public class SettingActivity extends BaseActivity<SettingContract.Presenter> implements SettingContract.View {

    @BindView(R2.id.tv_language)
    TextView mTvLanguage;
    @BindView(R2.id.tv_valuation)
    TextView mTvValuation;
    @BindView(R2.id.btn_login_out)
    Button mBtnLoginOut;

    @Override
    protected void initView() {
        mMyToolbar.setTitle(getString(R.string.setting_setting));
        //根据用户是否登录来决定退出登录的显示
        mBtnLoginOut.setVisibility(UserInfoManager.getInstance().isLogin() ? View.VISIBLE : View.INVISIBLE);
        initEvent();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected SettingPresenter getPresenter() {

        return new SettingPresenter(this);
    }

    @OnClick({R2.id.rl_language_chose, R2.id.rl_valuation_mode, R2.id.rl_about_us, R2.id.btn_login_out})
    public void onViewClicked(View view) {
        int id = view.getId();
        //语言选择
        if (id == R.id.rl_language_chose) {
            ARouter.getInstance().build(SettingArouterConstant.SETTING_LANGUAGE).navigation(mContext);

            //计价模式
        } else if (id == R.id.rl_valuation_mode) {
            ARouter.getInstance().build(SettingArouterConstant.SETTING_VALUATION).navigation(mContext);

            //关于我们
        } else if (id == R.id.rl_about_us) {
            ARouter.getInstance().build(SettingArouterConstant.SETTING_ABOUT).navigation(mContext);

            //退出登录
        } else if (id == R.id.btn_login_out) {
            mPresenter.loginOut();
        }
    }

    @Override
    public void onLoginOutSuccess(JsonObject success) {
        showShortToast(getString(R.string.setting_logout_success));
        doAfterLoginOut();
    }

    @Override
    public void onLoginOutError() {
        doAfterLoginOut();
    }

    /**
     * 退出登录后的操作
     */
    public void doAfterLoginOut() {
        UserInfoManager.getInstance().clearUserInfo();
        EventBus.getDefault().post(new MainTabChangeEvent(MainTabChangeEvent.POSITON_HOME));
        EventBus.getDefault().post(new LoginEvent(LoginEvent.LOGOUT_SUCCESS));
        ARouter.getInstance().build(AppArouterConstant.APP_MAIN).navigation(mContext);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLanguageChoseEvent(LanguageBean event) {
        if (null != event) {
            mTvLanguage.setText(event.getLanguage());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onValuationChoseEvent(ValuationBean event) {
        if (null != event) {
            mTvValuation.setText(event.getValuation());
        }
    }
}
