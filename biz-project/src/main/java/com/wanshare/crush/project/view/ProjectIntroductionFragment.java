package com.wanshare.crush.project.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.widget.NestedViewPager;
import com.wanshare.common.widget.WrapContentViewPager;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.R2;
import com.wanshare.crush.project.contract.ProjectCons;
import com.wanshare.crush.project.model.bean.Project;
import com.wanshare.crush.project.model.bean.ProjectDetail;
import com.wanshare.wscomponent.logger.LogUtil;

import butterknife.BindView;

/**
 * 项目公示
 * Created by xiesuichao on 2018/8/22.
 */

public class ProjectIntroductionFragment extends BaseFragment {

    @BindView(R2.id.tv_project_composite_info)
    TextView mCompositeInfoTv;
    @BindView(R2.id.iv_project_introduction_show)
    ImageView mShowTextIv;
    @BindView(R2.id.tv_project_detail_official_website)
    TextView mOfficialWebsiteTv;
    @BindView(R2.id.tv_project_detail_issue_volume)
    TextView mIssueVolumeTv;
    @BindView(R2.id.tv_project_detail_issue_time)
    TextView mIssueTimeTv;
    @BindView(R2.id.tv_project_detail_volume_online_market)
    TextView mMarketVolumeTv;

    private boolean isTextOpen = false;
    private ProjectDetail mProjectDetail;

    public static ProjectIntroductionFragment newInstance(ProjectDetail projectDetail){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ProjectCons.PROJECT_EXTRA_PROJECT, projectDetail);
        ProjectIntroductionFragment introductionFragment = new ProjectIntroductionFragment();
        introductionFragment.setArguments(bundle);
        return introductionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            this.mProjectDetail = (ProjectDetail) bundle.getSerializable(ProjectCons.PROJECT_EXTRA_PROJECT);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.project_fragment_detail_project_introduction;
    }

    @Override
    protected void initView() {
        if (mProjectDetail != null){
            mCompositeInfoTv.setText(mProjectDetail.getProjectInfo().getDescription());
            mIssueVolumeTv.setText(mProjectDetail.getCoinInfo().getIssuedVolume());
            mIssueTimeTv.setText(mProjectDetail.getProjectInfo().getAppliedAt());
//            mMarketVolumeTv.setText(mProjectDetail.getProjectInfo().getListedExchange());
            mOfficialWebsiteTv.setText(mProjectDetail.getProjectInfo().getOfficialWebsite());
        }

        mCompositeInfoTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mCompositeInfoTv.getViewTreeObserver().removeOnPreDrawListener(this);
                int textLines = mCompositeInfoTv.getLineCount();
                if (textLines > 4) {
                    mCompositeInfoTv.setMaxLines(4);
                }
                return false;
            }
        });

        mShowTextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTextOpen){
                    mCompositeInfoTv.setMaxLines(50);
                }else {
                    mCompositeInfoTv.setMaxLines(4);
                }
                isTextOpen = !isTextOpen;

            }
        });
    }

    @Override
    protected void initData() {

    }



}
