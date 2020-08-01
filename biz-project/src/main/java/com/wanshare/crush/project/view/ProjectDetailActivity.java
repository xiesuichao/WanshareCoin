package com.wanshare.crush.project.view;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.project.ProjectArouterConstant;
import com.wanshare.common.biz.setting.SettingInfoManager;
import com.wanshare.common.widget.CustomTabLayout;
import com.wanshare.common.widget.NestedViewPager;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.R2;
import com.wanshare.crush.project.contract.ProjectCons;
import com.wanshare.crush.project.contract.ProjectDetailContract;
import com.wanshare.crush.project.model.bean.ProjectDetail;
import com.wanshare.crush.project.presenter.ProjectDetailPresenter;
import com.wanshare.wscomponent.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目详情
 * Created by xiesuichao on 2018/8/22.
 */
@Route(path = ProjectArouterConstant.PROJECT_DETAIL)
public class ProjectDetailActivity extends BaseActivity<ProjectDetailContract.Presenter>
        implements ProjectDetailContract.View {

    @BindView(R2.id.srl_project_detail)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R2.id.vp_project_detail_container)
    NestedViewPager mContainerVp;
    @BindView(R2.id.ctl_project_detail)
    CustomTabLayout mDetailTab;
    @BindView(R2.id.tv_project_detail_currency)
    TextView mCurrencyTv;
    @BindView(R2.id.tv_project_detail_coin_name)
    TextView mCoinNameTv;
    @BindView(R2.id.tv_project_detail_full_name)
    TextView mFullNameTv;
    @BindView(R2.id.tv_project_detail_current_time)
    TextView mCurrentTimeTv;
    @BindView(R2.id.tv_project_detail_coin_price)
    TextView mCoinPriceTv;
    @BindView(R2.id.tv_project_detail_coin_kind)
    TextView mCoinKindTv;
    @BindView(R2.id.tv_project_detail_currency_price)
    TextView mCurrencyPriceTv;
    @BindView(R2.id.ll_project_publicity_currency)
    LinearLayout mCurrencyLl;


    private PopupWindow mCurrencyPop;
    private boolean isShowCny = true; //false:dollar
    private String mProjectId;
    private String mCoinShortName;


    @Override
    protected ProjectDetailContract.Presenter getPresenter() {
        return new ProjectDetailPresenter(this);
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected int getContentView() {
        return R.layout.project_activity_project_detail;
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        mProjectId = getIntent().getStringExtra(ProjectCons.PROJECT_INTENT_PROJECT_ID);
        mCoinShortName = getIntent().getStringExtra(ProjectCons.PRJECT_INTENT_COIN_SHORT_NAME);
    }

    @Override
    protected void initView() {
        mDetailTab.setTitleArr(new String[]{getString(R.string.project_online_exchange),
                getString(R.string.project_introduction), getString(R.string.project_newsletter)});
        mDetailTab.setOnTabClickListener(new CustomTabLayout.OnTabClickListener() {
            @Override
            public void tabClick(int position, String str) {
                mContainerVp.setCurrentItem(position);
            }
        });

        mDetailTab.setOnTabScrollListener(new CustomTabLayout.OnTabScrollListener() {
            @Override
            public void scrollChange(int position, String text) {
                if (position == 1){
                    mRefreshLayout.setEnableLoadMore(false);
                }else {
                    mRefreshLayout.setEnableLoadMore(true);
                }
            }
        });

    }

    @Override
    protected void initData() {
        mMyToolbar.setTitle(mCoinShortName);

        if (mPresenter != null) {
            mPresenter.getProjectDetail(mProjectId);
        }

        mCurrencyLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrencySelectPop();
            }
        });
    }

    @Override
    public void getProjectDetailSuccess(ProjectDetail projectDetail) {
        mCoinNameTv.setText(projectDetail.getCoinInfo().getShortName());
        mFullNameTv.setText("(" + projectDetail.getCoinInfo().getFullName() + ")");
        mCurrentTimeTv.setText(projectDetail.getCoinInfo().getIssuedAt());
        mCoinPriceTv.setText(projectDetail.getCoinInfo().getIssuePrice());
        if (isShowCny) {
            mCurrencyPriceTv.setText("≈ " + getString(R.string.project_rmb_sign) + " " + projectDetail.getSummary().getCnyPrice());
        } else {
            mCurrencyPriceTv.setText("≈ " + getString(R.string.project_usd_sign) + " " + projectDetail.getSummary().getUsdPrice());
        }

        initViewPager(projectDetail);

    }

    private void showCurrencySelectPop() {
        if (mCurrencyPop == null) {
            initCurrencySelectPop();
        }
        mCurrencyPop.showAsDropDown(mCurrencyTv);
    }

    private void initCurrencySelectPop() {
        View view = View.inflate(getApplicationContext(), R.layout.project_pop_detail_currency_select, null);
        TextView rmbTv = view.findViewById(R.id.tv_project_currency_pop_rmb);
        TextView dollarTv = view.findViewById(R.id.tv_project_currency_pop_dollar);
        CardView popCv = view.findViewById(R.id.cv_project_detail_pop);
        LinearLayout popLl = view.findViewById(R.id.ll_project_detail_pop);
        if (SettingInfoManager.isNightMode()){
            view.setBackgroundColor(0xff2c2f43);
            popCv.setBackgroundColor(0xff2c2f43);
            popLl.setBackgroundColor(0xff2c2f43);
            rmbTv.setBackground(getResources().getDrawable(R.drawable.common_selector_white_gray_top_radius3_night));
            dollarTv.setBackground(getResources().getDrawable(R.drawable.common_selector_white_gray_bot_radius3_night));

        }else {
            view.setBackgroundColor(0xffFFFFFF);
            popCv.setBackgroundColor(0xffFFFFFF);
            popLl.setBackgroundColor(0xffFFFFFF);
            rmbTv.setBackground(getResources().getDrawable(R.drawable.common_selector_white_gray_top_radius3));
            dollarTv.setBackground(getResources().getDrawable(R.drawable.common_selector_white_gray_bot_radius3));
        }

        rmbTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrencyTv.setText(R.string.project_rmb);
                mCurrencyPop.dismiss();
                isShowCny = true;
            }
        });

        dollarTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrencyTv.setText(R.string.project_dollar);
                mCurrencyPop.dismiss();
                isShowCny = false;
            }
        });

        mCurrencyPop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mCurrencyPop.setFocusable(true);

    }

    private void initViewPager(ProjectDetail projectDetail) {
        FragmentManager manager = getSupportFragmentManager();
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ProjectMarketFragment.newInstance(mProjectId));
        fragmentList.add(ProjectIntroductionFragment.newInstance(projectDetail));
        fragmentList.add(new ProjectNewsletterFragment());

        ProjectDetailVpAdapter vpAdapter = new ProjectDetailVpAdapter(manager, fragmentList);
        mContainerVp.setAdapter(vpAdapter);
        mContainerVp.setOffscreenPageLimit(2);

        mContainerVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mDetailTab.moveToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


}
