package com.wanshare.crush.account;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseApplication;
import com.wanshare.common.constant.AppConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import wanshare.wscomponent.http.ApiClient;

public class AccountApplication extends BaseApplication {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        initArouter(this);
        ApiClient.init(getString(R.string.account_base_url), new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return null;
            }
        });
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    private void initArouter(Application context) {
        if (AppConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(context);
    }

}
