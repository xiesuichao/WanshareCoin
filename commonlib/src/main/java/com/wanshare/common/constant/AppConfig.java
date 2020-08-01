package com.wanshare.common.constant;

import com.wanshare.common.BuildConfig;

/**
 * Created by Jason on 2018/7/21.
 * <p>
 * 全局常量配置
 */

public class AppConfig {
    public static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String BASE_URL = "http://www.wanshare.com";
    private static final String DEBUG_BASE_URL = "http://192.168.80.211:8888/v1/";
    public static final String WEBSOCKET_URL = "ws://192.168.80.211:8888/v1/crush/k-line/331/13atbidg/websocket";//ws://192.168.21.14:8888/v1/depth/612/uiryleuy/websocket";
    public static final String WEBSOCKET_TEST_URL = "wss://wsbeta.wanshare.com/socket.io/?EIO=3&transport=websocket";

    public static String getBaseUrl() {
        if (DEBUG) {
            return DEBUG_BASE_URL;
        }
        return BASE_URL;
    }

    public static String getGeetestUrl() {
        return getBaseUrl() + "init-captcha";
    }

}
