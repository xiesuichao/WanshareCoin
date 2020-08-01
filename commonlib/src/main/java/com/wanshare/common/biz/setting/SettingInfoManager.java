package com.wanshare.common.biz.setting;

import android.support.v7.app.AppCompatDelegate;

import com.wanshare.common.base.BaseApplication;
import com.wanshare.wscomponent.datamanager.SpCache;

/**
 * app设置相关信息
 * </br>
 * Date: 2018/9/8 15:43
 *
 * @author hemin
 */
public class SettingInfoManager {

    private static final String KEY_NIGHT_MODE = "key_night_mode";

    /**
     * 获取黑夜模式的值
     *
     * @return AppCompatDelegate.MODE_NIGHT_YES ,AppCompatDelegate.MODE_NIGHT_NO
     */
    public static int getNightMode() {
        int nightMode = new SpCache(BaseApplication.getInstance().getApplicationContext()).getInteger(KEY_NIGHT_MODE);
        if (nightMode == -1) {
            return AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            return nightMode;
        }
    }

    /**
     * 切换黑夜模式
     */
    public static void changeNightMode() {
        if (isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            new SpCache(BaseApplication.getInstance().getApplicationContext()).setData(KEY_NIGHT_MODE,
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            new SpCache(BaseApplication.getInstance().getApplicationContext()).setData(KEY_NIGHT_MODE,
                    AppCompatDelegate.MODE_NIGHT_YES);

        }
    }

    /**
     * 是否黑夜模式
     * @return
     */
    public static boolean isNightMode() {
        return getNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }

}
