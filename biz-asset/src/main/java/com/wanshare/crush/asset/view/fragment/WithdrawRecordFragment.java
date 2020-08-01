package com.wanshare.crush.asset.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
import com.wanshare.crush.asset.contract.WithdrawRecordContract;
import com.wanshare.crush.asset.model.bean.AssetRecord;
import com.wanshare.crush.asset.model.bean.WithdrawRecordBean;
import com.wanshare.crush.asset.presenter.WithdrawRecordPresenter;
import com.wanshare.crush.asset.view.AssetRecordDetailActivity;
import com.wanshare.wscomponent.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * @author wangdunwei
 * @date 2018/8/23
 */
public class WithdrawRecordFragment extends BaseListFragment<WithdrawRecordContract.Presenter>
        implements WithdrawRecordContract.View {
    @BindView(R2.id.tv_fee)
    TextView tvFee;

    @BindColor(R2.color.color_gray_light1)
    int divideColor;

    private RechargeRecordAdapter withdrawRecordAdapter;

    @Override
    protected void initArguments(Bundle savedInstanceState) {
        super.initArguments(savedInstanceState);
        mPresenter = new WithdrawRecordPresenter(this);
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

        withdrawRecordAdapter = new RechargeRecordAdapter(getContext(), false);
        setAdapter(withdrawRecordAdapter);

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                currentPage++;
                mPresenter.requestWithdrawRecord(currentPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                mPresenter.requestWithdrawRecord(currentPage);
            }
        });

        withdrawRecordAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if(position >= 0 && position < withdrawRecordAdapter.getItemCount()) {
                    ARouter.getInstance()
                           .build(AssetArouterConstant.ASSET_RECORD_DETAIL)
                           .withParcelable(AssetRecordDetailActivity.EXTRA_RECORD,
                                   withdrawRecordAdapter.getItem(position))
                           .withBoolean(AssetRecordDetailActivity.EXTRA_IS_RECHARGE, false)
                           .navigation(getActivity());
                }
            }
        });

        tvFee.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initData() {
        mPresenter.requestWithdrawRecord(currentPage);
    }

    @Override
    public void showLoading(String tips) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showHintMessage(@NonNull String message) {
        super.showHintMessage(message);
        onFinishRefresh();
        onFinishLoad();
    }

    @Override
    public void showWithdrawRecord(WithdrawRecordBean withdrawRecordBean) {
        onFinishRefresh();
        onFinishLoad();
        List<AssetRecord> assetRecords = withdrawRecordBean.getWithdrawRecords();
        if(assetRecords == null) {
            assetRecords = new ArrayList<>(0);
//            测试数据
            for(int i = 0; i < 10; i++) {
                assetRecords.add(new AssetRecord("address" + i, "10", "1", "2011年1月1日", 10, 9, "10", "logo", "remark", "btc",
                        "2011年1月1日", "txid", "confirming", "bitcoin", "0.011"));
            }
        }

        if(withdrawRecordBean.getMeta() != null) {
            currentPage = withdrawRecordBean.getMeta().getPageNo();
        }

        if(currentPage == 1) {
            updateData(assetRecords, withdrawRecordBean.getMeta().getPageSize());
        } else {
            addData(assetRecords, withdrawRecordBean.getMeta().getPageSize());
        }
    }
}
