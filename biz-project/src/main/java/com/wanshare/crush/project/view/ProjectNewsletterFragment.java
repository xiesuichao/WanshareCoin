package com.wanshare.crush.project.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wanshare.common.base.BaseFragment;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.R2;
import com.wanshare.crush.project.model.bean.Newsletter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目快讯
 * Created by xiesuichao on 2018/8/22.
 */

public class ProjectNewsletterFragment extends BaseFragment {

    @BindView(R2.id.rv_project_newsletter)
    RecyclerView containerRv;


    @Override
    protected int getContentView() {
        return R.layout.project_fragment_detail_project_newsletter;
    }

    @Override
    protected void initView() {
        initRecyclerView();

    }

    @Override
    protected void initData() {

    }

    private void initRecyclerView(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        ProjectNewsletterRvAdapter rvAdapter = new ProjectNewsletterRvAdapter(getContext());
        containerRv.setLayoutManager(manager);
        containerRv.setNestedScrollingEnabled(false);
        containerRv.setAdapter(rvAdapter);

        List<Newsletter> dataList = new ArrayList<>();
        dataList.add(new Newsletter());
        dataList.add(new Newsletter());
        dataList.add(new Newsletter());

        rvAdapter.updateList(dataList);
    }

}
