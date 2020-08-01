package com.weshare.wanxiang.main.model.api.service;


import com.wanshare.crush.exchange.model.bean.TopExchange;
import com.weshare.wanxiang.main.model.bean.ExchangeListBean;
import com.weshare.wanxiang.main.model.bean.ProjectListBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 首页接口
 */
public interface HomepageServer {

    /**
     * 获取首页交易所列表
     *
     * @param currentPage 第几页数据
     * @param pageSize    条目数量
     */
    @GET("exchanges/exchanges")
    Observable<ExchangeListBean> getExchangeList(@Query("page") int currentPage,
                                              @Query("pageSize") int pageSize);


    /**
     * 获取首页项目列表
     *
     * @param currentPage 第几页数据
     * @param pageSize    条目数量
     */
    @GET("project-center/projects")
    Observable<ProjectListBean> getProjectList(@Query("page") int currentPage,
                                               @Query("limit") int pageSize);
}
