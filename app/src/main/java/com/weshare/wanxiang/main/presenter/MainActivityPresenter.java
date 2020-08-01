package com.weshare.wanxiang.main.presenter;

import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.weshare.wanxiang.main.contract.MainContract;

import com.wanshare.wscomponent.http.ApiClient;


/**
 * </br>
 * Date: 2018/9/12 15:43
 *
 * @author hemin
 */
public class MainActivityPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public MainActivityPresenter(MainContract.View rootView) {
        super(rootView);
    }

    @Override
    public void initData() {
        getUserInfo();
    }

    @Override
    public void getUserInfo() {
        if (!UserInfoManager.getInstance().isLogin()) {
            return;
        }
        getAccountInfo();
        getBindStatus();
    }

    private void getAccountInfo() {
        ApiClient.getInstance().create(AccountServer.class).getAccountInfo()
                .compose(RxSchedulers.<AccountInfoBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<AccountInfoBean>(this) {
                    @Override
                    public void onNext(AccountInfoBean accountInfoBean) {
                        super.onNext(accountInfoBean);
                        AccountCacheManager.getInstance().putAccountInfoBean(accountInfoBean);
                        if (accountInfoBean.getAccountInfo().getEmail() != null) {
                            UserInfoManager.getInstance().putEmail(accountInfoBean.getAccountInfo().getEmail().getEmail());
                        }
                        if (accountInfoBean.getAccountInfo().getPhoneNumber() != null) {
                            UserInfoManager.getInstance().putPhone(accountInfoBean.getAccountInfo().getPhoneNumber().getPhoneNumber());
                        }
                        UserInfoManager.getInstance().putHasAssetPassword(accountInfoBean.isTraPassword());
                        UserInfoManager.getInstance().putAccountId(accountInfoBean.getAccountInfo().getAccountId());
                    }
                });
    }

    private void getBindStatus() {
        ApiClient.getInstance().create(AccountServer.class).getBindStatus()
                .compose(RxSchedulers.<BindStatusBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<BindStatusBean>(this) {
                    @Override
                    public void onNext(BindStatusBean bindStatusBean) {
                        super.onNext(bindStatusBean);
                        AccountCacheManager.getInstance().putBindStatusBean(bindStatusBean);
                    }
                });
    }
}
