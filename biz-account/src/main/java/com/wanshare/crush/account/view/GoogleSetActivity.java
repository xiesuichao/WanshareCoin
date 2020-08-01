package com.wanshare.crush.account.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;

import org.greenrobot.eventbus.EventBus;
import com.wanshare.crush.account.model.cache.AccountCacheManager;

import butterknife.BindView;
import butterknife.OnClick;



/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: google管理界面
 * */
@Route(path = AccountArouterConstant.ACCOUNT_GOOGLE_SET)
public class GoogleSetActivity extends BaseActivity {
    @BindView(R2.id.sw_google_set)
    Switch mSwGoogleSet;
    @BindView(R2.id.btn_google_set)
    ImageView mBtnGoogleSet;
    @BindView(R2.id.btn_google_set_modify)
    RelativeLayout mBtnGoogleSetModify;

    private boolean isStart;

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            isStart = bundle.getBoolean("isStart", false);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.account_google_verify_set);
        mSwGoogleSet.setChecked(isStart);

        mBtnGoogleSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStart){
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GOOGLE_OPERATION)
                            .withInt("type", 1)
                            .navigation(GoogleSetActivity.this, 2);
                }else{
                    ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GOOGLE_START)
                            .withBoolean("isStart", !isStart)
                            .withInt("type", 2)
                            .navigation(GoogleSetActivity.this, 2);
                }

            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_google_set;
    }


    @OnClick(R2.id.btn_google_set_modify)
    public void onViewClicked() {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GOOGLE_QR)
                .withBoolean("isModify", true)
                .navigation(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK) {
            isStart = !isStart;
            AccountCacheManager.getInstance().putIsGoogleAuthEnable(isStart);
            mSwGoogleSet.setChecked(isStart);
            EventBus.getDefault().post(new UserInfoEvent());
        }
    }
}
