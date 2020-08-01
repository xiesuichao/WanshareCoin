package com.wanshare.crush.favorite.view;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.favorite.FavoriteArouterConstant;
import com.wanshare.crush.favorite.R;
import com.wanshare.crush.favorite.adapter.MarketManagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangwenwu on 2018/8/27.
 */
@Route(path = FavoriteArouterConstant.FAVORITE_MARKET_MANAGER)
public class MarketManagerActivity extends BaseListActivity implements OnRefreshListener, OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener, MultiItemTypeAdapter.OnItemLongClickListener {

    private MarketManagerAdapter marketManagerAdapter;

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setBackgroundColor(getResources().getColor(R.color.color_white));
        mMyToolbar.setTitle(R.string.favorite_market_manager);
        mMyToolbar.setTitleTextStyle(Typeface.DEFAULT_BOLD);
        mMyToolbar.setRightButtonText(R.string.favorite_add);

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(this));

        marketManagerAdapter = new MarketManagerAdapter(mContext);
        setAdapter(marketManagerAdapter);

        //设置下拉刷新和上拉加载的监听
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);

        //设置条目点击事件
        marketManagerAdapter.setOnItemClickListener(this);
        marketManagerAdapter.setOnItemLongClickListener(this);
    }

    @Override
    protected void initData() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }

        updateData(list,1);
    }

    @Override
    protected int getContentView() {
        return R.layout.favorite_activity_manager;
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    protected void onRightButton(View view) {
        super.onRightButton(view);

        ARouter.getInstance().build(FavoriteArouterConstant.FAVORITE_MARKET_SEARCH).navigation(getContext());
    }
}
