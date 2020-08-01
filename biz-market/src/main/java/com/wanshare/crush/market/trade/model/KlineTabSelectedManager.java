package com.wanshare.crush.market.trade.model;

import android.text.TextUtils;

import com.wanshare.common.base.BaseApplication;

import com.wanshare.wscomponent.chart.kline.Quota;
import com.wanshare.wscomponent.datamanager.SpCache;


/**
 * 对选择的指标进行缓存
 * </br>
 * Date: 2018/8/22 21:32
 *
 * @author hemin
 */
public class KlineTabSelectedManager {
    public static final int TYPE_MAIN_QUOTA = 1000;
    public static final int TYPE_SECOND_QUOTA = 1001;
    public static final String KEY_SELECTED_PERIOD = "key_selected_period";
    public static final String KEY_SELECTED_MAIN_QUOTA = "key_selected_main_quota";
    public static final String KEY_SELECTED_SECOND_QUOTA = "key_selected_second_quota";
    public static final int DEFAULT_PERIOD_POSITION = 4;


    public static void saveMainQuota(String quota) {
        new SpCache(BaseApplication.getInstance().getApplicationContext()).setData(KEY_SELECTED_MAIN_QUOTA, quota);
    }

    public static String getMainQuota() {
        String quota = new SpCache(BaseApplication.getInstance().getApplicationContext()).getString(KEY_SELECTED_MAIN_QUOTA);
        return TextUtils.isEmpty(quota) ? Quota.MA : quota;
    }

    public static void saveSecondQuota(String quota) {
        new SpCache(BaseApplication.getInstance().getApplicationContext()).setData(KEY_SELECTED_SECOND_QUOTA, quota);
    }

    public static String getSecondQuota() {
        String quota = new SpCache(BaseApplication.getInstance().getApplicationContext()).getString(KEY_SELECTED_SECOND_QUOTA);
        return TextUtils.isEmpty(quota) ? "" : quota;
    }

    public static void savePeriod(int position) {
        int periodPosition = position < 0 ? 0 : position;
        new SpCache(BaseApplication.getInstance().getApplicationContext()).setData(KEY_SELECTED_PERIOD, periodPosition);
    }

    /**
     * 默认为1小时，所以position默认为4
     *
     * @return
     */
    public static int getPeriod() {
        Integer periodPosition = new SpCache(BaseApplication.getInstance().getApplicationContext()).getInteger(KEY_SELECTED_PERIOD);
        return -1 == periodPosition ? DEFAULT_PERIOD_POSITION : periodPosition;
    }

}
