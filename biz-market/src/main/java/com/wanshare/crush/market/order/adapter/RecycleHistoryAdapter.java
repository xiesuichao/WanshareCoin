package com.wanshare.crush.market.order.adapter;

import android.content.Context;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;

import java.util.ArrayList;

/**
 * Created by yangwenwu on 2018/8/21.
 */

public class RecycleHistoryAdapter extends CommonAdapter<EntrustsOrder.ItemsBean> {

    public RecycleHistoryAdapter(Context context) {
        super(context, R.layout.market_item_history_order, new ArrayList<EntrustsOrder.ItemsBean>());
    }


    @Override
    protected void convert(ViewHolder holder, EntrustsOrder.ItemsBean itemsBean, int position) {

        holder.setText(R.id.tv__market_item_order_left_coin,itemsBean.getTradingPair().substring(0,itemsBean.getTradingPair().indexOf("/")));
        holder.setText(R.id.tv_market_item_order_right_coin,itemsBean.getTradingPair().substring(itemsBean.getTradingPair().indexOf("/"),itemsBean.getTradingPair().length()));
        holder.setText(R.id.tv_market_item_order_exchange,itemsBean.getExchange());
        holder.setText(R.id.tv_market_item_history_order_time,itemsBean.getCreatedAt());
        holder.setText(R.id.tv_market_item_history_order_average_price,itemsBean.getPrice());
        holder.setText(R.id.tv_market_item_history_order_valume,itemsBean.getEntrustVolume());
        holder.setText(R.id.tv_market_item_history_order_valume_all,"/"+itemsBean.getVolume());

        if (itemsBean.getStatus().equals("done")){
            holder.setText(R.id.tv_market_item_order_trade_state,getContext().getResources().getString(R.string.market_order_history_done));
        } else if (itemsBean.getStatus().equals("cancelled")){
            holder.setText(R.id.tv_market_item_order_trade_state,getContext().getResources().getString(R.string.market_order_history_cancelled));
        } else{
            holder.setText(R.id.tv_market_item_order_trade_state,"");
        }
    }
}
