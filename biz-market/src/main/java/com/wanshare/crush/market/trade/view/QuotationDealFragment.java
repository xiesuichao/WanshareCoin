package com.wanshare.crush.market.trade.view;

import android.os.Bundle;
import android.widget.ListView;

import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.order.model.bean.HistoryOrder;
import com.wanshare.crush.market.trade.adapter.QuotationDealAdapter;
import com.wanshare.crush.market.trade.contract.QuotationDealContract;
import com.wanshare.crush.market.trade.presenter.QuotationDealPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * 成交
 * </br>
 * Date: 2018/9/5 11:36
 *
 * @author hemin
 */
public class QuotationDealFragment extends BaseFragment<QuotationDealContract.Presenter> implements QuotationDealContract.View {

    @BindView(R2.id.lv_deal_detail_bargain)
    ListView bargainLv;

    QuotationDealAdapter mDealDetailBargainLvAdapter;

    private String pair;

    public static QuotationDealFragment newInstance(String pair) {
        Bundle args = new Bundle();
        args.putString(IntentConstant.EXTRA_PAIR, pair);
        QuotationDealFragment fragment = new QuotationDealFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected QuotationDealContract.Presenter getPresenter() {
        return new QuotationDealPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pair = getArguments().getString(IntentConstant.EXTRA_PAIR);
    }

    @Override
    protected int getContentView() {
        return R.layout.market_fragment_deal;
    }

    @Override
    protected void initView() {
        mDealDetailBargainLvAdapter = new QuotationDealAdapter(getContext());
        bargainLv.setAdapter(mDealDetailBargainLvAdapter);
    }

    @Override
    protected void initData() {
        String symbol = "123092:BTC/ETH";
        mPresenter.getTradeHistory(symbol);
    }

    @Override
    public void showTradeHistory(List<HistoryOrder.ItemsBean> datas) {
        mDealDetailBargainLvAdapter.upData(datas);
    }
}
