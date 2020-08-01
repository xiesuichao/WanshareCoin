package com.wanshare.crush.asset.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wanshare.common.adapter.CustomRecyclerViewDivider;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.adapter.WalletAddressListAdapter;
import com.wanshare.crush.asset.contract.WithdrawAddressListContract;
import com.wanshare.crush.asset.model.bean.Coin;
import com.wanshare.crush.asset.model.bean.WithdrawAddress;
import com.wanshare.crush.asset.presenter.WithdrawAddressListPresenter;
import com.wanshare.wscomponent.utils.DensityUtil;
import com.wanshare.wscomponent.utils.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;


/**
 * 提币地址列表页
 *
 * @author wangdunwei
 */
@Route(path = AssetArouterConstant.WITHDRAW_ADDRESS_LIST)
public class WithdrawAddressListActivity extends BaseListActivity<WithdrawAddressListContract.Presenter>
        implements WithdrawAddressListContract.View {

    private static final int SLIDE_TEXT_SIZE = 15;
    public static final int REQUEST_UPDATE_ADDRESS = 2000;

    public static final String EXTRA_IS_EDIT_MODE = "extra_is_edit";
    public static final String EXTRA_ADDRESS = "extra_address";

    @BindColor(R2.color.color_gray_light1)
    int mDividerColor;
    @BindColor(R2.color.gray_text_line)
    int slideEditBg;
    @BindColor(R2.color.color_main_light)
    int slideDeleteBg;

    private WalletAddressListAdapter mAdapter;

    //预留参数，后边会用到
    private String coinId = "";
    private String coinName = "btc";

    @Override
    protected WithdrawAddressListContract.Presenter getPresenter() {
        return new WithdrawAddressListPresenter(this);
    }

    @Override
    protected void initData() {
        mPresenter.requestWithdrawAddressList(coinId, currentPage);
    }

    @Override
    protected void initView() {
        mSmartRefreshLayout = findViewById(R.id.smartRefresh);
        mLayoutRecycler = findViewById(R.id.layout_recycler);

        mMyToolbar.setTitle(R.string.asset_withdraw_address);
        mMyToolbar.setRightButtonImageVisible(true);
        mMyToolbar.setRightButtonImage(R.drawable.common_asset_address_add_selector);
        mMyToolbar.setOnRightButtonImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance()
                       .build(AssetArouterConstant.WITHDRAW_ADDRESS_ADD)
                       .withString(AddWithdrawAddressActivity.EXTRA_COIN_NAME, coinName)
                       .navigation(getActivity(), REQUEST_UPDATE_ADDRESS);
            }
        });

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(this));
        mLayoutRecycler.addItemDecoration(new CustomRecyclerViewDivider(2, mDividerColor, 0));

        boolean isEdit = getIntent().getBooleanExtra(EXTRA_IS_EDIT_MODE, true);
        if(isEdit) {
            if(mLayoutRecycler instanceof SwipeMenuRecyclerView) {
                ((SwipeMenuRecyclerView) mLayoutRecycler).setSwipeMenuCreator(swipeMenuCreator);
                ((SwipeMenuRecyclerView) mLayoutRecycler).setSwipeMenuItemClickListener(mMenuItemClickListener);
            }
        } else {
            mMyToolbar.setRightButtonImage(null);
        }

        mAdapter = new WalletAddressListAdapter(this);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_ADDRESS, mAdapter.getItem(position).getAddress());
                setResult(RESULT_OK, intent);
            }
        });
        setAdapter(mAdapter);

        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestWithdrawAddressList(coinId, ++currentPage);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                currentPage = 1;
                mPresenter.requestWithdrawAddressList(coinId, currentPage);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_withdraw_address_list;
    }

    @Override
    public void showHintMessage(@NonNull String message) {
        ToastUtil.showShort(getContext(), message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_UPDATE_ADDRESS && resultCode == RESULT_OK) {
            mPresenter.requestWithdrawAddressList(coinId, currentPage);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int slideItemWidth = DensityUtil.dip2px(getContext(), 80);

            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                        .setBackgroundColor(slideDeleteBg)
                        .setText(R.string.asset_delete)
                        .setTextColor(Color.WHITE)
                        .setTextSize(SLIDE_TEXT_SIZE)
                        .setWidth(slideItemWidth)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if(direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                //这里注意，如果侧滑中编辑选项去掉，这里的menuPosition也要相应更改
                if(menuPosition == 0) {
                    mPresenter.deleteWithdrawAddress(mAdapter.getItem(adapterPosition).getId());
                }
            }
        }
    };

    @Override
    public void showWithdrawAddressList(WithdrawAddress withdrawAddress) {
        onFinishRefresh();
        onFinishLoad();

        List<Coin> coins = withdrawAddress.getWithdrawAddresses();
        if(coins == null) {
            coins = new ArrayList<>(0);
        }

        if(withdrawAddress.getMeta() != null) {
            currentPage = withdrawAddress.getMeta().getPageNo();
        }

        int pageSize = withdrawAddress.getMeta() == null ? withdrawAddress.getMeta().getPageSize() : 10;
        if(currentPage == 1) {
            updateData(coins, pageSize);
        } else {
            addData(coins, pageSize);
        }
    }

    @Override
    public void onDeleteAddressSucceed() {
        ToastUtil.showShort(getContext(), R.string.asset_delete_wa_succeed);
        mPresenter.requestWithdrawAddressList(coinId, currentPage);
    }
}
