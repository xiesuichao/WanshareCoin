package com.wanshare.crush.setting.model.service;


import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 首页接口
 */
public interface SettingServer {

    /**
     * 退出登录
     */
    @POST("logout")
    Observable<JsonObject> loginOut(@Body RequestBody body );
}
