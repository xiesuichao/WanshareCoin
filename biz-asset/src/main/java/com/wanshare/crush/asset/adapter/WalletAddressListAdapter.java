package com.wanshare.crush.asset.adapter;

import android.content.Context;
import android.view.View;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.model.bean.Coin;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/29.
 */

public class WalletAddressListAdapter extends CommonAdapter<Coin>
{
    public WalletAddressListAdapter(Context context)
    {
        super(context, R.layout.asset_item_asset, new ArrayList<Coin>(0));
    }

    @Override
    protected void convert(ViewHolder holder, final Coin coin, final int position) {
        holder.setText(R.id.tv_title, coin.getRemark());
        holder.setText(R.id.tv_address, coin.getAddress());
//        holder.setText(R.id.tv_coin, walletModel.getTitle());
    }

    @Override
    public void onViewHolderCreated(ViewHolder holder, View itemView) {
        super.onViewHolderCreated(holder, itemView);
//        holder.getView(R.id.content).getLayoutParams().width = ScreenUtils.getScreenWidth(mContext);
    }
}

