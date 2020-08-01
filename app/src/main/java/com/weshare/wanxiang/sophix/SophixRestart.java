package com.weshare.wanxiang.sophix;

import android.content.Context;
import android.util.Log;

import com.taobao.sophix.SophixManager;
import com.wanshare.wscomponent.datamanager.SpCache;
import com.wanshare.wscomponent.utils.DeviceUtil;

/**
 * Created by Jason on 2018/8/14.
 * <p>
 * 处理热修复冷启动业务
 */

public class SophixRestart {

    /**
     * 热修复重启的标志位
     */
    public static final String SOPHIX_RESTAR = "sophix_restar";

    private SophixRestart() {
    }

    /**
     * 存储是否重新启动的标志位
     * @param context
     * @param restart
     */
    public static void isRestart(Context context, boolean restart) {
        new SpCache(context).setData(SOPHIX_RESTAR, restart, -1);
    }


    /**
     * 是否需要重启app的业务
     * @param context
     */
    public static void appRestart(Context context) {
        //获取是否有修复包的标志位
        Boolean isRestar = new SpCache(context).getBoolean(SOPHIX_RESTAR);
        Log.d("SophixStubApplication", "isRestar:== "+isRestar);
        if (isRestar && DeviceUtil.isAppOnForGround(context)) {
            //有修复包，需要重启app
            Log.d("SophixStubApplication", "重启app");
            SophixManager.getInstance().killProcessSafely();
        }
    }
}
