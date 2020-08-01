package com.wanshare.common.listdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanshare.common.R;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.listdemo.adapter.MultipleItemAdapter;
import com.wanshare.common.listdemo.mode.UserBean;

import java.util.ArrayList;

/**
 * Created by Jason on 2018/8/7.
 * <p>
 * 多种条目类型的列表展示demo
 */

public class MultipleItemListActivity extends BaseListActivity implements OnRefreshListener, OnLoadMoreListener {
    /**
     * 每页加载的数据量
     * 如果网络请求不需要分页加载，此参数为0
     */
    private int pageSize = 20;
    private ArrayList<UserBean> data = new ArrayList<>();
    private MultipleItemAdapter mMultipleItemAdapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MultipleItemListActivity.class));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_multiple_item_list;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected void initView() {
        //设置标题
        mMyToolbar.setTitle(getString(R.string.test_list_show));
        mMyToolbar.setRightButtonText(getString(R.string.finish));

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mMultipleItemAdapter = new MultipleItemAdapter(mContext);
        //设置adapter
//        setAdapter(mMultipleItemAdapter);

        //设置下拉刷新和上拉加载的监听
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        getData();
    }


    /**
     * 获取数据
     */
    private void getData() {
        data.clear();
        for (int i = 0; i < 15; i++) {
            if (i % 3 == 0) {
                data.add(new UserBean("张三" + i, "" + i));
            } else if (i % 3 == 1) {
                data.add(new UserBean("李四" + i, "" + i));
            } else {
                data.add(new UserBean("王五" + i, "" + i));
            }
        }

        /** 更新和加载数据 只需要判断数据集合是否为null,不用判断list.size()是否为0
         *  if (null != data) {
               updateData(data);
            }
         * */


        //下拉刷新，更新数据
        if (currentPage == 1) {
            onFinishRefresh();
            updateData(data, pageSize);

            //上拉加载，添加数据
        } else {
            onFinishLoad();
            addData(data, pageSize);
        }

    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        currentPage = 1;
        getData();
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage++;
        getData();
    }
}
