package com.wanshare.crush.market.trade.view;

import android.os.Bundle;
import android.view.View;


import com.google.gson.Gson;
import com.wanshare.common.base.LazyFragment;
import com.wanshare.common.base.UpdateChildUIListener;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.trade.contract.QuotationKlineContract;
import com.wanshare.crush.market.trade.event.KlineTabChangeEvent;
import com.wanshare.crush.market.trade.model.KlineTabSelectedManager;
import com.wanshare.crush.market.trade.presenter.QuotationKlinePresenter;
import com.wanshare.wscomponent.chart.kline.Quota;
import com.wanshare.wscomponent.chart.kline.WSKlineView;
import com.wanshare.wscomponent.chart.kline.model.KLineQuotaModel;
import com.wanshare.wscomponent.chart.kline.model.bean.HisData;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.utils.ArithUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 行情页面k线
 * </br>
 * Date: 2018/8/15 11:04
 *
 * @author hemin
 */
public class QuotationKlineFragment extends LazyFragment<QuotationKlineContract.Presenter> implements QuotationKlineContract.View {

    public static final int TYPE_MAIN_QUOTA = 1000;
    public static final int TYPE_SECOND_QUOTA = 1001;

    @BindView(R2.id.klineView)
    WSKlineView mKLineView;
    @BindView(R2.id.view_kline_empty)
    View mViewKlineEmpty;

    private String pair;
    private String period;
    private int initCount;

    public static QuotationKlineFragment newInstance(String pair, String period, int initCount) {
        QuotationKlineFragment fragment = new QuotationKlineFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IntentConstant.EXTRA_PAIR, pair);
        bundle.putString(IntentConstant.EXTRA_PERIOD, period);
        bundle.putInt(IntentConstant.EXTRA_INIT_COUNT, initCount);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected QuotationKlineContract.Presenter getPresenter() {
        return new QuotationKlinePresenter(this);
    }

    @Override
    protected void initArguments(Bundle savedInstanceState) {
        super.initArguments(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.market_fragment_market_kline;
    }

    @Override
    protected void initView() {
        pair = getArguments().getString(IntentConstant.EXTRA_PAIR);
        period = getArguments().getString(IntentConstant.EXTRA_PERIOD);
        initCount = getArguments().getInt(IntentConstant.EXTRA_INIT_COUNT);
        if (period != null && (period.contains("d") || period.contains("w"))) {
            mKLineView.setDateFormat("yyyy-MM-dd");
        } else {
            mKLineView.setDateFormat("MM-dd HH:mm");
        }

        mKLineView.setCount(initCount, 140, 18);

        if (pair != null && pair.contains("/")) {
            String unit = pair.substring(pair.indexOf("/") + 1);
            LogUtil.d("unit:" + unit);
            mKLineView.setVolUnit(unit);
        }
    }


    @Override
    protected void initData() {
        // TODO TASK: 2018/9/6 根据周期计算 from to
        String from = "12";
        String to = "2121";
        String symbol = "1:ETH/BTC";
        mPresenter.getKlineHistory(symbol, period, from, to);
    }

    private void initKLine(final List<HisData> hisData) {
        if (hisData == null || hisData.isEmpty()) {
            return;
        }
        mViewKlineEmpty.setVisibility(View.GONE);
        mKLineView.setVisibility(View.VISIBLE);

        mKLineView.initData(hisData);

        handleMainQuota(KlineTabSelectedManager.getMainQuota());
        handleSecondQuota(KlineTabSelectedManager.getSecondQuota());
    }

    private void handleMainQuota(String quota) {
        if (mKLineView == null || quota == null) {
            return;
        }
        if (Quota.MA.equalsIgnoreCase(quota)) {
            mKLineView.showMA();
        } else if (Quota.EMA.equalsIgnoreCase(quota)) {
            mKLineView.showEMA();
        } else if (Quota.BOLL.equalsIgnoreCase(quota)) {
            mKLineView.showBoll();
        } else {
            mKLineView.hideMainPartLine();
        }
    }

    private void handleSecondQuota(String quota) {
        if (mKLineView == null || quota == null) {
            return;
        }
        if (Quota.MACD.equalsIgnoreCase(quota)) {
            mKLineView.showMacd();
        } else if (Quota.KDJ.equalsIgnoreCase(quota)) {
            mKLineView.showKdj();
        } else {
            mKLineView.hideChartBottomPart();
        }
    }



    @Override
    public void showKline(List<HisData> hisDatas) {
        initKLine(hisDatas);
    }

    @Override
    public void showLoading(String tips) {
        // 覆盖父类方法，不显示dialog的loading
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onKlineTabChangeEvent(KlineTabChangeEvent event){
        if(event!=null){
            if(event.getType() == KlineTabSelectedManager.TYPE_MAIN_QUOTA){
                handleMainQuota( KlineTabSelectedManager.getMainQuota());
            }else if(event.getType() == KlineTabSelectedManager.TYPE_SECOND_QUOTA){
                handleSecondQuota( KlineTabSelectedManager.getSecondQuota());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
