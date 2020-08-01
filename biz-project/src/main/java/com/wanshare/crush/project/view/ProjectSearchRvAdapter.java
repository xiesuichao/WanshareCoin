package com.wanshare.crush.project.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.project.R;

import java.util.ArrayList;

/**
 * Created by xiesuichao on 2018/8/23.
 */

public class ProjectSearchRvAdapter extends CommonAdapter {


    public ProjectSearchRvAdapter(Context context) {
        super(context, R.layout.project_item_activity_project_search, new ArrayList());
    }

    @Override
    protected void convert(ViewHolder holder, Object object, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
