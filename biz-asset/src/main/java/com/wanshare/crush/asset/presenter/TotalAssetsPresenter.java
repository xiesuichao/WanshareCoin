package com.wanshare.crush.asset.presenter;

import android.os.Parcelable;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.asset.contract.TotalAssetsContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.UserAsserts;

import java.util.ArrayList;
import java.util.List;

import com.wanshare.wscomponent.http.ApiClient;


/**
 * Created by Richard on 2018/8/28
 */
public class TotalAssetsPresenter extends BasePresenter<TotalAssetsContract.View> implements TotalAssetsContract.Presenter {

    static final int PAGE_SIZE = 5;
    private int currentPage = 1;


    public TotalAssetsPresenter(TotalAssetsContract.View rootView) {
        super(rootView);
    }

    @Override
    public void refresh() {
        currentPage = 1;
        getAssets(currentPage, PAGE_SIZE, "", "", false, "");
    }

    @Override
    public void loadMore() {
        currentPage++;
        getAssets(currentPage, PAGE_SIZE, "", "", false, "");
    }

    private void getAssets(final int page, int pageSize, String orderRule, String order, boolean isHidingEmptyAsset, String search) {
        ApiClient.getInstance().create(AssetServer.class)
                .getAssets(page, pageSize, orderRule, order, isHidingEmptyAsset, search)
                .compose(RxSchedulers.<UserAsserts>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<UserAsserts>(this) {

                    @Override
                    public void onNext(UserAsserts userAsserts) {
                        super.onNext(userAsserts);
                        if (mRootView == null) {
                            return;
                        }
                        UserAsserts.MetaBean metaBean = userAsserts.getMeta();
                        boolean hasMore = true;
                        if (metaBean != null) {
                            if (metaBean.getPageSize() * metaBean.getPageNo() >= metaBean.getTotalCount()) {
                                hasMore = false;
                            }
                        }
                        List<Parcelable> showAssets = new ArrayList();
                        if (userAsserts.getAssetInfo() != null) {
                            showAssets.addAll(userAsserts.getAssetInfo());
                        }
                        if (currentPage == 1) {
                            if (userAsserts.getEstimates() != null) {
                                showAssets.add(0, userAsserts.getEstimates());
                            }
                            mRootView.onRefreshResult(showAssets, hasMore);
                        } else {
                            mRootView.onLoadMoreResult(showAssets, hasMore);
                        }
                    }
                });
    }
}
