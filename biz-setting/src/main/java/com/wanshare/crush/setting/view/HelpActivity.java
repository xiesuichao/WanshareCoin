package com.wanshare.crush.setting.view;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleListener;
import com.qiyukf.unicorn.api.lifecycle.SessionLifeCycleOptions;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.setting.R;
import com.wanshare.crush.setting.R2;

import butterknife.OnClick;

/**
 * Created by Jason on 2018/8/23.
 * <p>
 * 帮助页面
 */

@Route(path = SettingArouterConstant.SETTING_HELP)
public class HelpActivity extends BaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(getString(R.string.setting_help));
    }

    @Override
    protected void initData() {
    }

    @OnClick(R2.id.rl_container)
    public void onViewClicked() {
        if (!UserInfoManager.getInstance().isLogin()) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(getContext());
            return;
        }
        startOnlineServiceActivity();
    }

    /**
     * 启动在线客服页面
     */
    private void startOnlineServiceActivity() {
        String title = getString(R.string.setting_online_service);
        ConsultSource source = new ConsultSource(getString(R.string.setting_android), title, getString(R.string.setting_custom) + UserInfoManager.getInstance().getAccountId());
        SessionLifeCycleOptions lifeCycleOptions = new SessionLifeCycleOptions();
        lifeCycleOptions.setCanCloseSession(true);
        lifeCycleOptions.setCanQuitQueue(true);
        lifeCycleOptions.setSessionLifeCycleListener(new SessionLifeCycleListener() {
            @Override
            public void onLeaveSession() {
                Unicorn.logout();
            }
        });

        source.sessionLifeCycleOptions = lifeCycleOptions;
        Unicorn.openServiceActivity(this, title, source);
    }
}
