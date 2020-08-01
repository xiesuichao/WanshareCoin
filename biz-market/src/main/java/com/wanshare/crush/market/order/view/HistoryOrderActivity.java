package com.wanshare.crush.market.order.view;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseListActivity;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.order.adapter.RecycleHistoryAdapter;
import com.wanshare.crush.market.order.contract.HistoryOrderContract;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.common.biz.market.TradingCoins;
import com.wanshare.crush.market.order.presenter.HistoryOrderPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangwenwu on 2018/8/20.
 */
@Route(path = MarketArouterConstant.MARKET_ORDER_HISTORY)
public class HistoryOrderActivity extends BaseListActivity<HistoryOrderContract.Presenter> implements OnRefreshListener, OnLoadMoreListener,
        MultiItemTypeAdapter.OnItemClickListener, MultiItemTypeAdapter.OnItemLongClickListener,HistoryOrderContract.View,View.OnClickListener{

    EditText mEditTimeStart,mEditTimeStop,mEditMarket;
    Button mBtnBuy,mBtnSell,mBtnReset,mBtnSearch;
    TextView mTvCancel,mTvComplete;
    private LinearLayout mLlEndTime, mLlStartTime,mLlOperater;
    private LinearLayout mCoinCompareLl;

    View mMarketView;

    private int pageSize = 20;

    private PopupWindow mPopupWindow;

    //保存动态获取的年份数组
    private List<String> mYearList = new ArrayList<>();
    private List<String> mMonthList = new ArrayList<>();
    private List<String> mDayList = new ArrayList<>();

    private Calendar mCalendar;
    private int currentYear;
    private int currentMon;
    private int currentDay;

    private int mCurrentType;

    //判断显示那个wheelView的flag标识
    private final int START_TIME_WHEEL_FLAG = 1;
    private final int END_TIME_WHEEL_FLAG = 2;
    private final int MARKET_COIN_WHEEL_FLAG = 3;
    private final int COMPARE_COIN_WHEEL_FLAG = 4;
    private final int TYPE_WHEEL_FLAG = 5;


    private RecycleHistoryAdapter mRecycleHistoryAdapter;

    @Override
    protected HistoryOrderContract.Presenter getPresenter(){
        return new HistoryOrderPresenter(this);
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @Override
    protected void initData() {

        //初始化年时间
        initYearData();

        mPresenter.getHistoryTrades(1,null,null,"both",new String[]{"done","cancelled"});
        mPresenter.getTradingCoins();


    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setBackgroundColor(getResources().getColor(R.color.color_white));
        mMyToolbar.setTitle(R.string.market_order_history_title);
        mMyToolbar.setTitleTextStyle(Typeface.DEFAULT_BOLD);
        mMyToolbar.setRightButtonImageVisible(true);
        mMyToolbar.setRightButtonImage(R.drawable.ic_jiaoyi_sousuo);

        mMarketView = findViewById(R.id.ll_market_activity_history_order);

        initPopWindow();

        mCalendar = Calendar.getInstance();
        currentYear = mCalendar.get(Calendar.YEAR);
        currentMon = mCalendar.get(Calendar.MONTH) + 1; //返回的月份比实际月份少1
        currentDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        mLayoutRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycleHistoryAdapter = new RecycleHistoryAdapter(mContext);


        //如果不需要加头部和底部View，直接设置加载normalAdapter
        setAdapter(mRecycleHistoryAdapter);

        //设置下拉刷新和上拉加载的监听
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);

        //设置条目点击事件
        mRecycleHistoryAdapter.setOnItemClickListener(this);
        mRecycleHistoryAdapter.setOnItemLongClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.market_activity_history_order;
    }


    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    protected void onRightButton(View view) {
        super.onRightButton(view);

        mPopupWindow.showAtLocation(mMarketView, Gravity.BOTTOM, 0, 0);
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha=0.3f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    @Override
    public void showHistoryOrder(EntrustsOrder entrustsOrder) {
        updateData(entrustsOrder.getItems(),entrustsOrder.getMeta().getRequestedPage());
    }

    @Override
    public void showTradingCoins(TradingCoins tradingCoins) {


    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.et_market_activity_history_filter_end_time) {
            showWheelView(END_TIME_WHEEL_FLAG);

        } else if (i == R.id.et_market_activity_history_filter_start_time) {
            showWheelView(START_TIME_WHEEL_FLAG);

        } else if (i == R.id.et_market_activity_history_filter_compare_coin){
            showWheelView(MARKET_COIN_WHEEL_FLAG);
        } else if (i == R.id.btn_market_activity_history_filter_order_buy){
            mBtnBuy.setTextColor(getResources().getColor(R.color.color_red_light));
            mBtnBuy.setBackground(getResources().getDrawable(R.drawable.ic_wtjl_gouxxuan));
            mBtnSell.setTextColor(getResources().getColor(R.color.color_gray_dark));
            mBtnSell.setBackground(getResources().getDrawable(R.drawable.common_shape_gray_radius3));

        } else if (i == R.id.btn_market_activity_history_filter_order_sell){
            mBtnBuy.setTextColor(getResources().getColor(R.color.color_gray_dark));
            mBtnSell.setBackground(getResources().getDrawable(R.drawable.ic_wtjl_gouxxuan));
            mBtnSell.setTextColor(getResources().getColor(R.color.color_red_light));
            mBtnBuy.setBackground(getResources().getDrawable(R.drawable.common_shape_gray_radius3));


        }else if (i == R.id.tv_market_activity_history_filter_cancel){
            cancelWheelView();
        } else if (i == R.id.tv_market_activity_history_filter_complete){

        }
    }


    private void initPopWindow(){

        View view = View.inflate(getContext(), R.layout.market_activity_history_filter, null);

        mLlEndTime = view.findViewById(R.id.ll_market_activity_history_filter_end_time);
        mLlStartTime = view.findViewById(R.id.ll_market_activity_history_filter_start_time);
        mLlOperater = view.findViewById(R.id.ll_market_activity_history_filter_operate);
        mCoinCompareLl = view.findViewById(R.id.ll_market_activity_history_filter_coin_compare);

        mEditTimeStart = view.findViewById(R.id.et_market_activity_history_filter_start_time);
        mEditTimeStop = view.findViewById(R.id.et_market_activity_history_filter_end_time);
        mEditMarket = view.findViewById(R.id.et_market_activity_history_filter_compare_coin);
        mBtnBuy = view.findViewById(R.id.btn_market_activity_history_filter_order_buy);
        mBtnSell = view.findViewById(R.id.btn_market_activity_history_filter_order_sell);
        mBtnReset = view.findViewById(R.id.btn_market_activity_history_filter_reset);
        mBtnSearch = view.findViewById(R.id.btn_market_activity_history_filter_search);

        mTvCancel = view.findViewById(R.id.tv_market_activity_history_filter_cancel);
        mTvComplete = view.findViewById(R.id.tv_market_activity_history_filter_complete);

        mEditTimeStart.setOnClickListener(this);
        mEditTimeStop.setOnClickListener(this);
        mEditMarket.setOnClickListener(this);
        mBtnBuy.setOnClickListener(this);
        mBtnSell.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mTvComplete.setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.animTranslate);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                cancelWheelView();
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
            }
        });
    }

    public void showWheelView(int type) {
        mCurrentType = type;
        switch (type) {
            case START_TIME_WHEEL_FLAG:
                cancelAll();
                mLlOperater.setVisibility(View.VISIBLE);
                mLlStartTime.setVisibility(View.VISIBLE);
                break;
            case END_TIME_WHEEL_FLAG:
                cancelAll();
                mLlOperater.setVisibility(View.VISIBLE);
                mLlEndTime.setVisibility(View.VISIBLE);
                break;
            case MARKET_COIN_WHEEL_FLAG:
                cancelAll();
                mLlOperater.setVisibility(View.VISIBLE);
                mCoinCompareLl.setVisibility(View.VISIBLE);
                break;
            case COMPARE_COIN_WHEEL_FLAG:
                cancelAll();
                break;
            case TYPE_WHEEL_FLAG:
                break;
        }
    }

    /*
     * 先把所有的状态恢复，节省代码重复编写
     * */
    public void cancelAll() {
        mLlOperater.setVisibility(View.GONE);
        mLlStartTime.setVisibility(View.GONE);
        mLlEndTime.setVisibility(View.GONE);
        mCoinCompareLl.setVisibility(View.GONE);

    }

    private void cancelWheelView() {
        mLlOperater.setVisibility(View.GONE);
        mLlStartTime.setVisibility(View.GONE);
        mLlEndTime.setVisibility(View.GONE);
        mCoinCompareLl.setVisibility(View.GONE);
//        mTypeWp.setVisibility(View.GONE);
    }

    /*
     * 初始化年
     * */
    public void initYearData() {
        int startYear = 1980;  //可选年份从1980年起
        int endYear = currentYear;
        int yearSize = currentYear - startYear + 1;
        for (int i = 0; i < yearSize; i++) {
            mYearList.add(endYear + "");
            endYear--;
        }
        mMonthList = Arrays.asList(getResources().getStringArray(R.array.WheelMonth)).subList(0, currentMon);
        Collections.reverse(mMonthList);
        mDayList = Arrays.asList(getResources().getStringArray(R.array.WheelDayBig)).subList(0, currentDay);
        Collections.reverse(mDayList);

    }


}
