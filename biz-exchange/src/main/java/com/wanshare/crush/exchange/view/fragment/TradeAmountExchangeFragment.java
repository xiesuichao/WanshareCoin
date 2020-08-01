package com.wanshare.crush.exchange.view.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.biz.exchange.ExchangeArouterConstant;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.R2;
import com.wanshare.crush.exchange.adapter.TopExchangeAdapter;
import com.wanshare.crush.exchange.contract.TradeAmountExchangeContract;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.crush.exchange.model.bean.TopExchange;
import com.wanshare.crush.exchange.presenter.TradeAmountExchangePresenter;
import com.wanshare.crush.exchange.view.ExchangeActivity;

import java.util.ArrayList;

import butterknife.BindView;

import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE;

/**
 * @author wangdunwei
 * @date 2018/8/25
 */
public class TradeAmountExchangeFragment extends BaseListFragment<TradeAmountExchangeContract.Presenter>
        implements TradeAmountExchangeContract.View {
    public static final int PAGE_SIZE = 10;
    @BindView(R2.id.cb_values_per_day)
    CheckBox cbValuesPerDay;
    private TopExchangeAdapter topExchangeAdapter;

    @Override
    protected int getContentView() {
        return R.layout.exchange_trade_amount_exchange_fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        topExchangeAdapter = new TopExchangeAdapter(mActivity);
        setAdapter(topExchangeAdapter);

        topExchangeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position >= 0 && position <= topExchangeAdapter.getItemCount()) {
                    ARouter.getInstance()
                           .build(ExchangeArouterConstant.EXCHANGE)
                           .withParcelable(EXTRA_EXCHANGE, topExchangeAdapter.getItem(position))
                           .withBoolean(ExchangeActivity.EXTRA_IS_COLLECTED, true)
                           .navigation(getContext());
                }
            }
        });

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestExchangeByTradeAmount(++currentPage, PAGE_SIZE);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestExchangeByTradeAmount(1, PAGE_SIZE);
                if(!mSmartRefreshLayout.isEnableLoadMore()) {
                    mSmartRefreshLayout.setNoMoreData(false);
                    mSmartRefreshLayout.setEnableLoadMore(true);
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new TradeAmountExchangePresenter(this);
        mPresenter.requestExchangeByTradeAmount(currentPage, PAGE_SIZE);
    }

    @Override
    public void showExchangeList(TopExchange topExchange) {
        onFinishLoad();
        onFinishRefresh();

        TopExchange.Meta meta = topExchange.getMeta();
        if(meta != null) {
            currentPage = meta.getPage();
        }
        if(topExchange.getExchange() == null) {
            topExchange.setExchange(new ArrayList<Exchange>(0));
        }

        int pageSize = meta == null ? PAGE_SIZE : meta.getItemsPerPage();
        if(currentPage == 1) {
            updateData(topExchange.getExchange(), pageSize);
        } else {
            addData(topExchange.getExchange(), pageSize);
        }
    }
}
