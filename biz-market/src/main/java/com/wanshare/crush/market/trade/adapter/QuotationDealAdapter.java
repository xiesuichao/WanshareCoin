package com.wanshare.crush.market.trade.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.wanshare.crush.market.R;
import com.wanshare.crush.market.order.model.bean.HistoryOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成交
 */

public class QuotationDealAdapter extends BaseAdapter {

    private Context context;
    private List<HistoryOrder.ItemsBean> mHistoryModels;
    private int pricePrecision = 0;
    private int volumePrecision = 0;
    public QuotationDealAdapter(Context context){
        this.context = context;
    }

    public void upData(List<HistoryOrder.ItemsBean> historyModels){
        mHistoryModels = historyModels;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mHistoryModels == null) return 0;
        return mHistoryModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mHistoryModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder iHolder;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.market_item_deal_detail_bargain, null);
            iHolder = new ItemViewHolder();
            iHolder.timeTv = convertView.findViewById(R.id.tv_deal_detail_bargain_time);
            iHolder.priceTv = convertView.findViewById(R.id.tv_deal_detail_bargain_price);
            iHolder.countTv = convertView.findViewById(R.id.tv_deal_detail_bargain_count);
            convertView.setTag(iHolder);
        }else {
            iHolder = (ItemViewHolder)convertView.getTag();
        }

//        if (mHistoryModels.get(position).getmBuyOrSell().equals("bid")){
            iHolder.priceTv.setTextColor(context.getResources().getColor(R.color.color_green_light));
//        }

        // TODO TASK: 2018/9/5 判断买卖类型
//        if (mHistoryModels.get(position).getmBuyOrSell().equals("ask")){
//            iHolder.priceTv.setTextColor(context.getResources().getColor(R.color.color_red_light));
//        }

        iHolder.priceTv.setText(formatPrice(mHistoryModels.get(position).getPrice()));
        iHolder.countTv.setText(formatVol(mHistoryModels.get(position).getTradeAmount()));
        iHolder.timeTv.setText(mHistoryModels.get(position).getCreatedAt());

        return convertView;
    }

    private String formatPrice(String price){
        if(price==null){
            return "";
        }
        return new BigDecimal(price).setScale(pricePrecision, BigDecimal.ROUND_DOWN).toPlainString();
    }

    private String formatVol(String vol){
        if(vol==null){
            return "";
        }
        return new BigDecimal(vol).setScale(volumePrecision, BigDecimal.ROUND_DOWN).toPlainString();
    }

    public void setPrecision(int pricePrecision, int volumePrecision) {
        this.pricePrecision = pricePrecision;
        this.volumePrecision = volumePrecision;
    }

    private static class ItemViewHolder{
        TextView timeTv;
        TextView priceTv;
        TextView countTv;
    }

}
