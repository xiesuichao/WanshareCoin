package com.wanshare.crush.exchange.adapter;

import android.content.Context;
import android.view.View;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.StrUtil;

import java.util.ArrayList;

/**
 * @author wangdunwei
 * @date 2018/8/29
 */
public class TopExchangeAdapter extends CommonAdapter<Exchange> {
    public TopExchangeAdapter(Context context) {
        super(context, R.layout.exchange_top_exchange_item, new ArrayList<Exchange>(0));
    }

    @Override
    protected void convert(ViewHolder holder, Exchange exchange, int position) {
        holder.setText(R.id.tv_exchange_name, exchange.getName());
        holder.setText(R.id.tv_trade_market_num, String.valueOf(exchange.getTradingPairCount()) + "个");

        String amount = exchange.getUsdAmount();
        if(ArithUtil.bigVol(amount, "99999999")) {
            amount = ArithUtil.div(amount, "100000000", 2) + "亿";
        } else if(ArithUtil.bigVol(amount, "9999")) {
            amount = ArithUtil.div(amount, "10000", 2) + "万";
        }
        holder.setText(R.id.tv_exchange_amout_num, "$" + amount);

        String tags = exchange.getTags();
        String[] tag;
        if(null != tags && tags.contains(",")) {
            tag = tags.split(",");
            if(tag.length == 2) {
                if(StrUtil.getStringLength(tag[0]) > 10) {
                    tag[0] = tag[0].substring(0, 5) + "...";
                }
                if(StrUtil.getStringLength(tag[1]) > 10) {
                    tag[1] = tag[1].substring(0, 5) + "...";
                }
                holder.setText(R.id.tv_exchange_tag1, tag[0]);
                holder.setText(R.id.tv_exchange_tag2, tag[1]);
                holder.setVisible(R.id.tv_exchange_tag1, View.VISIBLE);
                holder.setVisible(R.id.tv_exchange_tag2, View.VISIBLE);
            } else {
                if(StrUtil.getStringLength(tag[0]) > 10) {
                    tag[0] = tag[0].substring(0, 5) + "...";
                }
                if(StrUtil.getStringLength(tag[1]) > 10) {
                    tag[1] = tag[1].substring(0, 5) + "...";
                }
                if(StrUtil.getStringLength(tag[2]) > 10) {
                    tag[2] = tag[2].substring(0, 5) + "...";
                }
                holder.setText(R.id.tv_exchange_tag1, tag[0]);
                holder.setText(R.id.tv_exchange_tag2, tag[1]);
                holder.setText(R.id.tv_exchange_tag3, tag[2]);
                holder.setVisible(R.id.tv_exchange_tag1, View.VISIBLE);
                holder.setVisible(R.id.tv_exchange_tag2, View.VISIBLE);
                holder.setVisible(R.id.tv_exchange_tag3, View.VISIBLE);
            }
        } else if(null != tags) {
            if(StrUtil.getStringLength(tags) > 10) {
                tags = tags.substring(0, 5) + "...";
            }
            holder.setText(R.id.tv_exchange_tag1, tags);
            holder.setVisible(R.id.tv_exchange_tag1, View.VISIBLE);
        }
    }
}
