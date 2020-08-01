package com.wanshare.crush.favorite.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.favorite.FavoriteArouterConstant;
import com.wanshare.crush.favorite.R;
import com.wanshare.crush.favorite.R2;
import com.wanshare.wscomponent.utils.DeviceUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangwenwu on 2018/8/28.
 */

public class FavoriteFragment extends BaseFragment {

    private final int MARKET = 0;
    private final int EXCHANGE = 1;
    private final int PROJECT = 2;

    @BindView(R2.id.ll_root_container)
    LinearLayout mLlRootContainer;
    @BindView(R2.id.tv_favorite_fragment_market)
    TextView mTvMarket;
    @BindView(R2.id.tv_favorite_fragment_exchange)
    TextView mTvExchange;
    @BindView(R2.id.tv_favorite_fragment_project)
    TextView mTvProject;
    @BindView(R2.id.iv_favorite_fragment_more)
    ImageView mIvMore;

    @BindView(R2.id.vp_favorite_fragment)
    ViewPager mFavorite;

    private BaseFragment[] fragments =
            {
                    FavoriteMarketFragment.newInstance(),
                    FavoriteExchangeFragment.newInstance(),
                    FavoriteProjectFragment.newInstance()
            };

    public static FavoriteFragment newInstance(){

        Bundle args = new Bundle();

        FavoriteFragment fragment = new FavoriteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.favorite_fragment;
    }

    @Override
    protected void initView() {

        mLlRootContainer.setPadding(mLlRootContainer.getPaddingLeft(), DeviceUtil.getStatusBarHeight(mActivity),mLlRootContainer.getPaddingRight(),mLlRootContainer.getPaddingBottom());

        mFavorite.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }
        });

        mFavorite.setCurrentItem(0);

        mFavorite.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case MARKET:
                        mTvMarket.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_blue_left_radius4));
                        mTvMarket.setTextColor(getResources().getColor(R.color.color_white2));
                        mTvExchange.setBackgroundColor(getResources().getColor(R.color.color_white));
                        mTvExchange.setTextColor(getResources().getColor(R.color.color_main_light));
                        mTvProject.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_right_radius4));
                        mTvProject.setTextColor(getResources().getColor(R.color.color_main_light));
                        break;
                    case EXCHANGE:
                        mTvMarket.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_left_radius4));
                        mTvMarket.setTextColor(getResources().getColor(R.color.color_main_light));
                        mTvExchange.setBackgroundColor(getResources().getColor(R.color.color_main_light));
                        mTvExchange.setTextColor(getResources().getColor(R.color.color_white2));
                        mTvProject.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_right_radius4));
                        mTvProject.setTextColor(getResources().getColor(R.color.color_main_light));
                        break;
                    case PROJECT:
                        mTvMarket.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_left_radius4));
                        mTvMarket.setTextColor(getResources().getColor(R.color.color_main_light));
                        mTvExchange.setBackgroundColor(getResources().getColor(R.color.color_white));
                        mTvExchange.setTextColor(getResources().getColor(R.color.color_main_light));
                        mTvProject.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_blue_right_radius4));
                        mTvProject.setTextColor(getResources().getColor(R.color.color_white2));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void initData() {

    }

    @OnClick({R2.id.tv_favorite_fragment_market,R2.id.tv_favorite_fragment_exchange, R2.id.tv_favorite_fragment_project,R2.id.iv_favorite_fragment_more})
    public void onViewClicked(View v){
        int i = v.getId();
        if (i == R.id.tv_favorite_fragment_market) {
            mFavorite.setCurrentItem(0);
            mTvMarket.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_blue_left_radius4));
            mTvMarket.setTextColor(getResources().getColor(R.color.color_white));
            mTvExchange.setBackgroundColor(getResources().getColor(R.color.color_white));
            mTvExchange.setTextColor(getResources().getColor(R.color.color_main_light));
            mTvProject.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_right_radius4));
            mTvProject.setTextColor(getResources().getColor(R.color.color_main_light));

        } else if (i == R.id.tv_favorite_fragment_exchange) {
            mFavorite.setCurrentItem(1);
            mTvMarket.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_left_radius4));
            mTvMarket.setTextColor(getResources().getColor(R.color.color_main_light));
            mTvExchange.setBackgroundColor(getResources().getColor(R.color.color_main_light));
            mTvExchange.setTextColor(getResources().getColor(R.color.color_white));
            mTvProject.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_right_radius4));
            mTvProject.setTextColor(getResources().getColor(R.color.color_main_light));

        } else if (i == R.id.tv_favorite_fragment_project) {
            mFavorite.setCurrentItem(2);
            mTvMarket.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_white_left_radius4));
            mTvMarket.setTextColor(getResources().getColor(R.color.color_main_light));
            mTvExchange.setBackgroundColor(getResources().getColor(R.color.color_white));
            mTvExchange.setTextColor(getResources().getColor(R.color.color_main_light));
            mTvProject.setBackground(getResources().getDrawable(R.drawable.common_shape_bg_blue_right_radius4));
            mTvProject.setTextColor(getResources().getColor(R.color.color_white));

        } else if (i == R.id.iv_favorite_fragment_more) {
            favoriteMore();

        }
    }

    private void favoriteMore(){

        switch (mFavorite.getCurrentItem()){
            case MARKET:
                ARouter.getInstance().build(FavoriteArouterConstant.FAVORITE_MARKET_MANAGER).navigation(mActivity);
                break;
            case EXCHANGE:
                ARouter.getInstance().build(FavoriteArouterConstant.FAVORITE_EXCHANGE_MANAGER).navigation(mActivity);
                break;
            case PROJECT:
                ARouter.getInstance().build(FavoriteArouterConstant.FAVORITE_PROJECT_MANAGER).navigation(mActivity);
                break;
        }
    }
}
