package com.wanshare.crush.favorite.adapter;

import android.content.Context;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.favorite.R;

import java.util.ArrayList;

/**
 * Created by yangwenwu on 2018/8/29.
 */

public class ExchangeManagerAdapter extends CommonAdapter<String> {

    public ExchangeManagerAdapter(Context context){
        super(context, R.layout.favorite_item_exchange_manager,new ArrayList<String>());
    }

    @Override
    protected void convert(ViewHolder holder, String s, int position) {

    }
}
