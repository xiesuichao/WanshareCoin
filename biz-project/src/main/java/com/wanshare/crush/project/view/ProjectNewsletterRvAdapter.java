package com.wanshare.crush.project.view;

import android.content.Context;

import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.crush.project.R;
import com.wanshare.crush.project.model.bean.Newsletter;

import java.util.ArrayList;

/**
 * Created by xiesuichao on 2018/8/22.
 */

public class ProjectNewsletterRvAdapter extends CommonAdapter<Newsletter> {


    public ProjectNewsletterRvAdapter(Context context) {
        super(context, R.layout.project_item_fragment_newsletter, new ArrayList<Newsletter>());
    }

    @Override
    protected void convert(ViewHolder holder, Newsletter newsletter, int position) {

    }


}
