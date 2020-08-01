package com.wanshare.crush.project.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.project.ProjectArouterConstant;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.R2;
import com.wanshare.crush.project.contract.ProjectCons;
import com.wanshare.crush.project.contract.ProjectPublicityContract;
import com.wanshare.crush.project.model.bean.Project;
import com.wanshare.crush.project.presenter.ProjectPublicityPresenter;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 项目公示板
 * Created by xiesuichao on 2018/8/21.
 */
@Route(path = ProjectArouterConstant.PROJECT_PUBLICITY)
public class ProjectPublicityListActivity extends BaseActivity<ProjectPublicityContract.Presenter>
        implements ProjectPublicityContract.View {

    @BindView(R2.id.srl_project_publicity)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.rv_project_publicity)
    RecyclerView mProjectRv;
    @BindView(R2.id.ll_project_publicity_comment)
    LinearLayout mCommentLl;
    @BindView(R2.id.ll_project_publicity_update)
    LinearLayout mUpdateTimeLl;
    @BindView(R2.id.ll_project_publicity_volume)
    LinearLayout mVolumeLl;

    private ProjectPublicityRvAdapter mRvAdapter;



    @Override
    protected ProjectPublicityContract.Presenter getPresenter() {
        return new ProjectPublicityPresenter(this);
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected int getContentView() {
        return R.layout.project_activity_project_publicity;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(getString(R.string.project_publicity));
        mMyToolbar.setRightButtonImage(R.drawable.ic_home_search);
        mMyToolbar.setRightButtonImageVisible(true);
        mMyToolbar.setOnRightButtonImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(ProjectArouterConstant.PROJECT_SEARCH).navigation(ProjectPublicityListActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        initRecyclerView();
        if (mPresenter != null){
            mPresenter.getProjectList(1, "activity", 5);
        }
    }

    @Override
    public void getProjectListSuccess(Project project) {
        mRvAdapter.updateList(project.getItems());
    }

    private void initRecyclerView(){
        mRvAdapter = new ProjectPublicityRvAdapter(getApplicationContext());
        mProjectRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mProjectRv.setAdapter(mRvAdapter);

        mRvAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ARouter.getInstance().build(ProjectArouterConstant.PROJECT_DETAIL)
                        .withString(ProjectCons.PROJECT_INTENT_PROJECT_ID, mRvAdapter.getDatas().get(position).getProjectId())
                        .withString(ProjectCons.PRJECT_INTENT_COIN_SHORT_NAME, mRvAdapter.getDatas().get(position).getShortName())
                        .navigation(ProjectPublicityListActivity.this);
            }
        });

    }

    @OnClick({R2.id.ll_project_publicity_comment, R2.id.ll_project_publicity_update, R2.id.ll_project_publicity_volume})
    public void onViewClick(View v){
        int i = v.getId();
        if (i == R.id.ll_project_publicity_comment) {


        } else if (i == R.id.ll_project_publicity_update) {


        } else if (i == R.id.ll_project_publicity_volume) {


        }

    }


}
