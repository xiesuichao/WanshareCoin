package com.wanshare.common.listdemo.adapter;

import android.content.Context;
import android.view.View;

import com.wanshare.common.R;
import com.wanshare.common.adapter.CommonAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.listdemo.mode.UserBean;
import com.wanshare.wscomponent.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by Jason on 2018/7/30.
 */

public class NormalAdapter extends CommonAdapter<UserBean> {

    public NormalAdapter(Context context) {
        super(context, R.layout.item_rv, new ArrayList<UserBean>());
    }

    @Override
    protected void convert(ViewHolder holder, UserBean userBean, int position) {
        final String userName = userBean.getName();
        String userAge = userBean.getAge();
        holder.setText(R.id.user_name, userName);
        holder.setText(R.id.user_age, userAge);
        holder.setImageResource(R.id.image,R.drawable.ic_home_notice);

//        holder.setTextColor() 设置字体颜色
//        holder.setAlpha() 设置透明度
//        holder.setBackgroundColor() 设置背景色
//        holder.setEnabled()
//        ...



        //设置某个控件点击事件
        holder.setOnClickListener(R.id.user_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(mContext,userName);
            }
        });


    }
}
