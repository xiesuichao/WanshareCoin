package com.wanshare.crush.account;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.AccountArouterConstant;

import okhttp3.logging.HttpLoggingInterceptor;
import wanshare.wscomponent.http.ApiClient;


public class MainActivity extends BaseActivity {


    @Override
    protected void initData() {
        ApiClient.init(getString(R.string.account_base_url),new HttpLoggingInterceptor());
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(R.string.app_name);
        mMyToolbar.setBackButtonImage(0);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public void security(View view) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_SECURITY).navigation(this);
    }

    public void authorMessage(View view) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_AUTHOR_INFO).navigation(this);
    }

    public void login(View view) {
        ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(this);
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }
}
