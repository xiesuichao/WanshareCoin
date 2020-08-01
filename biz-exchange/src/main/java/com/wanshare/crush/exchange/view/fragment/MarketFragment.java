package com.wanshare.crush.exchange.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.adapter.MarketAdapter;
import com.wanshare.crush.exchange.contract.MarketContract;
import com.wanshare.crush.exchange.model.bean.Market;
import com.wanshare.crush.exchange.presenter.MarketPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.wanshare.common.constant.IntentConstant.EXTRA_BUYER_COIN_NAME;
import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE_ID;

/**
 * 市场Fragment
 *
 * @author wangdunwei
 * @date 2018/8/27
 */
public class MarketFragment extends BaseFragment<MarketContract.Presenter> implements MarketContract.View {

    @BindView(R2.id.rv_market)
    RecyclerView rvMarket;

    private String exchangeId;
    private String buyerCoinName;
    private MarketAdapter marketAdapter;

    @Override
    protected void initArguments(Bundle savedInstanceState) {
        super.initArguments(savedInstanceState);
        exchangeId = getArguments().getString(EXTRA_EXCHANGE_ID);
        buyerCoinName = getArguments().getString(EXTRA_BUYER_COIN_NAME);

        mPresenter = new MarketPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.exchange_market_fragment;
    }

    @Override
    protected void initView() {
        rvMarket.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMarket.addItemDecoration(new CustomRecyclerViewDivider(2,
                getResources().getColor(R.color.color_gray_light), 0));
        marketAdapter = new MarketAdapter(getContext());
        rvMarket.setAdapter(marketAdapter);
        marketAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position >= 0 && position < marketAdapter.getItemCount()) {
                    ARouter.getInstance().build(MarketArouterConstant.MARKET_TRADE_QUOTATION_DETAIL)
                           .navigation(mActivity);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.requestMarketList(exchangeId, buyerCoinName);
    }

    public static MarketFragment newInstance(String exchangeId, String groupId) {
        MarketFragment marketFragment = new MarketFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_EXCHANGE_ID, exchangeId);
        bundle.putString(EXTRA_BUYER_COIN_NAME, groupId);
        marketFragment.setArguments(bundle);
        return marketFragment;
    }

    @Override
    public void showExchangeList(List<Market> list) {
        if(list == null) {
            list = new ArrayList<>(0);
        }

        marketAdapter.updateList(list);
    }
}
