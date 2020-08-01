package com.wanshare.common.listdemo.adapter;

import android.content.Context;

import com.wanshare.common.R;
import com.wanshare.common.adapter.ItemViewDelegate;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.adapter.ViewHolder;
import com.wanshare.common.listdemo.mode.UserBean;

import java.util.ArrayList;

/**
 * Created by Jason on 2018/8/6.
 *
 * 多条目类型的adapter
 */

public class MultipleItemAdapter extends MultiItemTypeAdapter<UserBean> {

    private static final String ITEM_TYPE_1 = "张三";

    private static final String ITEM_TYPE_2 = "李四";

    private static final String ITEM_TYPE_3 = "王五";


    public MultipleItemAdapter(Context context) {
        super(context, new ArrayList<UserBean>());

        //添加条目类型
        addItemViewDelegate(new ItemType1());
        addItemViewDelegate(new ItemType2());
        addItemViewDelegate(new ItemType3());
    }

    /**
     * 列表条目类型1
     */
    public class ItemType1 implements ItemViewDelegate<UserBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type1;
        }

        @Override
        public boolean isForViewType(UserBean item, int position) {

            //可以用数据bean的字段来作为判断条件，也可以用position
//           return ITEM_TYPE_1.contains(item.getName());
            return position % 3 == 0;
        }

        @Override
        public void convert(ViewHolder holder, UserBean userBean, int position) {
            holder.setText(R.id.tv_user_name,userBean.getName());
        }
    }

    /**
     * 列表条目类型2
     */
    public class ItemType2 implements ItemViewDelegate<UserBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type2;
        }

        @Override
        public boolean isForViewType(UserBean item, int position) {
            return position % 3 == 1;
        }

        @Override
        public void convert(ViewHolder holder, UserBean userBean, int position) {
            holder.setText(R.id.tv_user_name,userBean.getName());
        }
    }

    /**
     * 列表条目类型3
     */
    public class ItemType3 implements ItemViewDelegate<UserBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_type3;
        }

        @Override
        public boolean isForViewType(UserBean item, int position) {
            return position % 3 == 2;
        }

        @Override
        public void convert(ViewHolder holder, UserBean userBean, int position) {
            holder.setText(R.id.tv_user_name,userBean.getName());
        }
    }
}
