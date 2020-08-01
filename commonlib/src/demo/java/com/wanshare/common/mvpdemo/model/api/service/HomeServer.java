package com.wanshare.common.mvpdemo.model.api.service;


import com.wanshare.common.mvpdemo.model.bean.Notice;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author wangdunwei
 * @date 2018/6/20
 */
public interface HomeServer {
    /**
     * 获取公共列表
     * @return
     */
    @GET("cms/items?model=announce&stick=Y&status=发布")
    Observable<List<Notice>> getNoticeList();


}
