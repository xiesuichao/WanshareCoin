package com.wanshare.crush.market.trade.view;

import android.os.Bundle;
import android.widget.ListView;

import com.wanshare.common.base.BaseFragment;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.trade.adapter.QuotationEntrustAdapter;
import com.wanshare.crush.market.trade.contract.QuotationDepthContract;
import com.wanshare.crush.market.trade.model.bean.DepthBean;
import com.wanshare.crush.market.trade.presenter.QuotationDepthPresenter;
import com.wanshare.wscomponent.utils.ArithUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 委盘
 * </br>
 * Date: 2018/9/5 11:06
 *
 * @author hemin
 */
public class QuotationEntrustFragment extends BaseFragment<QuotationDepthContract.Presenter> implements QuotationDepthContract.View {

    @BindView(R2.id.lv_deal_detail_discretion)
    ListView containerLv;

    QuotationEntrustAdapter mAdapter;
    private List<String> leftSalelist = new ArrayList<>();
    private List<String> leftPricelist = new ArrayList<>();
    private List<String> rightSalelist = new ArrayList<>();
    private List<String> rightPricelist = new ArrayList<>();

    private String pair;
    private TradeDialog mTradeBuyDialog;
    private TradeDialog mTradeSellDialog;

    public static QuotationEntrustFragment newInstance(String pair) {
        Bundle args = new Bundle();
        args.putString(IntentConstant.EXTRA_PAIR, pair);
        QuotationEntrustFragment fragment = new QuotationEntrustFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected QuotationDepthContract.Presenter getPresenter() {
        return new QuotationDepthPresenter(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pair = getArguments().getString(IntentConstant.EXTRA_PAIR);
    }

    @Override
    protected int getContentView() {
        return R.layout.market_fragment_entrust;
    }

    @Override
    protected void initView() {
        mAdapter = new QuotationEntrustAdapter(getContext(), leftSalelist,
                leftPricelist, rightSalelist, rightPricelist);
        containerLv.setAdapter(mAdapter);

        mAdapter.setOnLeftItemClickListener(new QuotationEntrustAdapter.onItemClickListener() {
            @Override
            public void onClick(String price, int position) {
                showBuyDialog(price);
            }
        });

        mAdapter.setOnRightItemClickListener(new QuotationEntrustAdapter.onItemClickListener() {
            @Override
            public void onClick(String price, int position) {
                showSellDialog(price);
            }
        });
    }

    private void showBuyDialog(String price) {
        if (mTradeBuyDialog == null) {
            mTradeBuyDialog = new TradeDialog(getContext(), 0, "1", price);
        }
        // TODO TASK: 2018/9/26 设置price
        mTradeBuyDialog.show();
    }

    private void showSellDialog(String price) {
        if (mTradeSellDialog == null) {
            mTradeSellDialog = new TradeDialog(getContext(), 1, "1", price);
        }
        // TODO TASK: 2018/9/26 设置price
        mTradeSellDialog.show();
    }

    @Override
    protected void initData() {
        String symbol = "123092:BTC/ETH";
        mPresenter.getDepthData(symbol);
    }

    @Override
    public void showDepth(List<DepthBean> buyBillList, List<DepthBean> sellBillList) {

        final int MAX_COMMISSION_NUMBER = 20;
        int askBillcount = sellBillList.size() > MAX_COMMISSION_NUMBER ? MAX_COMMISSION_NUMBER : sellBillList.size();
        for (int i = 0; i < askBillcount; i++) {
            rightSalelist.add(ArithUtil.round(sellBillList.get(i).getAmount().toPlainString(), 3));
            rightPricelist.add(ArithUtil.round(sellBillList.get(i).getPrice().toPlainString(), 6));
        }

        int buyBillCount = buyBillList.size() > MAX_COMMISSION_NUMBER ? MAX_COMMISSION_NUMBER : buyBillList.size();
        for (int i = 0; i < buyBillCount; i++) {
            leftSalelist.add(ArithUtil.round(buyBillList.get(i).getAmount().toPlainString(), 3));
            leftPricelist.add(ArithUtil.round(buyBillList.get(i).getPrice().toPlainString(), 6));
        }

        if (mAdapter != null) {
            mAdapter.updateData(leftSalelist, leftPricelist, rightSalelist, rightPricelist);
        }
    }

    /*public void updateData(List<String> leftSalelist,List<String> leftPricelist,List<String> rightSalelist,List<String> rightPricelist){
        this.leftSalelist = leftSalelist;
        this.leftPricelist = leftPricelist;
        this.rightSalelist = rightSalelist;
        this.rightPricelist = rightPricelist;

        if (mAdapter != null) {
            mAdapter.updateData(leftSalelist, leftPricelist, rightSalelist, rightPricelist);
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTradeBuyDialog != null && mTradeBuyDialog.isShowing()) {
            mTradeBuyDialog.dismiss();
        }

        if (mTradeSellDialog != null && mTradeSellDialog.isShowing()) {
            mTradeSellDialog.dismiss();
        }
    }
}
