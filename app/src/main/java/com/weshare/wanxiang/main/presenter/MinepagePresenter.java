package com.weshare.wanxiang.main.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.crush.account.model.bean.BindStatusBean;
import com.wanshare.crush.account.model.cache.AccountCacheManager;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.UserAsserts;
import com.weshare.wanxiang.main.contract.MinepageContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

public class MinepagePresenter extends BasePresenter<MinepageContract.View> implements MinepageContract.Presenter {

    private AssetServer mAssetServer;

    public MinepagePresenter(MinepageContract.View rootView){
        super(rootView);
        mAssetServer = ApiClient.getInstance().create(AssetServer.class);
    }

    @Override
    public void getUserAsserts(int page, int pageSize,String orderRule, String order, boolean isHidingEmptyAsset, String search) {

        mAssetServer.getAssets(page, pageSize,orderRule, order, isHidingEmptyAsset, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<UserAsserts>(this) {
                    @Override
                    public void onNext(UserAsserts userAsserts) {
                        super.onNext(userAsserts);

                        if (mRootView == null) return;

                        mRootView.showUserAsserts(userAsserts);
                    }
                });
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

                        if (mRootView == null) return;

                        mRootView.showUserInfo();
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
