package com.wanshare.crush.market.trade.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.wscomponent.dialog.CommonDialog;
import com.wanshare.wscomponent.utils.DateUtil;

import java.util.ArrayList;

public class TradeOrderAdapter extends CommonAdapter<EntrustsOrder.ItemsBean> {

    public interface OnCancelOrderItemListener {
        void onCancelOrder(EntrustsOrder.ItemsBean bean);
    }

    private OnCancelOrderItemListener mOnCancelOrderItemListener;

    public TradeOrderAdapter(Context context) {
        super(context, R.layout.market_item_trade, new ArrayList<EntrustsOrder.ItemsBean>());
    }

    public void setOnCancelOrderItemListener(OnCancelOrderItemListener onCancelOrderItemListenter) {
        mOnCancelOrderItemListener = onCancelOrderItemListenter;
    }

    @Override
    protected void convert(ViewHolder holder, final EntrustsOrder.ItemsBean entrust, int position) {
        holder.setText(R.id.tv_trade_item_time, TextUtils.isEmpty(entrust.getCreatedAt()) ? "":
                DateUtil.utc2cn(entrust.getCreatedAt()));
        holder.setText(R.id.tv_trade_item_price, TextUtils.isEmpty(entrust.getPrice()) ? "" : entrust.getPrice());
        holder.setText(R.id.tv_trade_item_deal_amount, TextUtils.isEmpty(entrust.getVolume()) ? "" : entrust.getVolume());
        holder.setText(R.id.tv_trade_item_entrust_amount, TextUtils.isEmpty(entrust.getEntrustVolume()) ? "" : entrust.getEntrustVolume());
        holder.setText(R.id.tv_trade_item_trade_type,
                getContext().getString("sell".equals(entrust.getTradeType()) ?
                        R.string.market_trade_limit_price_sell : R.string.market_trade_limit_price_buy));
        holder.setTextColor(R.id.tv_trade_item_trade_type,
                mContext.getResources().getColor("sell".equals(entrust.getTradeType()) ?
                        R.color.color_red_dark : R.color.color_green_dark));
        holder.setOnClickListener(R.id.btn_trade_item_order_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderCancelDialog(entrust);
            }
        });
    }

    private void showOrderCancelDialog(final EntrustsOrder.ItemsBean bean){
        new CommonDialog.Builder(getContext())
                .setMessage(getContext().getString(R.string.market_order_confirm_cancel))
                .setPositiveButton(new CommonDialog.OnPositiveClickListener() {
                    @Override
                    public void onClick(CommonDialog commonDialog) {
                        mOnCancelOrderItemListener.onCancelOrder(bean);
                    }
                })
                .create()
                .show();
    }
}
