package com.wanshare.common.base;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.R;
import com.wanshare.common.R2;
import com.wanshare.common.constant.CommonArouterCanstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.wscomponent.webview.ProgressWebView;

import butterknife.BindView;

/**
 * Created by Jason on 2018/8/7.
 */

@Route(path = CommonArouterCanstant.COMMON_WEBVIEW)
public class CommonWebViewActivity extends BaseActivity {

    @BindView(R2.id.progressWebView)
    ProgressWebView mProgressWebView;
    private String mUrl;

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected void initIntent() {
        Intent intent = getIntent();
        if (null != intent) {
            mUrl = intent.getStringExtra(IntentConstant.WEB_URL);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_common_webview;
    }

    @Override
    protected void initView() {
        mProgressWebView.loadUrl(mUrl);
    }

    @Override
    protected void initData() {

        mProgressWebView.setOnTitleChangedListener(new ProgressWebView.OnTitleChangedListener() {
            @Override
            public void onTitleChanged(ProgressWebView progressWebView, String title) {
                mMyToolbar.setTitle(title);
            }
        });

//        mProgressWebView.setShowProgressBar(false);
    }


    @Override
    protected void onBackButton(View view) {
        backPage();
    }

    @Override
    public void onBackPressed() {
        backPage();
    }

    private void backPage(){
        if(mProgressWebView.getWebView().canGoBack()) {
            mProgressWebView.getWebView().goBack();
            return;
        }else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressWebView.destroy();
    }
}
