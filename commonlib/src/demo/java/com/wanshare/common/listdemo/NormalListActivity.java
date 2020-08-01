package com.wanshare.common.listdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanshare.common.R;
import com.wanshare.common.adapter.HeaderAndFooterWrapper;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.listdemo.adapter.NormalAdapter;
import com.wanshare.common.listdemo.mode.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2018/8/6.
 *
 * 常见列表的使用demo
 */

public class NormalListActivity extends BaseListActivity implements OnRefreshListener, OnLoadMoreListener, MultiItemTypeAdapter.OnItemClickListener, MultiItemTypeAdapter.OnItemLongClickListener {

    /**
     * 每页加载的数据量
     * 如果网络请求不需要分页加载，此参数为0
     */
    private int pageSize = 20;
    private  ArrayList<UserBean> data = new ArrayList<>();
    private NormalAdapter mNormalAdapter;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, NormalListActivity.class));
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_normal_list;
    }

    @Override
    protected void initView() {
        //设置标题
        mMyToolbar.setTitle(getString(R.string.test_list_show));
        mMyToolbar.setRightButtonText(getString(R.string.finish));

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(this));
        mNormalAdapter = new NormalAdapter(mContext);
        //创建头部view
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_view, mLayoutRecycler, false);
        //添加头部和底部view,如果只需要其中一个，另一个参数可为null
        HeaderAndFooterWrapper adapter = (HeaderAndFooterWrapper) addHeaderAndFooter(headerView, null, mNormalAdapter);
        //recyclerView加载adapter
//        setAdapter(adapter);

        //如果不需要加头部和底部View，直接设置加载normalAdapter
//        setAdapter(mNormalAdapter,pageSize);

        //设置下拉刷新和上拉加载的监听
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);

        //设置条目点击事件
        mNormalAdapter.setOnItemClickListener(this);
        mNormalAdapter.setOnItemLongClickListener(this);
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

        //下拉刷新，更新数据
        if (currentPage == 1) {
            onFinishRefresh();
            updateData(data,pageSize);

        //上拉加载，添加数据
        } else {
            onFinishLoad();
            addData(data,pageSize);
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

    /**
     * 条目点击事件
     * @param view
     * @param holder
     * @param position
     */
    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        List<UserBean> datas = mNormalAdapter.getDatas();
        UserBean userBean = datas.get(position);
        showShortToast("第"+position+"个条目是="+userBean.toString());
    }

    /**
     * 条目长按事件
     * @param view
     * @param holder
     * @param position
     * @return
     */
    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }
}
