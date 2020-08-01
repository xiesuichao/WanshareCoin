package com.wanshare.crush.project.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wanshare.common.base.BaseFragment;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.R2;
import com.wanshare.crush.project.contract.ProjectCons;
import com.wanshare.crush.project.contract.ProjectMarketContract;
import com.wanshare.crush.project.model.bean.ProjectMarket;
import com.wanshare.crush.project.presenter.ProjectMarketPresenter;
import com.wanshare.wscomponent.logger.LogUtil;


import butterknife.BindView;

/**
 * 上线交易所
 * Created by xiesuichao on 2018/8/22.
 */

public class ProjectMarketFragment extends BaseFragment<ProjectMarketContract.Presenter>
        implements ProjectMarketContract.View {

    @BindView(R2.id.rv_online_exchange)
    RecyclerView containerRv;

    private ProjectMarketRvAdapter mRvAdapter;
    private String mProjectId;

    public static ProjectMarketFragment newInstance(String id){
        Bundle bundle = new Bundle();
        bundle.putString(ProjectCons.PROJECT_INTENT_PROJECT_ID, id);
        ProjectMarketFragment projectMarketFragment = new ProjectMarketFragment();
        projectMarketFragment.setArguments(bundle);
        return projectMarketFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            this.mProjectId = bundle.getString(ProjectCons.PROJECT_INTENT_PROJECT_ID);
        }

    }

    @Override
    protected ProjectMarketContract.Presenter getPresenter() {
        return new ProjectMarketPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.project_fragment_detail_online_exchange;
    }

    @Override
    protected void initView() {
        initRecyclerView();

    }

    @Override
    protected void initData() {
        if (mPresenter != null){
            mPresenter.getProjectMarketList("1", 1);
        }
    }

    @Override
    public void getProjectMarketListSuccess(ProjectMarket projectMarket) {
        mRvAdapter.updateList(projectMarket.getItems());
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRvAdapter = new ProjectMarketRvAdapter(getContext());
        containerRv.setLayoutManager(manager);
        containerRv.setNestedScrollingEnabled(false);
        containerRv.setAdapter(mRvAdapter);

    }


}
