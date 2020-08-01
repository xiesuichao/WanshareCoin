package com.wanshare.crush.project.view;

import android.content.Context;
import android.widget.ImageView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.model.bean.ProjectMarket;
import com.wanshare.wscomponent.image.ImageLoader;
import com.wanshare.wscomponent.logger.LogUtil;

import java.util.ArrayList;

/**
 * Created by xiesuichao on 2018/8/22.
 */

public class ProjectMarketRvAdapter extends CommonAdapter<ProjectMarket.ItemsBean> {

    private Context mContext;

    public ProjectMarketRvAdapter(Context context) {
        super(context, R.layout.project_item_fragment_online_exchange, new ArrayList<ProjectMarket.ItemsBean>());
        this.mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, ProjectMarket.ItemsBean market, int position) {
        ImageView iconIv = holder.getView(R.id.iv_project_market_logo);
//        ImageLoader.with(mContext).load(market.getLogo()).into(iconIv);

        holder.setText(R.id.tv_project_market_name, market.getName());
        if (market.getTradingPair().contains("/")){
            String leftCoin = market.getTradingPair().substring(0, market.getTradingPair().indexOf("/"));
            String rightCoin = market.getTradingPair().substring(market.getTradingPair().indexOf("/"), market.getTradingPair().length());
            holder.setText(R.id.tv_project_market_left_coin, leftCoin);
            holder.setText(R.id.tv_project_market_right_coin, rightCoin);
        }

        holder.setText(R.id.tv_project_market_coin_price, market.getLatestPrice());
        holder.setText(R.id.tv_project_market_trade_amount,
                mContext.getString(R.string.project_market_trade_volume) + market.getAmount24H());
        holder.setText(R.id.tv_project_market_change, market.getChgPct24H());

    }

}
