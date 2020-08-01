package com.wanshare.crush.account.presenter;

import android.text.TextUtils;

import com.wanshare.crush.account.model.bean.AccountInfoBean;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.account.R;
import com.wanshare.crush.account.contract.AuthorInfoContract;
import com.wanshare.crush.account.model.api.AccountServer;
import com.wanshare.crush.account.model.cache.AccountCacheManager;

import com.wanshare.wscomponent.http.ApiClient;

import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_ACCEPTED;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_APPLIED;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_NONE;
import static com.wanshare.crush.account.view.AuthorRealNameActivity.AUTHOR_TYPE_REJECTED;

public class AuthorInfoPresenter extends BasePresenter<AuthorInfoContract.View> implements AuthorInfoContract.Presenter {


    public AuthorInfoPresenter() {
    }

    public AuthorInfoPresenter(AuthorInfoContract.View rootView) {
        super(rootView);
    }

    public int[] getStatusConfig(String status) {
        int[] configs = new int[3];
        if (AUTHOR_TYPE_REJECTED.equals(status)) {
            configs[0] = R.string.account_author_fail;
            configs[1] = R.color.color_gray_dark2;
            configs[2] = 0;
        } else if (AUTHOR_TYPE_APPLIED.equals(status)) {
            configs[0] = R.string.account_author_loading;
            configs[1] = R.color.color_gray_dark2;
            configs[2] = 0;
        }else if (AUTHOR_TYPE_ACCEPTED.equals(status)) {
            configs[0] = R.string.account_author_success;
            configs[1] = R.color.color_gray_dark2;
            configs[2] = R.drawable.ic_succ;
        }else{
            configs[0] = R.string.account_author_not;
            configs[1] = R.color.color_gray_dark;
            configs[2] = 0;
        }
        return configs;
    }


    /**
     * 是否认证
     */
    public boolean isEnter(String status) {
        return TextUtils.isEmpty(status) ||
                status.equals(AUTHOR_TYPE_NONE) ||
                AUTHOR_TYPE_REJECTED.equals(status);
    }

    @Override
    public void getAccountInfo() {
        if (!UserInfoManager.getInstance().isLogin()) {
            return;
        }
        ApiClient.getInstance().create(AccountServer.class).getAccountInfo()
                .compose(RxSchedulers.<AccountInfoBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<AccountInfoBean>(this) {
                    @Override
                    public void onNext(AccountInfoBean accountInfoBean) {
                        super.onNext(accountInfoBean);
                        AccountCacheManager.getInstance().putAccountInfoBean(accountInfoBean);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultAccountInfo(accountInfoBean);
                    }
                });
    }


}
