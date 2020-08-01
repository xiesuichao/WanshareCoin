package com.wanshare.crush.favorite.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.crush.favorite.R;
import com.wanshare.crush.favorite.R2;
import com.wanshare.crush.favorite.adapter.FavoriteMarketAdapter;
import com.wanshare.crush.favorite.adapter.FavoriteProjectAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangwenwu on 2018/8/28.
 */

public class FavoriteProjectFragment extends BaseListFragment {


    public static FavoriteProjectFragment newInstance(){

        Bundle args = new Bundle();

        FavoriteProjectFragment fragment = new FavoriteProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.favorite_project_fragment;
    }

    @Override
    protected void initView() {
        super.initView();

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

        setAdapter(new FavoriteProjectAdapter(getContext()));
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
