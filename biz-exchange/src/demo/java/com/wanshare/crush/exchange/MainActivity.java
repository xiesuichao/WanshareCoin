package com.wanshare.crush.exchange;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.crush.exchange.model.bean.Market;
import com.wanshare.crush.exchange.view.ExchangeActivity;
import com.wanshare.crush.exchange.view.dialog.MarketDialog;
import com.wanshare.wscomponent.logger.LogUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.logging.HttpLoggingInterceptor;
import wanshare.wscomponent.http.ApiClient;

import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE;


public class MainActivity extends BaseActivity{

    private MarketDialog marketDialog;
    private List<List<Market>> marketList;
    private List<String> titleList;
    private Button btnMarket;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    Exchange exchange = new Exchange("1011", ".jpg", "天空交易所", "11.121",
            30, "天空交易所是个交易所",
            "123.33", "别名", "http://背景图url.jpg");

    @Override
    protected void initData() {
        initMarketDialogData();
    }

    @Override
    protected void initView() {
        btnMarket = findViewById(R.id.btnMarket);
        init();
    }

    private void init() {
        ARouter.init(getApplication());
        LogUtil.init();
        ApiClient.init("http://www.wanshare.com/", new HttpLoggingInterceptor());
//        ApiClient.init("http://result.eolinker.com/", new HttpLoggingInterceptor());
        ApiClient.getInstance().initHttpClient();
    }

    public void onExchange(View view) {

        ARouter.getInstance()
               .build(ExchangeArouterConstant.EXCHANGE)
               .withParcelable(EXTRA_EXCHANGE, exchange)
               .withBoolean(ExchangeActivity.EXTRA_IS_COLLECTED, true)
               .navigation(this);
    }

    public void onExchangeFragment(View view) {
        startActivity(new Intent(this, ExchangeFragmnetActivity.class));
    }

    public void onMarketDialog(View view) {
        initMarketDialogData();
//        marketDialog = new MarketDialog(this, getSupportFragmentManager());
//        marketDialog.showBelow(btnMarket);

        MarketDialog.newInstance(exchange, btnMarket).show(getSupportFragmentManager(), MarketDialog.class.getSimpleName());

//        marketDialog.updateData(titleList, marketList);
    }

    private void initMarketDialogData() {
        titleList = new ArrayList<>();
        titleList.add("市场1");
        titleList.add("市场2");

        marketList = new ArrayList<>();
        List<Market> list = new ArrayList<>();
        Market market = new Market("1", "市场1", "1", "etc", "bth", "1.1", "1.1", "1.1", "1.1", "1.1", "1.1");
        for(int i = 0; i < 10; i++) {
            list.add(market);
        }
        marketList.add(list);

        list = new ArrayList<>();
        list.add(market);
        list.add(market);

        market = new Market("2", "市场2", "2", "ccc", "bbb", "2.2", "2.2", "2.2", "2.2", "2.2", "2.2");
        for(int i = 0; i < 10; i++) {
            list.add(market);
        }
        marketList.add(list);
    }

    public void onMarketDialog2(View view) {
//        new MarketDialog().show(getSupportFragmentManager(), MarketDialog.class.getSimpleName());
        MarketDialog.newInstance(exchange, btnMarket).show(getSupportFragmentManager(), MarketDialog.class.getSimpleName());
//        marketDialog = new MarketDialog(this, getSupportFragmentManager(), titleList, marketList);
//        marketDialog.showBelow(btnMarket);

//        marketDialog.updateData(titleList, marketList);
    }

    public void onUpdateMarket(View view) {
//        new MarketDialog().show(getSupportFragmentManager(), MarketDialog.class.getSimpleName());
        MarketDialog.newInstance(exchange, btnMarket).show(getSupportFragmentManager(), MarketDialog.class.getSimpleName());
//        marketDialog = new MarketDialog(this, getSupportFragmentManager());
//        marketDialog.showBelow(btnMarket);
//        marketDialog.updateData(titleList, marketList);

        final List<Market> marketList = new ArrayList<>();
        Market market = new Market("1", "市场1变了", "1", "etc变了", "btn变了", "11111.11", "11111.1", "11111.1", "11111.1", "-11111.1", "-11111.1");
        marketList.add(market);

//        btnMarket.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                marketDialog.updateMarket(marketList);
//            }
//        }, 3000);
    }
}
