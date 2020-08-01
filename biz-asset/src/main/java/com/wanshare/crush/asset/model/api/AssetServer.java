package com.wanshare.crush.asset.model.api;

import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.GetWithdrawTotalBean;
import com.wanshare.crush.asset.model.bean.RechargeAddressBean;
import com.wanshare.crush.asset.model.bean.RechargeRecordBean;
import com.wanshare.crush.asset.model.bean.SuccessBean;
import com.wanshare.crush.asset.model.bean.UserAsserts;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;
import com.wanshare.crush.asset.model.bean.WithdrawRecordBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author wangdunwei
 * @date 2018/8/22
 */
public interface AssetServer {
    /**
     * 获取提现地址列表
     * 不传coinId 为获取所有币种的提币地址 每页10条数据   传coinId 为获取单个币种的  每页2条数据
     *
     * @param coinId 币种id
     * @param page 当前页
     */
    @GET("withdraw-addresses")
    Observable<WithdrawAddress> getWithdrawAddressList(@Query("coinId") String coinId, @Query("page") int page);

    /**
     * (预留)获取提币地址列表。后台说pageSize无效，可不传。
     */
    @GET("withdraw-addresses")
    Observable<WithdrawAddress> getWithdrawAddressList(@Query("coinId") String coinId, @Query("page") int page,
                                                       @Query("pageSize")int pageSize);

    /**
     * 删除提现地址
     *
     * @param addressId 地址id
     */
    @Headers("Content-Type: application/json")
    @PUT("withdraw-addresses/{id}")
    Observable<Object> deleteWithdrawAddress(@Path("id") String addressId);

    /**
     * 添加提现地址
     *
     */
    @POST("withdraw-addresses")
    Observable<Object> addWithdrawAddress(@Body RequestBody req);

    /**
     * 获取可提币列表
     */
    @GET("coins/withdrawable-lists")
    Observable<List<CoinInfo>> getAvailableWithdrawCoinList();

    /**
     * 获取充币记录
     *
     * @param currentPage 当前页
     */
    @GET("recharge-records")
    Observable<RechargeRecordBean> getRechargeRecord(@Query("page") int currentPage);

    /**
     * (预留)获取充币记录
     *
     * @param currentPage 当前页
     * @param pageSize    每页条目数 可不传
     * @param coinId      币id 可不传
     * @param status      筛选状态：successed failed confirming 可不传
     */
    @GET("recharge-records")
    Observable<RechargeRecordBean> getRechargeRecord(@Query("page") int currentPage, @Query("pageSize")int pageSize,
                                                     @Query("coinId")String coinId, @Query("status")String status);

    /**
     * 获取提币记录
     *
     * @param currentPage 当前页
     * @return
     */
    @GET("withdraw-records")
    Observable<WithdrawRecordBean> getWithdrawRecord(@Query("page") int currentPage);

    /**
     * (预留)获取提币记录
     *
     * @param currentPage 当前页
     * @param pageSize    每页条目数 可不传
     * @param coinId      币id 可不传
     * @param status      筛选状态：successed failed confirming 可不传
     */
    @GET("withdraw-records")
    Observable<WithdrawRecordBean> getWithdrawRecord(@Query("page") int currentPage, @Query("pageSize")int pageSize,
                                                     @Query("coinId")String coinId, @Query("status")String status);

    /**
     * 获取单个币种资产
     * @param coinId 币种Id
     * @return
     */
    @GET("assets/{coinId}")
    Observable<AssetByCoinBean> getAssetByCoinId(@Path("coinId") String coinId);

    /**
     *  获取可充币列表
     */
    @GET("coins/rechargeable-lists")
    Observable<List<CoinInfo>> getRechargeableCoinLists();

    /**
     * 获取单个币种的充币地址
     */
    @GET("coins/recharge-addresses")
    Observable<RechargeAddressBean> getRechargeAddress(@Query("coinId") String coinId);

    /**
     * 获取当日已提币总数
     */
    @GET("withdraw/withdraw-total")
    Observable<GetWithdrawTotalBean> getWithdrawTotalToday(@Query("coinId") String coinId);

    /**
     * 提币
     * @param req
     * @return
     */
    @POST("withdraw")
    Observable<SuccessBean> withdraw(@Body RequestBody req);

    /**
     * 获取币种详细信息
     * @param coinId
     * @return
     */
    @GET("coins/{coinId}")
    Observable<CoinInfo> getCoinInfo(@Path("coinId") String coinId);

    /**
     * 获取帐户资产
     * @param page                   页码
     * @param orderRule              排序规则
     * @param order                  升序/降序
     * @param isHidingEmptyAsset     隐藏总资产为0的内容
     * @param search                 搜索框
     * @return
     */
    @GET("assets")
    Observable<UserAsserts> getAssets(@Query("page") int page,
                                      @Query("pageSize") int pageSize,
                                      @Query("orderRule") String orderRule,
                                      @Query("order") String order,
                                      @Query("isHidingEmptyAsset") boolean isHidingEmptyAsset,
                                      @Query("search") String search);
}
