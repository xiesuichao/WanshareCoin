package com.wanshare.crush.exchange.adapter;

import android.content.Context;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.model.bean.Market;
import com.wanshare.wscomponent.utils.ArithUtil;

import java.util.ArrayList;

/**
 * @author wangdunwei
 * @date 2018/8/27
 */
public class MarketAdapter extends CommonAdapter<Market>{
    public MarketAdapter(Context context) {
        super(context, R.layout.exchange_market_item, new ArrayList<Market>(0));
    }

    @Override
    protected void convert(ViewHolder holder, Market market, int position) {
        holder.setText(R.id.tv_buy_coin, market.getBuyerCoin());
        holder.setText(R.id.tv_sell_coin, market.getSellerCoin());
        holder.setText(R.id.tv_trade_volume, market.getVolume());
        holder.setText(R.id.tv_amount, market.getAmount() + " " + market.getBuyerCoin());
        holder.setText(R.id.tv_price, String.format("¥ %s",market.getLatestPrice()));
        holder.setText(R.id.btn_price_limit, market.getChangePctWithSymbol());

        boolean isUp = ArithUtil.isNatural(market.getChangeExtent());
        holder.setSelected(R.id.btn_price_limit, isUp);
        holder.setSelected(R.id.tv_trade_volume, isUp);

        holder.setImageResource(R.id.iv_logo, R.drawable.ic_and);
    }
}
