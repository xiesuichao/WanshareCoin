package com.wanshare.crush.market.order.adapter;

import android.content.Context;
import android.view.View;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;

import java.util.ArrayList;

/**
 * Created by yangwenwu on 2018/8/22.
 */

public class RecycleCurrentAdapter extends CommonAdapter<EntrustsOrder.ItemsBean> {

    public RecycleCurrentAdapter(Context context) {
        super(context, R.layout.market_item_current_order, new ArrayList<EntrustsOrder.ItemsBean>());
    }


    @Override
    protected void convert(ViewHolder holder, final EntrustsOrder.ItemsBean itemsBean, int position) {

        holder.setText(R.id.tv__market_item_order_left_coin,itemsBean.getTradingPair().substring(0,itemsBean.getTradingPair().indexOf("/")));
        holder.setText(R.id.tv_market_item_order_right_coin,itemsBean.getTradingPair().substring(itemsBean.getTradingPair().indexOf("/"),itemsBean.getTradingPair().length()));
        holder.setText(R.id.tv_market_item_order_exchange,itemsBean.getExchange());
        holder.setText(R.id.tv_market_item_current_order_time,itemsBean.getCreatedAt());
        holder.setText(R.id.tv_market_item_current_order_current_price,itemsBean.getPrice());
        holder.setText(R.id.tv_market_item_current_order_valume,itemsBean.getEntrustVolume());
        holder.setText(R.id.tv_market_item_current_order_valume_all,"/"+itemsBean.getVolume());

        holder.setOnClickListener(R.id.tv_market_item_order_trade_state, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCancelListener.onCancelClick(v,itemsBean.getId());
            }
        });
    }

    public interface OnCancelListener{
        void onCancelClick(View view,String orderId);
    }

    private OnCancelListener mOnCancelListener;

    public void setOnCancelListener(OnCancelListener onCancelListener){
        mOnCancelListener = onCancelListener;
    }
}
