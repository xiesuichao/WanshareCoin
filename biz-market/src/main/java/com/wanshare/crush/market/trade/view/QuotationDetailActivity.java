package com.wanshare.crush.market.trade.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.common.widget.viewpager.CommonFragmentPagerAdapter;
import com.wanshare.common.widget.viewpager.ViewPagerIndicator;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.trade.adapter.KlineFragmentViewPagerAdapter;
import com.wanshare.crush.market.trade.contract.QuotationDetailContract;
import com.wanshare.crush.market.trade.event.KlineTabChangeEvent;
import com.wanshare.crush.market.trade.model.KlineTabSelectedManager;
import com.wanshare.crush.market.trade.model.bean.OverviewBean;
import com.wanshare.crush.market.trade.presenter.QuotationDetailPresenter;
import com.wanshare.wscomponent.chart.kline.KLineTabLayout;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.ArithUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 市场行情k线页面
 * </br>
 * Date: 2018/9/3 17:45
 *
 * @author hemin
 */
@Route(path = MarketArouterConstant.MARKET_TRADE_QUOTATION_DETAIL)
public class QuotationDetailActivity extends BaseActivity<QuotationDetailContract.Presenter>
        implements QuotationDetailContract.View {

    @BindView(R2.id.tv_sell)
    TextView mTvSell;
    @BindView(R2.id.tv_buy)
    TextView mTvBuy;
    @BindView(R2.id.iv_deal_detail_collection)
    ImageView mIvDealDetailCollection;
    @BindView(R2.id.iv_deal_detail_right)
    ImageView mIvDealDetailRight;

    @BindView(R2.id.tv_price)
    TextView mTvPrice;
    @BindView(R2.id.tv_cny_price)
    TextView mTvCnyPrice;
    @BindView(R2.id.tv_price_rise)
    TextView mTvPriceRise;
    @BindView(R2.id.tv_vol_label)
    TextView mTvVolLabel;
    @BindView(R2.id.tv_price_rise_percent)
    TextView mTvPriceRisePercent;
    @BindView(R2.id.tv_vol_value)
    TextView mTvVolValue;
    @BindView(R2.id.tv_vol_unit)
    TextView mTvVolUnit;
    @BindView(R2.id.tv_price_max_value)
    TextView mTvPriceMaxValue;
    @BindView(R2.id.tv_price_min_value)
    TextView mTvPriceMinValue;

    @BindView(R2.id.tabLayoutKline)
    KLineTabLayout mTabLayoutKline;
    @BindView(R2.id.vp_kline)
    ViewPager mViewPager;
    private KlineFragmentViewPagerAdapter mViewPagerAdapter;

    @BindView(R2.id.indicator_bottom)
    ViewPagerIndicator mIndicatorBottom;
    @BindView(R2.id.vp_bottom)
    ViewPager mViewPagerBottom;
    private CommonFragmentPagerAdapter mViewPagerAdapterBottom;

    String symbol = "1:ETH/BTC";

    @Override
    protected QuotationDetailContract.Presenter getPresenter() {
        return new QuotationDetailPresenter(this);
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d("leon= QuotationDetailActivity onCreate");
    }

    @Override
    protected int getContentView() {
        return R.layout.market_activity_quotation_detail;
    }

    @Override
    protected void initView() {
        String sellCurrency = "BTC";
        String buyCurrency = "USDT";
        String pair = "BTC/USDT";

        mTvSell.setText(sellCurrency);
        mTvBuy.setText("/".concat(buyCurrency));

        initKlinePart(pair);
    }

    @Override
    protected void initData() {
        mPresenter.getOverView(symbol);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("TradeDetailActivity onResume");
        EventBus.getDefault().post(new KlineTabChangeEvent(KlineTabSelectedManager.TYPE_MAIN_QUOTA));
        EventBus.getDefault().post(new KlineTabChangeEvent(KlineTabSelectedManager.TYPE_SECOND_QUOTA));
        updatePeriod();
    }

    private void initKlinePart(String pair) {
        List<KLineTabLayout.DataItem> periodItems = new ArrayList<>();

        String[] periodTabs = getResources().getStringArray(R.array.market_condition_period_tabs);
        for (int i = 0; i < periodTabs.length; i++) {
            String[] split = periodTabs[i].split("\\|");
            String label = split[0];
            String period = split[1];
            periodItems.add(new KLineTabLayout.DataItem(label, period));
        }

        mViewPagerAdapter = new KlineFragmentViewPagerAdapter(getSupportFragmentManager(),periodItems,pair,50);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        mTabLayoutKline.initTabs(periodItems);
        mTabLayoutKline.setOnTabSelectListener(new KLineTabLayout.onTabSelectListener() {
            @Override
            public void onPeriodSelected(String label, String period, int position) {
                KlineTabSelectedManager.savePeriod(position);
                mViewPager.setCurrentItem(position, false);
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
        mTabLayoutKline.setSelectedQuota(KlineTabSelectedManager.getMainQuota(), KlineTabSelectedManager.getSecondQuota());

        updatePeriod();

        List<Fragment> fragmentsBottom = new ArrayList<>();
        fragmentsBottom.add(QuotationDepthFragment.newInstance(pair));
        fragmentsBottom.add(QuotationEntrustFragment.newInstance(pair));
        fragmentsBottom.add(QuotationDealFragment.newInstance(pair));

        mViewPagerAdapterBottom = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragmentsBottom);
        mViewPagerBottom.setAdapter(mViewPagerAdapterBottom);
        String[] titles = getResources().getStringArray(R.array.market_condition_bottom_tabs);
        mIndicatorBottom.setTitles(titles, mViewPagerBottom);
    }

    @OnClick({R2.id.iv_deal_detail_back, R2.id.iv_deal_detail_right, R2.id.iv_deal_detail_collection
            , R2.id.btn_deal_detail_buy, R2.id.btn_deal_detail_sell})
    public void onViewClicked(View v) {
        int id = v.getId();
        if (id == R.id.iv_deal_detail_back) {
            finish();
        } else if (id == R.id.iv_deal_detail_collection) {
            if (fastClick()) {
                return;
            }

            // TODO TASK: 2018/9/6 处理收藏
        } else if (id == R.id.iv_deal_detail_right) {
            ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE_KLINE_LANDSCAPE).navigation(this);
        } else if (id == R.id.btn_deal_detail_buy) {
            LogUtil.d("买");
            // TODO TASK: 2018/9/6 处理买
            if(!UserInfoManager.getInstance().isLogin()){
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(getContext());
                return;
            }
            ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE).withInt("type", 0).navigation(this);
        } else if (id == R.id.btn_deal_detail_sell) {
            LogUtil.d("卖");
            // TODO TASK: 2018/9/6 处理卖
            if(!UserInfoManager.getInstance().isLogin()){
                ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(getContext());
                return;
            }
            ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE).withInt("type", 1).navigation(this);
        }
    }

    private void updatePeriod() {
        if (mViewPager == null) {
            return;
        }
        int position = KlineTabSelectedManager.getPeriod();
        mViewPager.setCurrentItem(position);
        mTabLayoutKline.setSelectedPeriod(position);
    }

    private void updateTopUI(OverviewBean bean) {
        if (mTvPrice == null || bean == null) {
            return;
        }

        boolean isRise = !bean.getChangePct().contains("-");
        mTvPrice.setText(bean.getLatestPrice());
        mTvCnyPrice.setText("￥" + bean.getCnyLatestPrice());
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
        mTvVolUnit.setText(bean.getBuyerCoin());
        mTvPriceMaxValue.setText(bean.getHighestPrice());
        mTvPriceMinValue.setText(bean.getLowestPrice());
    }

    @Override
    public void showOverview(OverviewBean bean) {
        updateTopUI(bean);
    }
}
