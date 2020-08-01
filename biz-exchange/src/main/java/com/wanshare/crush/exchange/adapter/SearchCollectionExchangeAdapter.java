package com.wanshare.crush.exchange.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.exchange.R;
import com.wanshare.crush.exchange.model.bean.Exchange;
import com.wanshare.wscomponent.image.ImageLoader;

import java.util.ArrayList;

/**
 * @author wangdunwei
 * @date 2018/8/31
 */
public class SearchCollectionExchangeAdapter extends CommonAdapter<Exchange> {
    public SearchCollectionExchangeAdapter(Context context) {
        super(context, R.layout.exchange_search_collection_item, new ArrayList<Exchange>(0));
    }

    @Override
    protected void convert(ViewHolder holder, Exchange exchange, int position) {
        holder.setText(R.id.tv_name, exchange.getName());

        ImageLoader.with(getContext())
             .load(exchange.getLogoUrl())
             .into((ImageView) holder.getView(R.id.iv));

        //todo 收藏接口完成后添加状态
        holder.setSelected(R.id.iv_collect, true);
    }
}
