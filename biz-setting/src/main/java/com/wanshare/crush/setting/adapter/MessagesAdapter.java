package com.wanshare.crush.setting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.setting.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/9/25.
 * <p>
 * 消息列表的Adapter
 */

public class MessagesAdapter extends CommonAdapter {
    public MessagesAdapter(Context context) {
        super(context, R.layout.setting_item_messages, new ArrayList());
    }

    @Override
    protected void convert(ViewHolder holder, Object o, final int position) {


        holder.setOnClickListener(R.id.cv_messages_container, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMessagesListener != null) {
                    mOnMessagesListener.onMessagesClick(v, position);
                }
            }
        });
    }

    public interface OnMessagesListener {
        void onMessagesClick(View view, int position);
    }

    private OnMessagesListener mOnMessagesListener;

    public void setOnMessagesListener(OnMessagesListener onMessagesListener) {
        this.mOnMessagesListener = onMessagesListener;
    }
}
