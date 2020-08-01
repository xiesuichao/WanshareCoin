package com.weshare.wanxiang.main.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.RecycleViewDivider;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.common.biz.app.MainTabChangeEvent;
import com.wanshare.common.biz.project.ProjectArouterConstant;
import com.wanshare.common.biz.setting.SettingInfoManager;
import com.wanshare.common.constant.CommonArouterCanstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.crush.exchange.view.ExchangeActivity;
import com.wanshare.crush.project.contract.ProjectCons;
import com.wanshare.wscomponent.datamanager.SpCache;
import com.wanshare.wscomponent.utils.DeviceUtil;
import com.wanshare.wscomponent.utils.ToastUtil;
import com.weshare.wanxiang.R;
import com.weshare.wanxiang.main.adapter.RecycleExchangeAdapter;
import com.weshare.wanxiang.main.adapter.RecycleProjectAdapter;
import com.weshare.wanxiang.main.contract.HomepageContract;
import com.weshare.wanxiang.main.model.bean.ProjectBean;
import com.weshare.wanxiang.main.presenter.HomepagePresenter;
import com.weshare.wanxiang.main.view.widget.ObservableScrollView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/8/20.
 * <p>
 * 首页
 */

public class HomepageFragment extends BaseFragment<HomepageContract.Presenter> implements HomepageContract.View, ViewTreeObserver.OnGlobalLayoutListener, ObservableScrollView.ScrollViewListener {
    @BindView(R.id.banner_view)
    ImageView mBannerView;
    @BindView(R.id.ll_show_project)
    LinearLayout mLlShowProject;
    @BindView(R.id.ll_vote_add_coin)
    LinearLayout mLlVoteAddCoin;
    @BindView(R.id.ll_share_money)
    LinearLayout mLlShareMoney;
    @BindView(R.id.ll_feedback)
    LinearLayout mLlFeedback;
    @BindView(R.id.iv_more_exchange)
    ImageView mIvMoreExchange;
    @BindView(R.id.rl_exchange)
    RecyclerView mRlExchange;
    @BindView(R.id.iv_more_project)
    ImageView mIvMoreProject;
    @BindView(R.id.rl_project)
    RecyclerView mRlProject;
    @BindView(R.id.scroll_view)
    ObservableScrollView mScrollView;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.fl_search_container)
    FrameLayout mFlSearchContainer;

    //ScroollView滚动距离
    private int mScrollY;
    //记录当前滚动距离的值
    private int mCurrentScrolly = 0;
    private RecycleExchangeAdapter mRecycleExchangeAdapter;
    private RecycleProjectAdapter mRecycleProjectAdapter;
    private boolean mNightMode;
    private int currentPage = 1;
    private int pageSize = 3;

    public static HomepageFragment newInstance() {

        Bundle args = new Bundle();

        HomepageFragment fragment = new HomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_homepage;
    }


    @Override
    protected HomepageContract.Presenter getPresenter() {
        return new HomepagePresenter(this);
    }

    @Override
    protected void initView() {
        mFlSearchContainer.setPadding(mFlSearchContainer.getPaddingLeft(), DeviceUtil.getStatusBarHeight(mActivity), mFlSearchContainer.getPaddingRight(), mFlSearchContainer.getPaddingBottom());

        //黑夜模式标志位
        mNightMode = SettingInfoManager.isNightMode();

        mFlSearchContainer.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mScrollView.setScrollViewListener(this);

        //初始化交易所列表和项目推荐列表
        mRlExchange.setLayoutManager(new LinearLayoutManager(mActivity));
        mRlProject.setLayoutManager(new LinearLayoutManager(mActivity));
        //解决嵌套滑动问题
        setRVConfig(mRlExchange);
        setRVConfig(mRlProject);
        //设置分割线
        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(mActivity, LinearLayoutManager.VERTICAL, 10,
                getResources().getColor(R.color.color_gray_light1));
        mRlExchange.addItemDecoration(recycleViewDivider);
        mRlProject.addItemDecoration(recycleViewDivider);
        //创建adapter
        mRecycleExchangeAdapter = new RecycleExchangeAdapter(mActivity);
        mRecycleProjectAdapter = new RecycleProjectAdapter(mActivity);
        mRlExchange.setAdapter(mRecycleExchangeAdapter);
        mRlProject.setAdapter(mRecycleProjectAdapter);

        //跳转交易所详情
        mRecycleExchangeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Exchange exchange = mRecycleExchangeAdapter.getDatas().get(position);
                ARouter.getInstance()
                        .build(ExchangeArouterConstant.EXCHANGE)
                        .withParcelable(IntentConstant.EXTRA_EXCHANGE, exchange)
                        .withBoolean(ExchangeActivity.EXTRA_IS_COLLECTED, true)
                        .navigation(mActivity);
            }
        });

        //跳转项目详情
        mRecycleProjectAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                ARouter.getInstance().build(ProjectArouterConstant.PROJECT_DETAIL)
                        .withString(ProjectCons.PROJECT_INTENT_PROJECT_ID, mRecycleProjectAdapter.getDatas().get(position).getProjectId())
                        .withString(ProjectCons.PRJECT_INTENT_COIN_SHORT_NAME, mRecycleProjectAdapter.getDatas().get(position).getShortName())
                        .navigation(mActivity);
            }
        });

        //跳转项目快讯详情
        mRecycleProjectAdapter.setOnMessageListener(new RecycleProjectAdapter.OnMessageListener() {
            @Override
            public void onMessageClick(View view) {
                ToastUtil.showShort(mActivity, "跳转项目快讯详情");
            }
        });

        mBannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(CommonArouterCanstant.COMMON_WEBVIEW).withString(IntentConstant.WEB_URL, "https:www.baidu.com").navigation(mActivity);
            }
        });
    }

    /**
     * 解决RecyclerView滑动不流畅问题
     *
     * @param recyclerView
     */
    private void setRVConfig(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    protected void initData() {
        mPresenter.getExchangeList(currentPage, pageSize);
        mPresenter.getProjectList(currentPage, pageSize);
    }

    @OnClick({R.id.ll_show_project, R.id.ll_vote_add_coin, R.id.ll_share_money,
            R.id.ll_feedback, R.id.iv_more_exchange, R.id.iv_more_project, R.id.ll_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //项目公示
            case R.id.ll_show_project:
                ARouter.getInstance().build(ProjectArouterConstant.PROJECT_PUBLICITY).navigation(mActivity);
                break;

            //投票上币
            case R.id.ll_vote_add_coin:
                ToastUtil.showShort(mActivity, "敬请期待");
                break;

            //天天分红
            case R.id.ll_share_money:
                ToastUtil.showShort(mActivity, "敬请期待");
                break;

            //问题反馈
            case R.id.ll_feedback:
                ToastUtil.showShort(mActivity, "敬请期待");
                break;

            //人人交易所
            case R.id.iv_more_exchange:
                EventBus.getDefault().post(new MainTabChangeEvent(MainTabChangeEvent.POSITON_EXCHANGE));
                break;

            //项目中心
            case R.id.iv_more_project:
                ARouter.getInstance().build(ProjectArouterConstant.PROJECT_PUBLICITY).navigation(mActivity);
                break;

            //搜索
            case R.id.ll_search:
                ARouter.getInstance().build(CommonArouterCanstant.COMMON_SEARCH)
                        .withString(IntentConstant.EXTRA_KEYWORDS_FRAGMENT, HomeSearchKeyWordsFragment.class.getName())
                        .withString(IntentConstant.EXTRA_RESULT_FRAGMENT, HomeSearchResultFragment.class.getName())
                        .navigation(mActivity);
                break;
        }
    }

    /**
     * 搜索框测量及布局完成的监听
     * <p>
     * 获取搜索框和banner图的高度
     */
    @Override
    public void onGlobalLayout() {
        //banner图高度
        int BannerViewHeight = mBannerView.getHeight();
        //搜索框容器高度
        int mFlSearchContainerHeight = mFlSearchContainer.getHeight();

        mScrollY = BannerViewHeight - mFlSearchContainerHeight;

        //移除监听
        mFlSearchContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    /**
     * 监听整个页面的滚动，实现搜索框背景渐变效果
     */
    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        mCurrentScrolly = y;
        setTitleStatus(y);
    }

    /**
     * 设置title状态变化
     *
     * @param y
     */
    private void setTitleStatus(int y) {
        if (mNightMode) {
            setNightTitleStatus(y);
        } else {
            setLightTitleStatus(y);
        }
    }

    /**
     * 设置黑夜版滑动状态
     *
     * @param y
     */
    private void setNightTitleStatus(int y) {
        if (y <= 0) {
            mFlSearchContainer.setBackgroundColor(Color.argb(0, 44, 47, 67));
            mView.setBackgroundColor(Color.argb(0, 31, 36, 54));
        } else if (y > 0 && y <= mScrollY) {
            float scale = (float) y / mScrollY;
            float alpha = (255 * scale);
            mFlSearchContainer.setBackgroundColor(Color.argb((int) alpha, 44, 47, 67));
            mView.setBackgroundColor(Color.argb((int) alpha, 31, 36, 54));
        } else {
            mFlSearchContainer.setBackgroundColor(Color.argb(255, 44, 47, 67));
            mView.setBackgroundColor(Color.argb(255, 31, 36, 54));
        }
    }

    /**
     * 设置白天版滑动状态
     *
     * @param y
     */
    private void setLightTitleStatus(int y) {
        if (y <= 0) {
            mFlSearchContainer.setBackgroundColor(Color.argb(0, 255, 255, 255));
            mView.setBackgroundColor(Color.argb(0, 243, 244, 246));
            mLlSearch.setBackgroundResource(R.drawable.common_shape_bg_search);
        } else if (y > 0 && y <= mScrollY) {
            float scale = (float) y / mScrollY;
            float alpha = (255 * scale);
            mFlSearchContainer.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            mView.setBackgroundColor(Color.argb((int) alpha, 243, 244, 246));
            mLlSearch.setBackgroundResource(R.drawable.common_shape_bg_search);
        } else {
            mLlSearch.setBackgroundResource(R.drawable.common_shape_bg_search_up);
            mFlSearchContainer.setBackgroundColor(Color.argb(255, 255, 255, 255));
            mView.setBackgroundColor(Color.argb(255, 243, 244, 246));
        }
    }

    @Override
    public void showExchangeList(List<Exchange> exchanges) {
        mRecycleExchangeAdapter.updateList(exchanges);
    }

    @Override
    public void showProjectList(List<ProjectBean> projects) {
        mRecycleProjectAdapter.updateList(projects);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleStatus(mCurrentScrolly);
    }
}
