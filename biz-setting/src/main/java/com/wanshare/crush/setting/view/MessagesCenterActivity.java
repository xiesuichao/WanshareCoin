package com.wanshare.crush.setting.view;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.setting.SettingArouterConstant;
import com.wanshare.common.constant.CommonArouterCanstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.setting.R;
import com.wanshare.crush.setting.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/9/25.
 *
 * 消息中心页面
 */
@Route(path = SettingArouterConstant.SETTING_MESSAGES)
public class MessagesCenterActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_messages_center;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(getString(R.string.setting_messages_center));
    }

    @Override
    protected void initData() {
    }

    @OnClick({R2.id.ll_system_messages, R2.id.ll_my_messages})
    public void onViewClicked(View view) {
        int id = view.getId();
        String title = "";
        if (id == R.id.ll_system_messages) {
            title = getString(R.string.setting_system_messages);
        } else if (id == R.id.ll_my_messages) {
            title = getString(R.string.setting_my_messages);
        }

        ARouter.getInstance().build(SettingArouterConstant.SETTING_MESSAGES_LIST)
                .withString(IntentConstant.EXTRA_MESSAGES_TITLE, title)
                .navigation(mContext);
    }
}
