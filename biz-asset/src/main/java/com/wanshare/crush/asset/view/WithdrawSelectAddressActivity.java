package com.wanshare.crush.asset.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.adapter.WalletAddressListAdapter;
import com.wanshare.crush.asset.contract.WithdrawSelectAddressContract;
import com.wanshare.crush.asset.model.bean.Coin;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;
import com.wanshare.crush.asset.presenter.WithdrawSelectAddressPresenter;
import com.wanshare.wscomponent.logger.LogUtil;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * 提币选择地址
 * </br>
 * Date: 2018/8/27 19:44
 *
 * @author hemin
 */
@Route(path = AssetArouterConstant.WITHDRAW_SELECT_ADDRESS)
public class WithdrawSelectAddressActivity extends BaseListActivity<WithdrawSelectAddressContract.Presenter> implements WithdrawSelectAddressContract.View, OnRefreshListener, OnLoadMoreListener {

    public static final int REQUEST_SELECT_ADDRESS = 2001;

    @BindView(R2.id.layout_recycler)
    RecyclerView mRvContent;

    @BindColor(R2.color.color_gray_light1)
    int mDividerColor;

    String mCoinId;
    private WalletAddressListAdapter mAdapter;

    @Override
    protected WithdrawSelectAddressContract.Presenter getPresenter() {
        return new WithdrawSelectAddressPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_withdraw_select_address;
    }

    @Override
    protected void initIntent() {
        mCoinId = getIntent().getStringExtra(IntentConstant.EXTRA_CURRENCY);
        if (TextUtils.isEmpty(mCoinId)) {
            LogUtil.d("coinid is null or empty");
            finish();
            return;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setTitle(R.string.asset_withdraw_address);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.addItemDecoration(new CustomRecyclerViewDivider(2, mDividerColor, 0));
        mAdapter = new WalletAddressListAdapter(this);
        setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra(IntentConstant.EXTRA_ADDRESS, mAdapter.getItem(position).getAddress());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
//        mRvContent.setAdapter(mAdapter);

        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        mPresenter.getWithdrawAddresses(mCoinId, currentPage);
    }

    @Override
    public void showWithdrawAddresses(WithdrawAddress result) {
        if (result == null || result.getWithdrawAddresses() == null || result.getMeta() == null || result.getMeta().getPageSize() <= 0) {
            onFinishRefresh();
            onFinishLoad();
            return;
        }

        currentPage = result.getMeta().getPageNo();

        if (currentPage == 1) {
            //下拉刷新，更新数据
            onFinishRefresh();
            updateData(result.getWithdrawAddresses(), result.getMeta().getPageSize());
        } else {
            //上拉加载，添加数据
            onFinishLoad();
            addData(result.getWithdrawAddresses(), result.getMeta().getPageSize());
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
        initData();
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        currentPage++;
        initData();
    }
}
