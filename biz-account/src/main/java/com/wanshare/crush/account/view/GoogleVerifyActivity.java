package com.wanshare.crush.account.view;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.R2;
import com.wanshare.crush.account.model.bean.event.UserInfoEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: google下载界面
 * */
@Route(path = AccountArouterConstant.ACCOUNT_GOOGLE_VERIFY)
public class GoogleVerifyActivity extends BaseActivity {
    public static final String GOOGLE_AUTHENTICATION_APK="https://wanxiang-app.oss-cn-shenzhen.aliyuncs.com/Android/com.google.android.apps.authenticator2_5.00.apk";
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        initEvent();
        mMyToolbar.setTitle(R.string.account_google_verify_title);
    }

    @Override
    protected int getContentView() {
        return R.layout.account_activity_google_verify;
    }


    @OnClick({R2.id.btn_google_verify_download, R2.id.btn_google_verify_next_steps})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_google_verify_download) {
            openBrowser(GOOGLE_AUTHENTICATION_APK);
        } else if (i == R.id.btn_google_verify_next_steps) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_GOOGLE_QR).navigation(this);

        }
    }

    /**
     * 调用第三方浏览器打开
     * @param url 要浏览的资源地址
     */
    public  void openBrowser(String url){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoEvent event) {
        finish();
    }
}
