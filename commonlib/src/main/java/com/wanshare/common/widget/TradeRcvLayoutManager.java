package com.wanshare.common.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 捕获IndexOutOfBoundsException异常
 * @author wangdunwei
 * @date 2018/6/4
 */
public class TradeRcvLayoutManager extends LinearLayoutManager
{
    public TradeRcvLayoutManager(Context context)
    {
        super(context);
    }

    public TradeRcvLayoutManager(Context context, int orientation, boolean reverseLayout)
    {
        super(context, orientation, reverseLayout);
    }

    public TradeRcvLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state)
    {
        try
        {
            super.onLayoutChildren(recycler, state);
        } catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }
    }
}
