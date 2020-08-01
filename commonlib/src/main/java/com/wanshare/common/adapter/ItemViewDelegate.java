package com.wanshare.common.adapter;


/**
 * Created by Venn on 2018/3/14.
 */
public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
