package com.wanshare.crush.market.order.model.api;

import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.common.biz.market.TradingCoins;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yangwenwu on 2018/8/30.
 *
 * 市场订单相关接口
 */

public interface MarketOrderServer {


    /**
     * 获取委托订单历史订单列表
     * @param page  页码
     * @param exchangeId  交易所id
     * @param tradingPair 交易币对
     * @param type  委托单类型
     * @param status 委托单状态的数组
     * @return  委托订单数据
     */
    @GET("entrusts")
    Observable<EntrustsOrder> getEntrusts(@Query("page") int page,
                                          @Query("exchangeId") String exchangeId,
                                          @Query("tradingPair") String tradingPair,
                                          @Query("type") String type,
                                          @Query("status") String[] status);

    /**
     * 创建委托订单
     * @param body
     */
    @POST("entrusts")
    Observable<Object> entrusts(@Body RequestBody body);


    /**
     * 撤消委托订单
     * @param id 订单id
     */
    @POST("entrusts/{id}/cancel")
    Observable<Object> entrustsCancel(@Path("id") String id);

    @GET("coins")
    Observable<String> getCoins(@Query("type") String type);

    @GET("markets/TradingCoins")
    Observable<TradingCoins> getTradingCoins();
}
