package com.wanshare.crush.exchange.view;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.common.biz.exchange.ExchangeCoins;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.contract.ExchangeContract;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.crush.exchange.presenter.ExchangePresenter;
import com.wanshare.crush.exchange.view.fragment.MarketFragment;
import com.wanshare.wscomponent.toolbar.MyToolbar;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.wanshare.common.constant.IntentConstant.EXTRA_BUYER_COIN_NAME;
import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE;
import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE_ID;

/**
 * 交易所页面
 *
 * @author wangdunwei
 */
@Route(path = ExchangeArouterConstant.EXCHANGE)
public class ExchangeActivity extends BaseActivity<ExchangeContract.Presenter> implements ExchangeContract.View {
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    @BindView(R2.id.toolbar)
    MyToolbar toolbar;
    @BindView(R2.id.iv_collection)
    ImageView ivCollection;
    @BindView(R2.id.iv_search)
    ImageView ivSearch;
    @BindView(R2.id.tl_exchange)
    TabLayout tlExchange;
    @BindView(R2.id.vp_exchange)
    ViewPager vpExchange;

    private Exchange exchange;

    private List<BaseFragment> marketFragmentList = new ArrayList<>(0);

    @Override
    protected ExchangeContract.Presenter getPresenter() {
        return new ExchangePresenter(this);
    }

    @Override
    protected void initData() {
        mPresenter.requestExchangeCoins(exchange.getId());
    }

    @Override
    protected void initView() {
        boolean isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);
        exchange = getIntent().getParcelableExtra(EXTRA_EXCHANGE);

        if(exchange == null) {
            ToastUtil.showShort(getContext(), R.string.exchange_cannot_get_exchange_info);
            return;
        }

        ivCollection.setSelected(isCollected);

        toolbar.setOnBackButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle(exchange.getName());

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                       .build(ExchangeArouterConstant.SEARCH_COIN)
                       .withString(EXTRA_EXCHANGE_ID, exchange.getId())
                       .withString(EXTRA_BUYER_COIN_NAME, "")
                       .navigation(getContext());
            }
        });

    }

    /**
     * 获得买方币种列表后初始化MarketFragment
     *
     * @param buyerCoinList 买方币种列表
     */
    private void initMarketFragment(final List<ExchangeCoins.BuyerCoinBean> buyerCoinList) {
        //测试数据
        if(buyerCoinList == null || buyerCoinList.isEmpty()) {
            buyerCoinList.add(new ExchangeCoins.BuyerCoinBean(1, "BTC"));
            buyerCoinList.add(new ExchangeCoins.BuyerCoinBean(2, "ETH"));
            buyerCoinList.add(new ExchangeCoins.BuyerCoinBean(3, "CCEC"));
            buyerCoinList.add(new ExchangeCoins.BuyerCoinBean(4, "WXG"));
            buyerCoinList.add(new ExchangeCoins.BuyerCoinBean(5, "USDT"));
        }

        for(int i = 0; i < buyerCoinList.size(); i++) {
            marketFragmentList
                    .add(MarketFragment.newInstance(exchange.getId(), String.valueOf(buyerCoinList.get(i).getId())));
        }

        vpExchange.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return marketFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return marketFragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return buyerCoinList.get(position).getName();
            }
        });
        tlExchange.setupWithViewPager(vpExchange);
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.exchange_activity;
    }

    @Override
    public void onGotMarketTitles(List<ExchangeCoins.BuyerCoinBean> strings) {
        initMarketFragment(strings);
    }
}
