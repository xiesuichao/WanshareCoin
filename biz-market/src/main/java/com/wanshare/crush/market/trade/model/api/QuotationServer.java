package com.wanshare.crush.market.trade.model.api;


import com.wanshare.crush.market.trade.model.bean.CoinAssetsBean;
import com.wanshare.crush.market.trade.model.bean.QuotationHistory;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author hemin
 * @date 2018/8/22
 */
public interface QuotationServer {

    /**
     * 获取k线历史数据
     * @return
     */
    @GET("quotations/historical-data")
    Observable<List<QuotationHistory>> getHistoricalData(@Query("symbol") String symbol,
                                                         @Query("period") String period,
                                                         @Query("from") String from,
                                                         @Query("to") String to);

    /**
     * 获取单个币种资产
     * @return
     */
    @GET("assets/{coinId}")
    Observable<CoinAssetsBean> queryCoinAssert(@Path("coinId") String coinId);

}
