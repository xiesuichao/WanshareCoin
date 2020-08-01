package com.wanshare.crush.market.order.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.WebSocketBaseFragment;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.order.adapter.RecycleCurrentAdapter;
import com.wanshare.crush.market.order.contract.EntrustsOrderContract;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.crush.market.order.presenter.EntrustsOrderPresenter;
import com.wanshare.wscomponent.toolbar.MyToolbar;
import com.wanshare.wscomponent.utils.DeviceUtil;
import com.wanshare.wscomponent.websocket.DepthResponse;
import com.wanshare.wscomponent.websocket.ErrorResponse;
import com.wanshare.wscomponent.websocket.MessageType;
import com.wanshare.wscomponent.websocket.Response;
import com.wanshare.wscomponent.websocket.bean.DepthEntity;
import com.wanshare.wscomponent.websocket.bean.KlineEntity;

import butterknife.BindView;

/**
 * Created by yangwenwu on 2018/8/22.
 */

public class EntrustsOrderFragment extends WebSocketBaseFragment<EntrustsOrderContract.Presenter> implements EntrustsOrderContract.View{

    @BindView(R2.id.ll_root_container)
    LinearLayout mLlRootContainer;
    @BindView(R2.id.tb_market_current_title)
    MyToolbar myToolbar;
    @BindView(R2.id.rl_market_current_order)
    RecyclerView mRlHistoryOrder;


    public static EntrustsOrderFragment newInstance(){

        Bundle args = new Bundle();

        EntrustsOrderFragment fragment = new EntrustsOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected EntrustsOrderContract.Presenter getPresenter(){
        return new EntrustsOrderPresenter(this);
    }
    @Override
    protected int getContentView() {
        return R.layout.market_fragment_current_order;
    }

    private RecycleCurrentAdapter mRecycleCurrentAdapter;

    @Override
    protected void initView() {
        mLlRootContainer.setPadding(mLlRootContainer.getPaddingLeft(), DeviceUtil.getStatusBarHeight(mActivity),mLlRootContainer.getPaddingRight(),mLlRootContainer.getPaddingBottom());


        //初始化交易所列表和项目推荐列表
        mRlHistoryOrder.setLayoutManager(new LinearLayoutManager(mActivity));

        //创建adapter
        mRecycleCurrentAdapter = new RecycleCurrentAdapter(mActivity);
        mRlHistoryOrder.setAdapter(mRecycleCurrentAdapter);

        mRecycleCurrentAdapter.setOnCancelListener(new RecycleCurrentAdapter.OnCancelListener() {
            @Override
            public void onCancelClick(View view, String orderId) {
                mPresenter.entrustsCancel(orderId);
            }
        });

        myToolbar.setOnRightButtonImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MarketArouterConstant.MARKET_ORDER_HISTORY).navigation(mActivity);
            }
        });

    }

    @Override
    protected void initData() {

        if (UserInfoManager.getInstance().isLogin()) {
            mPresenter.getEntrustsOrder(1, null, null, "both", new String[]{"entrusting"});
        }

    }

    @Override
    public void showEntrustsOrder(EntrustsOrder entrustsOrder) {
        mRecycleCurrentAdapter.updateList(entrustsOrder.getItems());
    }

    @Override
    public void showEntrustsCancel(Object object) {

    }

    @Override
    public void showKline(KlineEntity klineEntity) {

    }

    @Override
    public void onServiceBindSuccess() {
        super.onServiceBindSuccess();

        sendText("k-line/21345/1d");
//        sendText("depth/21345");
//        sendText("overview/21345");
//        sendText("partition-summary/21345");
//        sendText("trade-history/21345");

    }

    @Override
    public void onMessageResponse(Response message) {

        if (message.getType() == MessageType.KLINE ){
            KlineEntity klineEntity= (KlineEntity)message.getResponseEntity();
            String period = klineEntity.getPeriod();
        }
    }

    @Override
    public void onSendMessageError(ErrorResponse error) {

        if (error.getErrorCode() == MessageType.JASON_DECODE_ERROR){
            String er = error.getDescription();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
//            sendText("[\"UNSUBSCRIBE\\nid:sub-0\\ndestination:/topic/crush/trade-history/21345\\n\\n\\u0000\"]");
//            sendText("[\"UNSUBSCRIBE\\nid:sub-0\\ndestination:/topic/crush/trade-history/21345\\n\\n\\u0000\"]");
        }
    }


}
