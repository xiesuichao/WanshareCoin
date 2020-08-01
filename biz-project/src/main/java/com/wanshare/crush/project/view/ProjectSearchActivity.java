package com.wanshare.crush.project.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.project.ProjectArouterConstant;
import com.wanshare.common.widget.text.CustomSearchLayout;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目搜索
 * Created by xiesuichao on 2018/8/23.
 */
@Route(path = ProjectArouterConstant.PROJECT_SEARCH)
public class ProjectSearchActivity extends BaseActivity {

    @BindView(R2.id.csl_project_search)
    CustomSearchLayout mSearchLayout;
    @BindView(R2.id.srl_project_search)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.rv_project_search)
    RecyclerView mContainerRv;

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected int getContentView() {
        return R.layout.project_activity_project_search;
    }

    @Override
    protected void initView() {
        initRecyclerView();

        mRefreshLayout.setEnableRefresh(false);

        mSearchLayout.setOnSearchCancelListener(new CustomSearchLayout.OnSearchCancelListener() {
            @Override
            public void cancel() {
                ProjectSearchActivity.this.finish();
            }
        });

        mSearchLayout.setOnTextChangeListener(new CustomSearchLayout.OnInputChangeListener() {
            @Override
            public void textChange(Editable s) {

            }
        });

        mSearchLayout.setOnKeyboardSearchClickListener(new CustomSearchLayout.OnKeyboardSearchClickListener() {
            @Override
            public void searchClick(String content) {
                System.out.println("---x: " + content);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    private void initRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        ProjectSearchRvAdapter rvAdapter = new ProjectSearchRvAdapter(getApplicationContext());
        mContainerRv.setLayoutManager(manager);
        mContainerRv.setAdapter(rvAdapter);

        List<String> dataList = new ArrayList<>();
        dataList.add("str");
        dataList.add("str");
        dataList.add("str");
        dataList.add("str");
        dataList.add("str");
        dataList.add("str");
        dataList.add("str");

        rvAdapter.updateList(dataList);

    }


}
