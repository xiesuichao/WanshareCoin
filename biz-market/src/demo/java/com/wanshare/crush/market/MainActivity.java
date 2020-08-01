package com.wanshare.crush.market;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.common.constant.AppConfig;

import com.wanshare.crush.market.order.view.EntrustsOrderFragment;
import com.wanshare.wscomponent.logger.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {


    @BindView(R2.id.market_main)
    FrameLayout mMarketMain;
    @BindView(R2.id.ll_market_view)
    LinearLayout mLlMarketView;

    @Override
    protected void initData() {
        if (AppConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(getApplication());
        LogUtil.init();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    public void market(View view) {
        mMyToolbar.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.market_main, new EntrustsOrderFragment())
                .commit();
    }

    public void transaction(View view) {
        ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE).navigation(this);
    }

    public void onQuotationDetail(View view){
        LogUtil.d("onQuotation");
        ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE_QUOTATION_DETAIL).navigation(this);
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }
}
