package com.wanshare.common.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wanshare.common.R;
import com.wanshare.common.adapter.EmptyViewAdapter;
import com.wanshare.common.adapter.HeaderAndFooterWrapper;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.mvp.IPresenter;

import java.util.List;

/**
 * Created by Jason on 2018/8/23.
 */

public abstract class BaseListFragment<P extends IPresenter> extends BaseFragment<P> {

    /**
     * 列表recyclerView
     */
    public RecyclerView mLayoutRecycler;
    /**
     * 上拉加载/下拉刷新 的容器
     */
    public SmartRefreshLayout mSmartRefreshLayout;

    /**
     * 当前页面数
     */
    protected int currentPage = 1;


    private EmptyViewAdapter mEmptyViewAdapter;

    @Override
    protected void initView() {
        mLayoutRecycler = parentView.findViewById(R.id.layout_recycler);
        mSmartRefreshLayout = parentView.findViewById(R.id.smartRefresh);

        if (null != mLayoutRecycler) {
            mLayoutRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        }
    }

    /**
     * 设置是否禁用下拉刷新
     *
     * @param isDown true 可以下拉刷新 false 禁止
     */
    protected void setDown(boolean isDown) {
        mSmartRefreshLayout.setEnableRefresh(isDown);
    }

    /**
     * 设置是否禁用上拉加载
     *
     * @param isUp true 可以上拉加载 false 禁止
     */
    protected void setIsUp(boolean isUp) {
        mSmartRefreshLayout.setEnableLoadMore(isUp);
    }

    /**
     * 下拉刷新完成
     */
    protected void onFinishRefresh() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mSmartRefreshLayout) {
                    mSmartRefreshLayout.finishRefresh();
                }
            }
        });
    }

    /**
     * 上拉加载完成
     */
    protected void onFinishLoad() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != mSmartRefreshLayout) {
                    mSmartRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    /**
     * 添加列表头部view和底部view
     *
     * @param headerView
     * @param footerView
     * @param adapter
     * @return
     */
    protected <T extends MultiItemTypeAdapter> RecyclerView.Adapter addHeaderAndFooter(View headerView, View footerView, @NonNull T adapter) {
        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(adapter);
        if (null != headerView) {
            headerAndFooterWrapper.addHeaderView(headerView);
        }
        if (null != footerView) {
            headerAndFooterWrapper.addFootView(footerView);
        }
        return headerAndFooterWrapper;
    }

    /**
     * 设置列表的adater 封装了空视图
     *
     * @param adapter
     */
    protected void setAdapter(MultiItemTypeAdapter adapter) {
        mEmptyViewAdapter = new EmptyViewAdapter(adapter);
        mLayoutRecycler.setAdapter(mEmptyViewAdapter);
    }

    /**
     * 列表更新数据
     *
     * @param pageSize 分页加载数据量
     * @param data
     */
    protected void updateData(List data, int pageSize) {
        if (null == data) {
            return;
        }
        setIsUp(data.size() == pageSize);
        if (data.size() <= 0) {
            mEmptyViewAdapter.setEmptyView(updateEmptyView());
        }else {
            mEmptyViewAdapter.updateList(data);
        }

    }

    /**
     * 自定义空视图
     */
    protected int updateEmptyView() {
//        return LayoutInflater.from(mActivity).inflate(emptyView, mLayoutRecycler, false);
        return R.layout.empty_view;
    }

    /**
     * 列表加载更多数据
     *
     * @param pageSize 分页加载数据量
     * @param data
     */
    protected void addData(List data, int pageSize) {
        if (null == data) {
            return;
        }
        setIsUp(data.size() == pageSize);
        mEmptyViewAdapter.addList(data);
    }

    /**
     * 刷新列表
     */
    protected void refresh() {
        mEmptyViewAdapter.refresh();
    }
}
