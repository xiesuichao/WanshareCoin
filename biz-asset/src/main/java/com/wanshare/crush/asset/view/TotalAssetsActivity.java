package com.wanshare.crush.asset.view;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.RecycleViewDivider;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.adapter.TotalAssetsAdapter;
import com.wanshare.crush.asset.contract.TotalAssetsContract;
import com.wanshare.crush.asset.model.bean.AssetInfoBean;
import com.wanshare.crush.asset.presenter.TotalAssetsPresenter;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Richard on 2018/8/28
 */
@Route(path = AssetArouterConstant.ASSET_TOTAL_ASSETS)
public class TotalAssetsActivity extends BaseActivity<TotalAssetsContract.Presenter> implements TotalAssetsContract.View {
    @BindView(R2.id.rv_assets)
    RecyclerView rvAssets;
    @BindView(R2.id.layout_smart_refresh)
    SmartRefreshLayout layoutSmartRefresh;
    private TotalAssetsAdapter mAdapter;

    @Override
    protected TotalAssetsContract.Presenter getPresenter() {
        return new TotalAssetsPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_total_assets;
    }

    @Override
    protected void initView() {
        initTitle();
        initRV();
        initSmartRefresh();
    }

    @Override
    public void showHintMessage(@NonNull String message) {
        super.showHintMessage(message);
        layoutSmartRefresh.finishRefresh();
        layoutSmartRefresh.finishLoadMore();
    }

    private void initTitle() {
        mMyToolbar.setTitle(R.string.asset_total_assets);
        mMyToolbar.setRightButtonText(R.string.asset_financial_record);
        mMyToolbar.setOnRightButtonTextClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转财务记录
                ARouter.getInstance().build(AssetArouterConstant.ASSET_RECORD).navigation(TotalAssetsActivity.this);
            }
        });
    }

    private void initRV() {
        rvAssets.setLayoutManager(new LinearLayoutManager(this));
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.color_gray_light1));
        rvAssets.addItemDecoration(recycleViewDivider);
        mAdapter = new TotalAssetsAdapter(this, new ArrayList<Parcelable>());
        rvAssets.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Parcelable clickItem = mAdapter.getItem(position);
                if (mAdapter.getItemViewType(position) == TotalAssetsAdapter.VIEW_TYPE_NORMAL && clickItem instanceof AssetInfoBean) {
                    AssetInfoBean assetInfoBean = (AssetInfoBean) clickItem;
                    ARouter.getInstance().build(AssetArouterConstant.ASSET_ASSETS_DETAIL).withParcelable(IntentConstant.EXTRA_CURRENCY, assetInfoBean).navigation(TotalAssetsActivity.this);
                }
            }
        });
    }

    private void initSmartRefresh() {
        layoutSmartRefresh.setEnableRefresh(true);
        layoutSmartRefresh.setEnableLoadMore(true);
        layoutSmartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.refresh();
    }

    @Override
    public void onRefreshResult(List<Parcelable> list, boolean hasMore) {
        if (hasMore) {
            layoutSmartRefresh.finishRefresh();
            layoutSmartRefresh.setNoMoreData(false);
        } else {
            layoutSmartRefresh.finishRefresh();
            layoutSmartRefresh.finishLoadMoreWithNoMoreData();
        }
        mAdapter.updateList(list);
    }

    @Override
    public void onLoadMoreResult(List<Parcelable> list, boolean hasMore) {
        if (hasMore) {
            layoutSmartRefresh.finishLoadMore();
        } else {
            layoutSmartRefresh.finishLoadMoreWithNoMoreData();
        }
        mAdapter.addList(list);
    }

    @Override
    public void showToast(int msgId) {
        ToastUtil.showShort(this, msgId);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(this, msg);
    }

}
