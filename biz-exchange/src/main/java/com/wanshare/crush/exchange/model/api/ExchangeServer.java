package com.wanshare.crush.exchange.model.api;


import com.wanshare.common.biz.exchange.ExchangeCoins;
import com.wanshare.common.biz.market.TradingCoins;
import com.wanshare.crush.exchange.model.bean.Market;
import com.wanshare.crush.exchange.model.bean.TopExchange;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 交易所相关接口
 *
 * @author wangdunwei
 * @date 2018/8/25
 */
public interface ExchangeServer {
    String SORT_AMOUNT = "amount";
    String SORT_CONCERN = "ConcernNumber";

    /**
     * 交易所排行列表
     *
     * @param currentPage 第几页数据
     * @param key         关键字排行。可用的排序依据，amount:成交额, ConcernNumber:关注度排行
     * @param pageSize    条目数量
     */
    @GET("exchanges/exchanges")
    Observable<TopExchange> getExchangeBySort(@Query("page") int currentPage,
                                              @Query("sortKey") String key,
                                              @Query("limit") int pageSize);

    /**
     * 获取交易所下某一分区行情列表
     *
     * @param exchangeId 交易所id
     * @param buyerCoinName 买方币种
     * @return Market
     */
    @GET("quotations/summary")
    Observable<List<Market>> getMarketFromExchange(@Query("exchangeId") String exchangeId,
                                                   @Query("buyerCoinName") String buyerCoinName);

    /**
     * 获取交易币种
     */
    @GET("markets/TradingCoins")
    Observable<TradingCoins> getTradingCoins();

    /**
     * 获取交易所下买方和卖方币种
     *
     * @param exchangeId 交易所id
     */
    @GET("exchanges/exchange-coin/{exchangeId}")
    Observable<ExchangeCoins> getExchangeCoins(@Path("exchangeId") String exchangeId);
}
