package com.wanshare.common.adapter;

import java.util.List;

/**
 * Created by Jason on 2018/8/29.
 */

public class EmptyViewAdapter extends EmptyWrapper {
    private MultiItemTypeAdapter mMultiItemTypeAdapter;

    public EmptyViewAdapter(MultiItemTypeAdapter adapter) {
        super(adapter);
        mMultiItemTypeAdapter = adapter;
    }

    public void updateList(List list) {
        mMultiItemTypeAdapter.updateList(list);
        notifyDataSetChanged();
    }

    public void addList(List list) {
        mMultiItemTypeAdapter.addList(list);
    }

    public void refresh(){
        mMultiItemTypeAdapter.notifyDataSetChanged();
        notifyDataSetChanged();
    }
}
