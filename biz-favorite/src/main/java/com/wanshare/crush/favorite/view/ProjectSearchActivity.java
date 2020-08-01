package com.wanshare.crush.favorite.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.favorite.FavoriteArouterConstant;
import com.wanshare.common.widget.text.CustomSearchLayout;
import com.wanshare.crush.favorite.R;
import com.wanshare.crush.favorite.R2;
import com.wanshare.crush.favorite.adapter.MarketSearchAdapter;
import com.wanshare.crush.favorite.adapter.ProjectSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yangwenwu on 2018/8/29.
 */
@Route(path = FavoriteArouterConstant.FAVORITE_PROJECT_SEARCH)
public class ProjectSearchActivity extends BaseActivity {

    @BindView(R2.id.csl_favorite_activity_search)
    CustomSearchLayout mCustomSearchLayout;
    @BindView(R2.id.rv_favorite_activity_search)
    RecyclerView mRecyclerView;

    private ProjectSearchAdapter mProjectSearchAdapter;

    @Override
    protected int getContentView() {
        return R.layout.favorite_activity_search;
    }

    @Override
    protected void initView() {
        mMyToolbar.setVisibility(View.GONE);
        mCustomSearchLayout.setHintText(R.string.favorite_enter_project_name);

        mCustomSearchLayout.setOnSearchCancelListener(new CustomSearchLayout.OnSearchCancelListener() {
            @Override
            public void cancel() {
                finish();
            }
        });

        mCustomSearchLayout.setOnTextChangeListener(new CustomSearchLayout.OnInputChangeListener() {
            @Override
            public void textChange(Editable s) {

            }
        });

        mProjectSearchAdapter = new ProjectSearchAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(mProjectSearchAdapter);
    }

    @Override
    protected void initData() {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i + "");
        }

        mProjectSearchAdapter.updateList(list);
    }
}
