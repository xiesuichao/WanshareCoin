package com.wanshare.common.usercenter;

import android.text.TextUtils;

import com.wanshare.common.base.BaseApplication;
import com.wanshare.wscomponent.datamanager.DataCache;
import com.wanshare.wscomponent.datamanager.SpCache;

/**
 * Created by Richard on 2018/7/31
 * 用户信息管理类
 */
public class UserInfoManager {
    static final String SP_FILE_NAME = "user_info";

    static final String KEY_TOKEN = "key_token";
    static final String KEY_EMAIL = "key_email";
    static final String KEY_PHONE = "key_phone";
    static final String KEY_HAS_ASSET_PASSWORD = "key_has_asset_password";
    static final String KEY_ACCOUNT_ID = "key_account_id";


    private static UserInfoManager mInstance;
    private DataCache mDataCache;

    private UserInfoManager() {
        mDataCache = new SpCache(BaseApplication.getInstance(), SP_FILE_NAME);
    }

    public static UserInfoManager getInstance() {
        if (mInstance == null) {
            mInstance = new UserInfoManager();
        }
        return mInstance;
    }

    public void clearUserInfo() {
        mDataCache.clear(true);
    }

    public String getPhone() {
        return mDataCache.getString(KEY_PHONE);
    }

    public String getEmail() {
        return mDataCache.getString(KEY_EMAIL);
    }

    public void putPhone(String phone) {
        mDataCache.setData(KEY_PHONE, phone);
    }

    public void putEmail(String email) {
        mDataCache.setData(KEY_EMAIL, email);
    }

    public void putToken(String token) {
        mDataCache.setData(KEY_TOKEN, token);
    }

    public String getToken() {
        return mDataCache.getString(KEY_TOKEN);
    }


    public boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    public void putHasAssetPassword(boolean has) {
        mDataCache.setData(KEY_HAS_ASSET_PASSWORD, has);
    }

    public boolean getHasAssetPassword() {
        return mDataCache.getBoolean(KEY_HAS_ASSET_PASSWORD);
    }

    public void putAccountId(String accountId) {
        mDataCache.setData(KEY_ACCOUNT_ID, accountId);
    }

    public String getAccountId() {
        return mDataCache.getString(KEY_ACCOUNT_ID);
    }

}
