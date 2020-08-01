package com.wanshare.crush.market.trade.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wanshare.common.widget.BarChartView;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.trade.model.bean.CoinPair;
import com.wanshare.crush.market.trade.model.bean.TradeDataModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by admin on 2018/5/2.
 */

public class TradeBarChartLvAdapter extends BaseAdapter {

    private Context context;
    private List<CoinPair> topDataList;
    private List<CoinPair> botDataList;
    private TradeDataModel tradeDataModel;
    private final int TYPE_ITEM = 0;
    private final int TYPE_CENTER = 1;
    private int mTopFirstPosition;
    private int pricePrecision = 0;
    private int volumePrecision = 0;
    private int mCount = 6;

    public TradeBarChartLvAdapter(Context context, List<CoinPair> topDataList, List<CoinPair> botDataList, TradeDataModel tradeDataModel) {
        this.context = context;
        this.topDataList = topDataList;
        this.botDataList = botDataList;
        this.tradeDataModel = tradeDataModel;
    }

    public void updateList() {
        mTopFirstPosition = 6 - topDataList.size();
        notifyDataSetChanged();
    }

    public void setPrecision(int pricePrecision, int volumePrecision){
        this.pricePrecision = pricePrecision;
        this.volumePrecision = volumePrecision;
    }

    @Override
    public int getCount() {
        return 13;
    }

    @Override
    public Object getItem(int position) {
        if (position < mCount && position < topDataList.size()) {
            return topDataList.get(position);
        } else if (position > mCount && position < botDataList.size() + 7) {
            return botDataList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mCount) {
            return TYPE_CENTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch (getItemViewType(position)) {
            case TYPE_ITEM:
                try {
                    ItemViewHolder iHolder;
                    if (convertView == null) {
                        convertView = View.inflate(context, R.layout.market_item_trade_bar_chart, null);
                        iHolder = new ItemViewHolder();
                        iHolder.barChartView = convertView.findViewById(R.id.bcv_trade_bar_chart);
                        iHolder.priceTv = convertView.findViewById(R.id.tv_item_trade_bar_chat_price);
                        iHolder.amountTv = convertView.findViewById(R.id.tv_item_trade_bar_chat_amount);
                        convertView.setTag(iHolder);
                    } else {
                        iHolder = (ItemViewHolder) convertView.getTag();
                    }

                    if (topDataList != null && topDataList.size() > 0 && position >= 0 && position < mCount) {
                        int dataPosition = position - mCount + topDataList.size();
                        if (position == mTopFirstPosition){
                            if (mTopFirstPosition < mCount && dataPosition < topDataList.size()){
                                iHolder.barChartView.setData(topDataList.get(dataPosition).getAmount());
                                iHolder.barChartView.changeBackgroundCol(true);
                                iHolder.priceTv.setTextColor(context.getResources().getColor(R.color.color_red_light));
                                iHolder.priceTv.setText(topDataList.get(dataPosition).getPrice());
                                iHolder.amountTv.setText(topDataList.get(dataPosition).getAmount());
                                mTopFirstPosition++;
                            }else {
                                mTopFirstPosition = mCount - topDataList.size();
                            }
                        }else {
                            mTopFirstPosition = mCount - topDataList.size();
                            iHolder.barChartView.setData("0");
                            iHolder.priceTv.setText("");
                            iHolder.amountTv.setText("");
                        }

                    } else if (botDataList != null && botDataList.size() > 0 && position > mCount && position < botDataList.size() + 7) {
                        iHolder.barChartView.setData(botDataList.get(position - 7).getAmount());
                        iHolder.barChartView.changeBackgroundCol(false);
                        iHolder.priceTv.setTextColor(context.getResources().getColor(R.color.color_green_dark));
                        iHolder.priceTv.setText(botDataList.get(position - 7).getPrice());
                        iHolder.amountTv.setText(botDataList.get(position - 7).getAmount());

                    } else {
                        iHolder.barChartView.setData("0");
                        iHolder.priceTv.setText("");
                        iHolder.amountTv.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case TYPE_CENTER:
                CenterViewHolder cHolder;
                if (convertView == null) {
                    cHolder = new CenterViewHolder();
                    convertView = View.inflate(context, R.layout.market_item_trade_bar_chart_center, null);
                    cHolder.centerPriceTv = convertView.findViewById(R.id.tv_trade_bar_chart_center_price);
                    cHolder.centerCNYTv = convertView.findViewById(R.id.tv_trade_bar_chart_center_CNY);
                    cHolder.centerPercentTv = convertView.findViewById(R.id.tv_trade_bar_chart_center_percent);
                    convertView.setTag(cHolder);
                } else {
                    cHolder = (CenterViewHolder) convertView.getTag();
                }

                if (tradeDataModel != null && !TextUtils.isEmpty(tradeDataModel.getmLastPrice())) {
                    if (tradeDataModel.getmChange().contains("-")) {
                        cHolder.centerPriceTv.setTextColor(context.getResources().getColor(R.color.color_green_light));
                        cHolder.centerPercentTv.setTextColor(context.getResources().getColor(R.color.color_green_light));
                        cHolder.centerPercentTv.setText("-" + tradeDataModel.getmPercent());
                    } else {
                        cHolder.centerPriceTv.setTextColor(context.getResources().getColor(R.color.color_red_light));
                        cHolder.centerPercentTv.setTextColor(context.getResources().getColor(R.color.color_red_light));
                        cHolder.centerPercentTv.setText(tradeDataModel.getmPercent());
                    }
                    cHolder.centerPriceTv.setText(new BigDecimal(tradeDataModel.getmLastPrice()).setScale(pricePrecision, BigDecimal.ROUND_DOWN).toString());
                    cHolder.centerCNYTv.setText(tradeDataModel.getmPriceToCNY());
                } else {
                    cHolder.centerPriceTv.setText("");
                    cHolder.centerPercentTv.setText("");
                    cHolder.centerCNYTv.setText("");
                }
                break;
        }
        return convertView;
    }

    private static class ItemViewHolder {
        BarChartView barChartView;
        TextView priceTv;
        TextView amountTv;
    }

    private static class CenterViewHolder {
        TextView centerPriceTv;
        TextView centerCNYTv;
        TextView centerPercentTv;
    }

}
