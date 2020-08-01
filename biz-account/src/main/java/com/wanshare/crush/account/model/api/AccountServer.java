package com.wanshare.crush.account.model.api;


import com.google.gson.JsonObject;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.model.bean.AuthorBean;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.bean.CompanyBean;
import com.wanshare.crush.account.model.bean.GoogleInfoEntity;
import com.wanshare.crush.account.model.bean.IndividualBean;
import com.wanshare.crush.account.model.bean.LoginBean;
import com.wanshare.crush.account.model.bean.UploadBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PUT;

//import io.reactivex.Observable;
//import retrofit2.http.GET;

/**
 * @author xuwenxiang
 * @date 2018/6/20
 */
public interface AccountServer {

    /**
     * 绑定手机号码
     *
     * @param body
     */
    @POST("bind-phone")
    Observable<Object> bindPhone(@Body RequestBody body);

    /**
     * 开启手机验证
     *
     * @param body
     */
    @POST("open-phone-authenticator")
    Observable<Object> openPhone(@Body RequestBody body);

    /**
     * 关闭手机验证
     *
     * @param body
     */
    @POST("close-phone-authenticator")
    Observable<Object> closePhone(@Body RequestBody body);

    /**
     * 获取验证码
     *
     * @param body
     */
    @POST("send-verification-code")
    Observable<Object> sendVerificationCode(@Body RequestBody body);

    /**
     * 个人实名认证
     *
     * @param body
     */
    @POST("certifications/individual")
    Observable<Object> individual(@Body RequestBody body);

    /**
     * 查询个人实名认证
     *
     */
    @GET("accounts/individual")
    Observable<AuthorBean> queryIndividual();

    /**
     * 企业认证
     *
     * @param body
     */
    @POST("certifications/company")
    Observable<Object> company(@Body RequestBody body);


    /**
     * 查询企业认证
     *
     */
    @GET("accounts/company")
    Observable<AuthorBean> queryCompany();

    /**
     * 绑定google验证
     *
     * @param body
     */
    @POST("bind-google-authenticator")
    Observable<Object> bindGoogleAuthenticator(@Body RequestBody body);

    /**
     * 开启google验证
     *
     * @param body
     */
    @POST("open-google-authenticator")
    Observable<Object> openGoogleAuthenticator(@Body RequestBody body);


    /**
     * 关闭google验证
     *
     * @param body
     */
    @POST("close-google-authenticator")
    Observable<Object> closeGoogleAuthenticator(@Body RequestBody body);

    /**
     * 修改google验证
     *
     * @param body
     */
    @POST("alter-google-authenticator")
    Observable<Object> modifyGoogleAuthenticator(@Body RequestBody body);

    /**
     * 获取绑定状态
     *
     */
    @GET("get-bind-status")
    Observable<BindStatusBean> getBindStatus();


    /**
     * Google 验证初始化
     *
     * @param body
     */
    @POST("init-googleauth")
    Observable<GoogleInfoEntity> initGoogleauth(@Body RequestBody body);

    /**
     * 修改绑定状态
     *
     * @param body
     */
    @POST("set-bind-statue")
    Observable<String> setBindStatue(@Body RequestBody body);


    /**
     * 登录
     * @param body
     * @return
     */
    @POST("login")
    Observable<LoginBean> login(@Body RequestBody body);

    /**
     * 注册
     * @param body
     * @return
     */
    @POST("register")
    Observable<LoginBean> register(@Body RequestBody body);

    /**
     * 获取账户状态信息
     *
     * @return
     */
    @GET("account-info")
    Observable<AccountInfoBean> getAccountInfo();

    /**
     * 验证码验证
     * @param body
     * @return
     */
    @POST("verify")
    Observable<JsonObject> verify(@Body RequestBody body);

    /**
     * 重置密码信息
     * @param body
     * @return
     */
    @POST("reset-password")
    Observable<JsonObject> resetPassword(@Body RequestBody body);

    /**
     * 设置资金密码
     * @param body
     * @return
     */
    @PUT("assets/asset-password")
    Observable<JsonObject> setAssetPassword(@Body RequestBody body);

    /**
     * 修改资金密码
     * @param body
     * @return
     */
    @POST("assets/asset-password")
    Observable<JsonObject> editAssetPassword(@Body RequestBody body);

    /**
     * 修改登录
     * @param body
     * @return
     */
    @POST("set-password")
    Observable<JsonObject> editLoginPassword(@Body RequestBody body);

    @Multipart
    @POST("file/upload")
    Observable<UploadBean> uploadFile(@Part MultipartBody.Part body);

    @Multipart
    @POST("file/upload")
    Call<UploadBean> uploadFileSn(@Part MultipartBody.Part body);


}
