package com.wanshare.crush.market.trade.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.base.UpdateChildUIListener;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.common.widget.viewpager.CommonFragmentPagerAdapter;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.trade.adapter.KlineFragmentViewPagerAdapter;
import com.wanshare.crush.market.trade.contract.QuotationDetailContract;
import com.wanshare.crush.market.trade.event.KlineTabChangeEvent;
import com.wanshare.crush.market.trade.model.KlineTabSelectedManager;
import com.wanshare.crush.market.trade.model.bean.OverviewBean;
import com.wanshare.crush.market.trade.presenter.QuotationDetailPresenter;
import com.wanshare.wscomponent.chart.kline.KLineTabLayout;
import com.wanshare.wscomponent.chart.kline.KLineTabLayoutLandscape;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.DensityUtil;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * k线横屏显示
 * </br>
 * Date: 2018/8/21 18:07
 *
 * @author hemin
 */
@Route(path = MarketArouterConstant.MARKET_TRADE_KLINE_LANDSCAPE)
public class KlineLandscapeActivity extends BaseActivity<QuotationDetailContract.Presenter> implements QuotationDetailContract.View {

    static final String EXTRA_MARKET_NAME = "extra_market_name";

    @BindView(R2.id.tv_trade_market)
    TextView mTvTradeMarket;
    @BindView(R2.id.tv_trade_unit)
    TextView mTvTradeUnit;
    @BindView(R2.id.tv_price)
    TextView mTvPrice;
    @BindView(R2.id.tv_cny_price)
    TextView mTvCnyPrice;
    @BindView(R2.id.tv_price_rise)
    TextView mTvPriceRise;
    @BindView(R2.id.tv_price_rise_percent)
    TextView mTvPriceRisePercent;
    @BindView(R2.id.tv_vol_value)
    TextView mTvVolValue;
    @BindView(R2.id.tv_price_max_value)
    TextView mTvPriceMaxValue;
    @BindView(R2.id.tv_price_min_value)
    TextView mTvPriceMinValue;

    @BindView(R2.id.tabLayoutKline)
    KLineTabLayoutLandscape mTabLayoutKline;

    @BindView(R2.id.viewPager)
    ViewPager mViewPager;
    private KlineFragmentViewPagerAdapter mViewPagerAdapter;

    private String mMarketName;

    String symbol = "123092:BTC/ETH";
    @Override
    protected QuotationDetailContract.Presenter getPresenter() {
        return new QuotationDetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("leon= KlineLandscapeActivity onCreate");
    }

    @Override
    protected void initIntent() {
        mMarketName = getIntent().getStringExtra(EXTRA_MARKET_NAME);
        mMarketName = "BTC/USDT";
    }

    @Override
    protected int getContentView() {
        return R.layout.market_activity_kline_fullscreen;
    }

    @Override
    protected void initView() {

        mTvTradeMarket.setText(getCoin());
        mTvTradeUnit.setText("/"+getUnit());
        List<KLineTabLayout.DataItem> periodItems = new ArrayList<>();
        String[] periodTabs = getResources().getStringArray(R.array.market_condition_period_tabs);
        for (int i = 0; i < periodTabs.length; i++) {
            String[] split = periodTabs[i].split("\\|");
            String label = split[0];
            String period = split[1];
            periodItems.add(new KLineTabLayout.DataItem(label, period));
        }

        mViewPagerAdapter = new KlineFragmentViewPagerAdapter(getSupportFragmentManager(), periodItems,mMarketName,80);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        mTabLayoutKline.initTabs(periodItems);

        mTabLayoutKline.setOnTabSelectListener(new KLineTabLayout.onTabSelectListener() {
            @Override
            public void onPeriodSelected(String label, String period, int position) {
                KlineTabSelectedManager.savePeriod(position);
                mViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onMainQuotaSelected(String quota) {
                KlineTabSelectedManager.saveMainQuota(quota);
                EventBus.getDefault().post(new KlineTabChangeEvent(KlineTabSelectedManager.TYPE_MAIN_QUOTA));
            }

            @Override
            public void onSecondQuotaSelected(String quota) {
                KlineTabSelectedManager.saveSecondQuota(quota);
                EventBus.getDefault().post(new KlineTabChangeEvent(KlineTabSelectedManager.TYPE_SECOND_QUOTA));
            }
        });

        int position = KlineTabSelectedManager.getPeriod();
        mViewPager.setCurrentItem(position);
        mTabLayoutKline.setSelectedPeriod(position);
        mTabLayoutKline.setSelectedQuota(KlineTabSelectedManager.getMainQuota(), KlineTabSelectedManager.getSecondQuota());
    }

    @Override
    protected void initData() {
        mPresenter.getOverView(symbol);
    }

    @OnClick({R2.id.iv_exit})
    public void onViewClicked(View v){
        int id = v.getId();
        if(id == R.id.iv_exit){
            finish();
        }
    }

    private void updateTopUI(OverviewBean bean) {
        if (mTvPrice == null|| bean == null ) {
            return;
        }

        boolean isRise = !bean.getChangePct().contains("-");

        mTvPrice.setText(bean.getLatestPrice());
        mTvCnyPrice.setText( bean.getCnyLatestPrice());
        mTvPriceRise.setText(bean.getChangeExtent());

        StringBuilder risePercentBuilder = new StringBuilder();
        if (isRise) {
            mTvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_red_light));
            mTvPriceRise.setTextColor(ContextCompat.getColor(this, R.color.color_red_light));
            mTvPriceRisePercent.setTextColor(ContextCompat.getColor(this, R.color.color_red_light));
        } else {
            mTvPrice.setTextColor(ContextCompat.getColor(this, R.color.color_green_light));
            mTvPriceRise.setTextColor(ContextCompat.getColor(this, R.color.color_green_light));
            mTvPriceRisePercent.setTextColor(ContextCompat.getColor(this, R.color.color_green_light));
        }
        risePercentBuilder.append(ArithUtil.div(bean.getChangePct(), "0.01", 2)).append("%");

        mTvPriceRisePercent.setText(risePercentBuilder.toString());
        mTvVolValue.setText(bean.getVolume());
        mTvPriceMaxValue.setText(bean.getHighestPrice());
        mTvPriceMinValue.setText(bean.getLowestPrice());
    }

    public String getCoin(){
        if(mMarketName!=null && mMarketName.contains("/")){
            return mMarketName.substring(0,mMarketName.indexOf("/"));
        }
        return "";
    }

    public String getUnit(){
        if(mMarketName!=null && mMarketName.contains("/")){
            return mMarketName.substring(mMarketName.indexOf("/")+1);
        }
        return "";
    }


    @Override
    public void showOverview(OverviewBean bean) {
        updateTopUI(bean);
    }

    @Override
    protected boolean showTitle() {
        return false;
    }
}
