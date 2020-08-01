package com.weshare.wanxiang;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.moduth.blockcanary.BlockCanary;
import com.qiyukf.unicorn.api.ImageLoaderListener;
import com.qiyukf.unicorn.api.StatusBarNotificationConfig;
import com.qiyukf.unicorn.api.Unicorn;
import com.qiyukf.unicorn.api.UnicornImageLoader;
import com.qiyukf.unicorn.api.YSFOptions;
import com.squareup.leakcanary.LeakCanary;
import com.taobao.sophix.SophixManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.wanshare.common.base.BaseApplication;
import com.wanshare.common.biz.setting.SettingInfoManager;
import com.wanshare.common.constant.AppConfig;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.wscomponent.datamanager.EnDecryptUtil;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.DeviceUtil;
import com.wanshare.wscomponent.websocket.WebSocketResponseDispatcher;
import com.wanshare.wscomponent.websocket.WebSocketService;
import com.wanshare.wscomponent.websocket.WebSocketSetting;
import com.weshare.wanxiang.monitor.AppBlockCanaryContext;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * Created by Jason on 2018/7/24.
 */

public class MyApplication extends BaseApplication {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void init() {
        super.init();

        Context context = getApplicationContext();
        //查询阿里后台是否有修复包
        SophixManager.getInstance().queryAndLoadNewPatch();
        //初始化ApiClient
        initApiClient();
        //初始化友盟
        initUM(context);
        //初始化bugly
        initBugly(context);

        EnDecryptUtil.init(getApplicationContext());
        //初始化网易七鱼客服
        initNetEaseService();

        //所有activity生命周期方法的回调
        ActivityLifecycleCallbacksImpl activityLifecycleCallbacksImpl = new ActivityLifecycleCallbacksImpl();
        this.registerActivityLifecycleCallbacks(activityLifecycleCallbacksImpl);

        //7.0拍照问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        initArouter(this);
        initLogger();

        //初始化websokcet
        initWebsocket();

        //设置黑夜/白天模式
        AppCompatDelegate.setDefaultNightMode(SettingInfoManager.getNightMode());

        initLeakCanary();

        initBlockCanary();
    }

    private void initBlockCanary() {
        if (AppConfig.DEBUG) {
            BlockCanary.install(this, new AppBlockCanaryContext()).start();
        }
    }

    private void initLeakCanary() {
        if (AppConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
    }

    private void initApiClient() {
        ApiClient.init(AppConfig.getBaseUrl(), new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Response response;
                if (UserInfoManager.getInstance().isLogin()) {
                    String token = UserInfoManager.getInstance().getToken();
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authentication-Token", token)
                            .build();
                    response = chain.proceed(newRequest);
                } else {
                    response = chain.proceed(chain.request());
                }

                return response;
            }
        });
    }

    private void initLogger() {
        if (AppConfig.DEBUG) {
            LogUtil.init();
        }
    }

    private void initArouter(Application context) {
        if (AppConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(context);
    }

    private void initUM(Context context) {
        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        //初始化组件化基础库
        UMConfigure.init(context, getString(R.string.app_um_key), getString(R.string.app_um_channel), UMConfigure.DEVICE_TYPE_PHONE, null);
    }

    private void initBugly(Context context) {
        boolean isDebug = AppConfig.DEBUG;
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = DeviceUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly
        CrashReport.initCrashReport(context, "d33be3e58a", isDebug, strategy);
    }

    private void initNetEaseService() {
        Unicorn.init(this, "ee411684bb4e9762c6ee79db74c9fb8d", options(), new UnicornImageLoader() {
            @Nullable
            @Override
            public Bitmap loadImageSync(String uri, int width, int height) {
                return null;
            }

            @Override
            public void loadImage(String uri, int width, int height, ImageLoaderListener listener) {

            }
        });
    }

    private YSFOptions options() {
        YSFOptions options = new YSFOptions();
        options.statusBarNotificationConfig = new StatusBarNotificationConfig();
        return options;
    }

    private void initWebsocket(){

        WebSocketSetting.setConnectUrl(AppConfig.WEBSOCKET_URL);//必选
        WebSocketSetting.setResponseProcessDelivery(new WebSocketResponseDispatcher());
        WebSocketSetting.setReconnectWithNetworkChanged(true);

        //启动 WebSocket 服务
        startService(new Intent(getApplicationContext(), WebSocketService.class));
    }

}
