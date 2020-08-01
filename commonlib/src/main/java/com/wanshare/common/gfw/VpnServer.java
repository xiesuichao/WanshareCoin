package com.wanshare.common.gfw;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface VpnServer {

    /**
     * 上传vps地址信息
     * @param requestBody vps 地址信息[包含访问成功地址与访问失败地址信息]
     * @return
     */
    @PUT("vps")
    Observable<Object> putVpsAddress(@Body RequestBody requestBody);

    /**
     * 获取vps地址信息
     * @param url  地址信息请求的url
     * @return
     */
    @GET
    Observable<VpnAddress> getVpnAddress(@Url String url);
}
