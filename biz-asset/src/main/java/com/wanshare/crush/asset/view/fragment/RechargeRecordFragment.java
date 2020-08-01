package com.wanshare.crush.asset.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListFragment;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.adapter.RechargeRecordAdapter;
import com.wanshare.crush.asset.contract.RechargeRecordContract;
import com.wanshare.crush.asset.model.bean.AssetRecord;
import com.wanshare.crush.asset.model.bean.RechargeRecordBean;
import com.wanshare.crush.asset.presenter.RechargeRecordPresenter;
import com.wanshare.crush.asset.view.AssetRecordDetailActivity;
import com.wanshare.wscomponent.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;

/**
 * @author wangdunwei
 * @date 2018/8/23
 */
public class RechargeRecordFragment extends BaseListFragment<RechargeRecordContract.Presenter>
        implements RechargeRecordContract.View {
    @BindColor(R2.color.color_gray_light1)
    int divideColor;
    private RechargeRecordAdapter rechargeRecordAdapter;

    @Override
    protected void initArguments(Bundle savedInstanceState) {
        super.initArguments(savedInstanceState);
        mPresenter = new RechargeRecordPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_recharge_record_fragment;
    }

    @Override
    protected void initView() {
        super.initView();

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mLayoutRecycler.addItemDecoration(
                new CustomRecyclerViewDivider(DensityUtil.dip2px(getContext(), 1), divideColor, 0));

        rechargeRecordAdapter = new RechargeRecordAdapter(getContext(), true);
        setAdapter(rechargeRecordAdapter);

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestRechargeRecord(++currentPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                mPresenter.requestRechargeRecord(currentPage);
            }
        });

        rechargeRecordAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position >= 0 && position < rechargeRecordAdapter.getItemCount()) {
                    ARouter.getInstance()
                           .build(AssetArouterConstant.ASSET_RECORD_DETAIL)
                           .withParcelable(AssetRecordDetailActivity.EXTRA_RECORD,
                                   rechargeRecordAdapter.getItem(position))
                           .withBoolean(AssetRecordDetailActivity.EXTRA_IS_RECHARGE, true)
                           .navigation(getActivity());
                }
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.requestRechargeRecord(currentPage);
    }

    @Override
    public void showHintMessage(@NonNull String message) {
        super.showHintMessage(message);
        onFinishRefresh();
        onFinishLoad();
    }

    @Override
    public void showRechargeRecord(RechargeRecordBean rechargeRecordBean) {
        onFinishRefresh();
        onFinishLoad();
        List<AssetRecord> assetRecords = rechargeRecordBean.getRechargeRecords();
        if(assetRecords == null) {
            assetRecords = new ArrayList<>(0);
//            测试数据
            for(int i = 0; i < 10; i++) {
                assetRecords.add(new AssetRecord("address" + i, "10", "1", "2011年1月1日", 10, 9, "10", "logo", "remark", "btc",
                        "2011年1月1日", "txid", "confirming", "bitcoin", "0.011"));
            }
        }

        if(rechargeRecordBean.getMeta() != null) {
            currentPage = rechargeRecordBean.getMeta().getPageNo();
        }

        if(currentPage == 1) {
            updateData(assetRecords, rechargeRecordBean.getMeta().getPageSize());
        } else {
            addData(assetRecords, rechargeRecordBean.getMeta().getPageSize());
        }
    }
}
