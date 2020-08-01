package com.wanshare.crush.favorite.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.crush.favorite.R;
import com.wanshare.crush.favorite.R2;
import com.wanshare.crush.favorite.adapter.FavoriteMarketAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangwenwu on 2018/8/28.
 */

public class FavoriteMarketFragment extends BaseListFragment {

    @BindView(R2.id.cb_favorite_market_fragment_price)
    CheckBox mCbPrice;
    @BindView(R2.id.cb_favorite_market_fragment_change_24)
    CheckBox mCbChange;

    FavoriteMarketAdapter mFavoriteMarketAdapter;

    public static FavoriteMarketFragment newInstance(){

        Bundle args = new Bundle();

        FavoriteMarketFragment fragment = new FavoriteMarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.favorite_market_fragment;
    }

    @Override
    protected void initView() {
        super.initView();

        mCbPrice.setSelected(true);
        mCbPrice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbPrice.setSelected(true);
                mCbChange.setSelected(false);
            }
        });
        mCbChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbPrice.setSelected(false);
                mCbChange.setSelected(true);
            }
        });

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
            }
        });

        mFavoriteMarketAdapter = new FavoriteMarketAdapter(getContext());
        setAdapter(mFavoriteMarketAdapter);

        mFavoriteMarketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE_QUOTATION_DETAIL).navigation(mActivity);
            }
        });
    }

    @Override
    protected void initData() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i + "");
        }

        updateData(list,1);
    }
}
