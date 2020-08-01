package com.weshare.wanxiang.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.wscomponent.image.ImageLoader;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.StrUtil;
import com.weshare.wanxiang.R;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Jason on 2018/8/20.
 */

public class RecycleExchangeAdapter extends CommonAdapter<Exchange> {
    public RecycleExchangeAdapter(Context context) {
        super(context, R.layout.app_item_recycle_exchange, new ArrayList<Exchange>());
    }

    @Override
    protected void convert(ViewHolder holder, Exchange exchange, int position) {
        if (null == exchange){
            return;
        }
        holder.setText(R.id.tv_exchange_name, exchange.getName());
//        ImageLoader.with(mContext).load(exchange.getLogoUrl()).into((ImageView) holder.getView(R.id.iv_exchange_logo));
        holder.setText(R.id.tv_trade_market_num, String.valueOf(exchange.getTradingPairCount()) + "个");
        String amount = exchange.getUsdAmount();
        if (ArithUtil.bigVol(amount, "99999999")) {
            amount = ArithUtil.div(amount, "100000000", 2) + "亿";
        } else if (ArithUtil.bigVol(amount, "9999")) {
            amount = ArithUtil.div(amount, "10000", 2) + "万";
        }
        holder.setText(R.id.tv_exchange_amout_num, "$" + amount);

        String tags = exchange.getTags();
        String[] tag = new String[3];
        if (null != tags && tags.contains(",")) {
            tag = tags.split(",");
        } else if (null != tags) {
            if (StrUtil.getStringLength(tags) > 10) {
                tags = tags.substring(0, 5) + "...";
            }
            holder.setText(R.id.tv_exchange_tag1, tags);
            holder.setVisible(R.id.tv_exchange_tag1, View.VISIBLE);
        }
        if (tag.length == 2) {
            if (StrUtil.getStringLength(tag[0]) > 10) {
                tag[0] = tag[0].substring(0, 5) + "...";
            }
            if (StrUtil.getStringLength(tag[1]) > 10) {
                tag[1] = tag[1].substring(0, 5) + "...";
            }
            holder.setText(R.id.tv_exchange_tag1, tag[0]);
            holder.setText(R.id.tv_exchange_tag2, tag[1]);
            holder.setVisible(R.id.tv_exchange_tag1, View.VISIBLE);
            holder.setVisible(R.id.tv_exchange_tag2, View.VISIBLE);
        } else {
            if (StrUtil.getStringLength(tag[0]) > 10) {
                tag[0] = tag[0].substring(0, 5) + "...";
            }
            if (StrUtil.getStringLength(tag[1]) > 10) {
                tag[1] = tag[1].substring(0, 5) + "...";
            }
            if (StrUtil.getStringLength(tag[2]) > 10) {
                tag[2] = tag[2].substring(0, 5) + "...";
            }
            holder.setText(R.id.tv_exchange_tag1, tag[0]);
            holder.setText(R.id.tv_exchange_tag2, tag[1]);
            holder.setText(R.id.tv_exchange_tag3, tag[2]);
            holder.setVisible(R.id.tv_exchange_tag1, View.VISIBLE);
            holder.setVisible(R.id.tv_exchange_tag2, View.VISIBLE);
            holder.setVisible(R.id.tv_exchange_tag3, View.VISIBLE);
        }
    }
}
