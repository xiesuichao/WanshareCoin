package com.wanshare.crush.market.trade.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.common.widget.CustomRecyclerViewDivider;
import com.wanshare.common.widget.TradeRcvLayoutManager;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.crush.market.trade.adapter.TradeBarChartLvAdapter;
import com.wanshare.crush.market.trade.adapter.TradeOrderAdapter;
import com.wanshare.crush.market.trade.contract.TradeContract;
import com.wanshare.crush.market.trade.model.bean.CoinAssetsBean;
import com.wanshare.crush.market.trade.model.bean.CoinPair;
import com.wanshare.crush.market.trade.model.bean.Entrust;
import com.wanshare.crush.market.trade.model.bean.TradeDataModel;
import com.wanshare.crush.market.trade.presenter.TradePresenter;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class TradeActivity extends BaseActivity<TradeContract.Presenter> implements TradeContract.View{



    @BindView(R2.id.btn_trade_back)
    LinearLayout mBtnTradeBack;
    @BindView(R2.id.tv_trade_title)
    TextView mTvTradeTitle;
    @BindView(R2.id.btn_trade_title)
    LinearLayout mBtnTradeTitle;
    @BindView(R2.id.btn_trade_right)
    RelativeLayout mBtnTradeRight;
    @BindView(R2.id.rcv_trade_container)
    RecyclerView mContainerRcv;
    @BindView(R2.id.nslv_trade_bar_chart)
    ListView mContainerLv;
    @BindView(R2.id.tv_trade_pop_bg)
    TextView mPopBgTv;
    @BindView(R2.id.rb_trade_left_buy_in)
    RadioButton mLeftBuyInRb;
    @BindView(R2.id.rb_trade_right_sell_out)
    RadioButton mRightSellOutRb;
    @BindView(R2.id.iv_trade_price_minus)
    ImageView mMinusIv;
    @BindView(R2.id.iv_trade_price_add)
    ImageView mAddIv;
    @BindView(R2.id.btn_trade_bot_commit)
    Button mBotCommitBtn;
    @BindView(R2.id.rl_trade_price_type_select)
    RelativeLayout mPriceTypeSelectRl;
    @BindView(R2.id.tv_trade_price_type)
    TextView mPriceTypeTv;
    @BindView(R2.id.et_trade_deal_price_input)
    EditText mDealPriceEt;
    @BindView(R2.id.tv_trade_first_percent)
    TextView mFirstPercentTv;
    @BindView(R2.id.tv_trade_second_percent)
    TextView mSecondPercentTv;
    @BindView(R2.id.tv_trade_third_percent)
    TextView mThirdPercentTv;
    @BindView(R2.id.tv_trade_forth_precent)
    TextView mForthPercentTv;
    @BindView(R2.id.tv_trade_top_deal_amount)
    TextView mTradeAmountTv;
    @BindView(R2.id.tv_trade_top_available_count)
    TextView mAvailableCountTv;
    @BindView(R2.id.et_trade_amount_input)
    EditText mDealCountEt;
    @BindView(R2.id.tv_trade_available_count_unit)
    TextView mAvailableUnitTv;
    @BindView(R2.id.tv_trade_deal_amount_unit)
    TextView mDealAmountUnitTv;
    @BindView(R2.id.tv_trade_syn_cny)
    TextView mTvAmountCNY;
    @BindView(R2.id.fl_trade_deal_price_container)
    FrameLayout mDealPriceFl;
    @BindView(R2.id.rl_trade_deal_amount_container)
    FrameLayout mDealAmountFl;
    @BindColor(R2.color.color_gray_light1)
    int dividerColor;

    private TradeOrderAdapter mTradeOrderAdapter;
    private TradeBarChartLvAdapter mainLvAdapter;
    private List<CoinPair> mTopDataList = new ArrayList<>();
    private List<CoinPair> mBotDataList = new ArrayList<>();
    private Entrust mTradeEntrust = new Entrust();
    private TradeDataModel mTradeDataModel = new TradeDataModel();

    private String mCountNum;


    @Override
    protected TradeContract.Presenter getPresenter() {
        return new TradePresenter(this);
    }

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {

        }
    }

    @Override
    protected void initData() {
        mPresenter.getEntrustList();
        mPresenter.getTradeList();
    }

    @Override
    protected void initView() {
        initTradeList();
        initListView();
    }

    private void initTradeList(){
        mContainerRcv.setNestedScrollingEnabled(false);
        mContainerRcv.addItemDecoration(
                new CustomRecyclerViewDivider(DensityUtil.dp2px(0.5f), dividerColor, 0));
        mTradeOrderAdapter = new TradeOrderAdapter(mContext);
        mContainerRcv.setLayoutManager(new TradeRcvLayoutManager(getContext()));
        mContainerRcv.setAdapter(mTradeOrderAdapter);
    }

    private void initListView() {
        mainLvAdapter = new TradeBarChartLvAdapter(getContext(), mTopDataList, mBotDataList, mTradeDataModel);
        mContainerLv.setAdapter(mainLvAdapter);
        mContainerLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mTopDataList != null && mTopDataList.size() > 0 && position >= 0 && position < 5) {
                    int dataPosition = position - 5 + mTopDataList.size();
                    if (dataPosition >= 0 && dataPosition < mTopDataList.size()) {
                        mTradeEntrust.setPrice(mTopDataList.get(dataPosition).getPrice());
                    }
                } else if (mBotDataList != null && mBotDataList.size() > 0 && position > 5
                        && position < mBotDataList.size() + 6) {
                    mTradeEntrust.setPrice(mBotDataList.get(position - 6).getPrice());
                }
                if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
                    mCountNum = getDecimalEndNum(mDealPriceEt.getText().toString().trim());
                    String tradePrice = mTradeEntrust.getPrice();
                    mDealPriceEt.setText(tradePrice);
                    mDealPriceEt.setSelection(mDealPriceEt.getText().length());
                }
            }
        });
    }



    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.market_activity_trade;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }

    @OnClick({R2.id.btn_trade_back, R2.id.btn_trade_title, R2.id.btn_trade_right,
            R2.id.rb_trade_left_buy_in, R2.id.rb_trade_right_sell_out,
            R2.id.btn_trade_bot_commit, R2.id.iv_trade_price_add,
            R2.id.iv_trade_price_minus, R2.id.tv_trade_first_percent,
            R2.id.tv_trade_second_percent, R2.id.tv_trade_third_percent,
            R2.id.tv_trade_forth_precent})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_trade_back) {
            finish();

        } else if (i == R.id.btn_trade_title) {
        } else if (i == R.id.iv_trade_price_add) {
            mCountNum = getDecimalEndNum(mDealPriceEt.getText().toString().trim());
            if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
                mTradeEntrust.setPrice(ArithUtil.add(mTradeEntrust.getPrice(), mCountNum));
                mDealPriceEt.setText(mTradeEntrust.getPrice());
                mDealPriceEt.setSelection(mDealPriceEt.getText().length());
            }

        } else if (i == R.id.iv_trade_price_minus) {
            mCountNum = getDecimalEndNum(mDealPriceEt.getText().toString().trim());
            if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
                mTradeEntrust.setPrice(ArithUtil.sub(mTradeEntrust.getPrice(), mCountNum));
                if (Double.parseDouble(mTradeEntrust.getPrice()) < 0) {
                    mTradeEntrust.setPrice("0");
                }
                mDealPriceEt.setText(mTradeEntrust.getPrice());
                mDealPriceEt.setSelection(mDealPriceEt.getText().length());
            }

        } else if (i == R.id.btn_trade_right) {
            ARouter.getInstance().build(MarketArouterConstant.MARKET_ORDER_HISTORY).navigation(this);

        } else if (i == R.id.rb_trade_left_buy_in) {
            resetOrderInfo("buy");

        } else if (i == R.id.rb_trade_right_sell_out) {
            resetOrderInfo("sell");

        } else if (i == R.id.btn_trade_bot_commit) {
        } else if (i == R.id.tv_trade_first_percent) {
            resetPercentTv("0.25");

        } else if (i == R.id.tv_trade_second_percent) {
            resetPercentTv("0.5");

        } else if (i == R.id.tv_trade_third_percent) {
            resetPercentTv("0.75");

        } else if (i == R.id.tv_trade_forth_precent) {
            resetPercentTv("1");

        }
    }

    private void resetPercentTv(String percentStr) {
        mAvailableCountTv.setText("10000");
        String price = mDealPriceEt.getText().toString();
        String availableCount = mAvailableCountTv.getText().toString();
        if (TextUtils.isEmpty(availableCount)) {
            showLongToast(getString(R.string.market_trade_getting_balance));
            return;
        }
        if (TextUtils.isEmpty(price) || price.matches("[0-0,.]{1,}")) {
            showLongToast(getString(R.string.market_trade_not_get_balance));
            return;
        }
        if ("0".equals(availableCount)) {
            mDealCountEt.setText("0");
        } else {
            String tradeType = mTradeEntrust.getTrade_type();
            if ("buy".equals(tradeType)) {
                String maxCount = ArithUtil.div(availableCount, mDealPriceEt.getText().toString(), 8);
                if (maxCount.contains("E")) {
                    mDealCountEt.setText("0");
                } else {
                    String buyAmount = ArithUtil.mul(maxCount, percentStr, 8);
                    mDealCountEt.setText(buyAmount);
                }
            } else {
                String sellAmount = ArithUtil.mul(availableCount, percentStr, 8);
                mDealCountEt.setText(sellAmount);
            }
            mDealCountEt.setSelection(mDealCountEt.getText().length());
        }
    }

    private void resetOrderInfo(String type) {
        if (mTradeAmountTv == null || mPriceTypeTv == null || mDealPriceEt == null || mDealCountEt == null
                || mAvailableCountTv == null) {
            return;
        }
        if ("buy".equals(type)) {
            mBotCommitBtn.setBackgroundResource(R.drawable.common_corner_green_green_selector);
            mBotCommitBtn.setText(R.string.market_trade_buy);
            mLeftBuyInRb.setChecked(true);
        } else if ("sell".equals(type)) {
            mBotCommitBtn.setBackgroundResource(R.drawable.common_corner_red_red_selector);
            mBotCommitBtn.setText(R.string.market_trade_sell);
            mRightSellOutRb.setChecked(true);
        }
        mTradeEntrust.setTrade_type(type);
        mTradeEntrust.setTrades_count("0");
        mTradeEntrust.setOrd_type("limit");
        mTradeEntrust.setPrice("");
        mTradeEntrust.setAmounts("");
        mTradeAmountTv.setText("0");
        mPriceTypeTv.setText(R.string.market_trade_limit_order);
        mDealPriceEt.setText("");
        mDealCountEt.setText("");
        mAvailableCountTv.setText("");
//        getBalance();
    }

    private void updateDepthLv() {
        if (mainLvAdapter != null) {
            mainLvAdapter.updateList();
        }
    }

    private void updateMainRcv() {
        if (mTradeOrderAdapter != null) {
            mTradeOrderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void resultEntrustMock(List<EntrustsOrder.ItemsBean> list) {
        mTradeOrderAdapter.updateList(list);
    }

    @Override
    public void resultTrades(List<CoinPair> topList, List<CoinPair> botList) {
        mTopDataList.clear();
        mBotDataList.clear();
        mTopDataList.addAll(topList);
        mBotDataList.addAll(botList);
        mTradeDataModel.setmChange("121");
        mTradeDataModel.setmLastPrice("123");
        mTradeDataModel.setmPercent("-31%");
        mTradeDataModel.setmPriceToCNY("≈12121");
        updateDepthLv();
    }

    @Override
    public void resultEntrust(Object object) {

    }

    @Override
    public void resultEntrustList(List<EntrustsOrder.ItemsBean> items) {

    }

    @Override
    public void resultEntrustCancel(Object object) {

    }

    @Override
    public void resultCoinAssert(CoinAssetsBean bean) {

    }

    public static String getDecimalEndNum(String str){
        String num = "1";
        if (str.contains(".")) {
            String priceDecimal = str.substring(str.indexOf(".") + 1);
            switch (priceDecimal.length()) {
                case 1:
                    num = "0.1";
                    break;

                case 2:
                    num = "0.01";
                    break;

                case 3:
                    num = "0.001";
                    break;

                case 4:
                    num = "0.0001";
                    break;

                case 5:
                    num = "0.00001";
                    break;

                case 6:
                    num = "0.000001";
                    break;

                case 7:
                    num = "0.0000001";
                    break;

                case 8:
                    num = "0.00000001";
                    break;
            }
        }
        return num;
    }

}
