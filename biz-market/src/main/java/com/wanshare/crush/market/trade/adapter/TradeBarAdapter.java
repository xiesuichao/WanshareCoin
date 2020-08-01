package com.wanshare.crush.market.trade.adapter;

import android.content.Context;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.widget.BarChartView;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.trade.model.bean.CoinPair;

import java.util.ArrayList;
import java.util.List;

public class TradeBarAdapter extends CommonAdapter<CoinPair> {

    private boolean isSell;

    public TradeBarAdapter(Context context) {
        super(context, R.layout.market_item_trade_bar_chart, new ArrayList<CoinPair>());
    }

    @Override
    protected void convert(ViewHolder holder, CoinPair coinPair, int position) {


        BarChartView chartView = holder.getView(R.id.bcv_trade_bar_chart);
        chartView.setData(coinPair.getAmount());
        chartView.changeBackgroundCol(isSell);
        holder.setTextColor(R.id.tv_item_trade_bar_chat_price,
                mContext.getResources().getColor(isSell ? R.color.color_red_light : R.color.color_green_dark));
        holder.setText(R.id.tv_item_trade_bar_chat_price, coinPair.getPrice());
        holder.setText(R.id.tv_item_trade_bar_chat_amount, coinPair.getAmount());

    }

    public void setSell(boolean isSell) {
        this.isSell = isSell;
    }
}
