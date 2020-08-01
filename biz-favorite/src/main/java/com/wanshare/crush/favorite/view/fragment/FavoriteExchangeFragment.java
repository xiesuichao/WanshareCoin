package com.wanshare.crush.favorite.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.crush.favorite.R;
import com.wanshare.crush.favorite.R2;
import com.wanshare.crush.favorite.adapter.FavoriteExchangeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangwenwu on 2018/8/28.
 */

public class FavoriteExchangeFragment extends BaseListFragment {

    @BindView(R2.id.cb_favorite_exchange_fragment_exchange_market)
    CheckBox mCbMarket;
    @BindView(R2.id.cb_favorite_market_fragment_change_24)
    CheckBox mCbChange;

    public static FavoriteExchangeFragment newInstance(){

        Bundle args = new Bundle();

        FavoriteExchangeFragment fragment = new FavoriteExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.favorite_exchange_fragment;
    }

    @Override
    protected void initView() {
        super.initView();

        mCbMarket.setSelected(true);
        mCbMarket.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbMarket.setSelected(true);
                mCbChange.setSelected(false);
            }
        });
        mCbChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCbMarket.setSelected(false);
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

        setAdapter(new FavoriteExchangeAdapter(getContext()));
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
