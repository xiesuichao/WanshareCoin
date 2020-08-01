package com.wanshare.crush.exchange.view.fragment;

import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.common.widget.viewpager.ViewPagerIndicator;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.adapter.ExchangeBannerAdapter;
import com.wanshare.crush.exchange.model.bean.TestData;
import com.wanshare.wscomponent.toolbar.MyToolbar;
import com.wanshare.wscomponent.utils.DeviceUtil;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * @author wangdunwei
 * @date 2018/8/25
 */
public class ExchangeFragment extends BaseFragment {
    @BindView(R2.id.cl_root)
    CoordinatorLayout clRoot;
    @BindView(R2.id.my_toolbar)
    MyToolbar myToolbar;
    @BindView(R2.id.vpi_type)
    ViewPagerIndicator vpiType;
    @BindView(R2.id.vp_exchange)
    ViewPager vpExchange;
    @BindView(R2.id.vp_banner)
    ViewPager vpBanner;
    @BindView(R2.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R2.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
//    @BindView(R2.id.toolbar)
//    Toolbar toolbar;

    private BaseFragment[] fragments =
            {
                    new TradeAmountExchangeFragment(),
                    new HotExchangeFragment()
            };

    @BindArray(R2.array.exchange_vp_titles)
    String[] titles;
    private ExchangeBannerAdapter exchangeBannerAdapter;

    public static ExchangeFragment newInstance() {
        return new ExchangeFragment();
    }

    @Override
    protected int getContentView() {
        return R.layout.exchange_fragment;
    }

    @Override
    protected void initView() {
        final int statusBarHeight = DeviceUtil.getStatusBarHeight(mActivity);
        appBarLayout.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams lp = appBarLayout.getLayoutParams();
                lp.height += statusBarHeight;
            }
        });

        ViewGroup.LayoutParams lp = myToolbar.getLayoutParams();
        lp.height += statusBarHeight;
        myToolbar.setPadding(myToolbar.getPaddingLeft(), myToolbar.getPaddingTop() + statusBarHeight,
                myToolbar.getPaddingRight(), myToolbar.getPaddingBottom());

        CollapsingToolbarLayout.LayoutParams ctLp = (CollapsingToolbarLayout.LayoutParams) vpBanner.getLayoutParams();
        ctLp.topMargin += statusBarHeight;

        myToolbar.setOnRightButtonImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                       .build(ExchangeArouterConstant.SEARCH_COLLECTION_EXCHANGE)
                       .navigation(getContext());
            }
        });

        vpExchange.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        vpiType.setTitles(titles, vpExchange);

        //测试代码
        exchangeBannerAdapter = new ExchangeBannerAdapter(TestData.ofBannerList());
        vpBanner.setAdapter(exchangeBannerAdapter);
    }

    @Override
    protected void initData() {

    }

}
