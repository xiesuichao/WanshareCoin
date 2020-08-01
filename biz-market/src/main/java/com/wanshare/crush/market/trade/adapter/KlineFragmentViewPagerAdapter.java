package com.wanshare.crush.market.trade.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.wanshare.crush.market.trade.view.QuotationKlineFragment;
import com.wanshare.wscomponent.chart.kline.KLineTabLayout;


import java.util.List;

/**
 * k显示对应viewpager的adapter
 * </br>
 * Date: 2018/9/18 20:41
 *
 * @author hemin
 */
public class KlineFragmentViewPagerAdapter extends FragmentStatePagerAdapter {
    private final List<KLineTabLayout.DataItem> periodItems;
    private String market ;
    private int mInitCount;
    public KlineFragmentViewPagerAdapter(FragmentManager fm, List<KLineTabLayout.DataItem> periodItems, String market,int initCount) {
        super(fm);
        this.periodItems = periodItems;
        this.market = market;
        this.mInitCount = initCount;
    }

    @Override
    public Fragment getItem(int position) {
        return QuotationKlineFragment.newInstance(market, periodItems.get(position).getPeriod(), mInitCount);
    }

    @Override
    public int getCount() {
        return periodItems.size();
    }
}