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
import com.wanshare.crush.exchange.contract.HotExchangeContract;
import com.wanshare.crush.exchange.model.bean.TestData;
import com.wanshare.crush.exchange.model.bean.TopExchange;
import com.wanshare.crush.exchange.presenter.HotExchangePresenter;
import com.wanshare.crush.exchange.view.ExchangeActivity;

import butterknife.BindView;

import static com.wanshare.common.constant.IntentConstant.EXTRA_EXCHANGE;

/**
 * @author wangdunwei
 * @date 2018/8/25
 */
public class HotExchangeFragment extends BaseListFragment<HotExchangeContract.Presenter>
        implements HotExchangeContract.View {
    public static final int PAGE_SIZE = 10;
    @BindView(R2.id.cb_attention_amount)
    CheckBox cbAttentionAmount;
    private TopExchangeAdapter topExchangeAdapter;

    @Override
    protected int getContentView() {
        return R.layout.exchange_hot_fragment;
    }

    @Override
    protected void initView() {
        super.initView();
        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        topExchangeAdapter = new TopExchangeAdapter(getContext());
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
                currentPage = 1;
                mPresenter.requestExchangeByTradeAmount(currentPage, PAGE_SIZE);
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter = new HotExchangePresenter(this);
        mPresenter.requestExchangeByTradeAmount(currentPage, PAGE_SIZE);
    }

    @Override
    public void showExchangeList(TopExchange topExchange) {
        onFinishLoad();
        onFinishRefresh();

        if(topExchange.getExchange() == null) {
            topExchange.setExchange(TestData.ofExchangeList());
        }

        TopExchange.Meta meta = topExchange.getMeta();
        if(meta != null) {
            currentPage = meta.getPage();
        }
        int pageSize = meta == null ? PAGE_SIZE : meta.getItemsPerPage();
        if(currentPage == 1) {
            updateData(topExchange.getExchange(), pageSize);
        } else {
            addData(topExchange.getExchange(), pageSize);
        }
    }
}
