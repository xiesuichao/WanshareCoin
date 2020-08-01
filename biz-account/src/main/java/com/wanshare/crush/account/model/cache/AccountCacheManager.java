package com.wanshare.crush.account.model.cache;

import com.wanshare.common.base.BaseApplication;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.wscomponent.datamanager.DataCache;
import com.wanshare.wscomponent.datamanager.SpCache;

/**
 * Created by Richard on 2018/7/31
 * 用户信息管理类
 */
public class AccountCacheManager {
    static final String SP_FILE_NAME = "account_cache";
    static final String KEY_ACCOUNT_INFO_BEAN = "key_account_info_bean";
    static final String KEY_IS_GOOGLE_AUTH_ENABLE = "key_is_google_auth_enable";
    static final String KEY_IS_PHONE_AUTH_ENABLE = "key_is_phone_auth_enable";
    static final String KEY_IS_EMAIL_AUTH_ENABLE = "key_is_email_auth_enable";


    private static AccountCacheManager mInstance;
    private DataCache mDataCache;

    private AccountCacheManager() {
        mDataCache = new SpCache(BaseApplication.getInstance(), SP_FILE_NAME);
    }

    public static AccountCacheManager getInstance() {
        if (mInstance == null) {
            mInstance = new AccountCacheManager();
        }
        return mInstance;
    }

    public void clearUserInfo() {
        mDataCache.clear(true);
    }


    public void putAccountInfoBean(AccountInfoBean accountInfoBean) {
        mDataCache.setObject(KEY_ACCOUNT_INFO_BEAN, accountInfoBean);
    }

    public AccountInfoBean getAccountInfoBean() {
        return (AccountInfoBean) mDataCache.getObject(KEY_ACCOUNT_INFO_BEAN);
    }


    public AccountInfoBean.AccountInfo.PhoneNumberBean getPhoneNumberBean() {
        AccountInfoBean.AccountInfo accountInfo = getAccountInfo();
        return accountInfo == null ? null : accountInfo.getPhoneNumber();
    }

    public AccountInfoBean.AccountInfo.EmailBean getEmailBean() {
        AccountInfoBean.AccountInfo accountInfo = getAccountInfo();
        return accountInfo == null ? null : accountInfo.getEmail();
    }

    public AccountInfoBean.AccountInfo getAccountInfo() {
        AccountInfoBean bean = getAccountInfoBean();
        return bean == null ? null : bean.getAccountInfo();
    }


    public void putIsGoogleAuthEnable(boolean isEnable) {
        mDataCache.setData(KEY_IS_GOOGLE_AUTH_ENABLE, isEnable);
    }

    public boolean getIsGoogleAuthEnable() {
        return mDataCache.getBoolean(KEY_IS_GOOGLE_AUTH_ENABLE);
    }

    public void putIsPhoneAuthEnable(boolean isEnable) {
        mDataCache.setData(KEY_IS_PHONE_AUTH_ENABLE, isEnable);
    }

    public boolean getIsPhoneAuthEnable() {
        return mDataCache.getBoolean(KEY_IS_PHONE_AUTH_ENABLE);
    }

    public void putIsEmailAuthEnable(boolean isEnable) {
        mDataCache.setData(KEY_IS_EMAIL_AUTH_ENABLE, isEnable);
    }

    public boolean getIsEmailAuthEnable() {
        return mDataCache.getBoolean(KEY_IS_EMAIL_AUTH_ENABLE);
    }

    public void putBindStatusBean(BindStatusBean bean) {
        if (bean == null) {
            return;
        }
        putIsEmailAuthEnable(bean.getEmailAuthenticator() == 1);
        putIsPhoneAuthEnable(bean.getPhoneAuthenticator() == 1);
        putIsGoogleAuthEnable(bean.getGoogleAuthenticator() == 1);
    }

}
