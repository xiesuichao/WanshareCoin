package com.wanshare.crush.exchange.adapter;

import android.content.Context;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.model.bean.Market;

import java.util.ArrayList;

/**
 * @author wangdunwei
 * @date 2018/8/28
 */
public class CoinAdapter extends CommonAdapter<Market>{
    public CoinAdapter(Context context) {
        super(context, R.layout.exchange_coin_item, new ArrayList<Market>(0));
    }

    @Override
    protected void convert(ViewHolder holder, Market market, int position) {
        holder.setText(R.id.tv_buy_coin, market.getBuyerCoin());
        holder.setText(R.id.tv_sell_coin, market.getSellerCoin());

        holder.setImageResource(R.id.iv_logo, R.drawable.ic_and);
    }
}
