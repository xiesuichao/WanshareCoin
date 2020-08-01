package com.wanshare.crush.market.trade.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.wanshare.common.adapter.MultiItemTypeAdapter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.account.constant.AccountArouterConstant;
import com.wanshare.common.biz.exchange.IMarketDialog;
import com.wanshare.common.biz.exchange.MarketFactory;
import com.wanshare.common.biz.exchange.OnMarketItemClickListener;
import com.wanshare.common.biz.market.MarketArouterConstant;
import com.wanshare.common.usercenter.UserInfoManager;
import com.wanshare.common.widget.CustomRecyclerViewDivider;
import com.wanshare.common.widget.CustomTabLayout;
import com.wanshare.common.widget.TradeRcvLayoutManager;
import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.crush.market.trade.adapter.TradeBarAdapter;
import com.wanshare.crush.market.trade.adapter.TradeOrderAdapter;
import com.wanshare.crush.market.trade.contract.TradeContract;
import com.wanshare.crush.market.trade.model.bean.CoinAssetsBean;
import com.wanshare.crush.market.trade.model.bean.CoinPair;
import com.wanshare.crush.market.trade.model.bean.Entrust;
import com.wanshare.crush.market.trade.model.bean.EntrustReq;
import com.wanshare.crush.market.trade.presenter.TradePresenter;
import com.wanshare.crush.market.trade.view.widget.SingleTextWatcher;
import com.wanshare.wscomponent.utils.ArithUtil;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 交易界面
 */
@Route(path = MarketArouterConstant.MARKET_TRADE)
public class TradeActivity1 extends BaseActivity<TradeContract.Presenter> implements TradeContract.View {

    public static final String EXCHANGE_ID = "exchangeId";
    public static final String TRADING_PAIR = "tradingPair";
    public static final String MARKET_ID = "market_id";

    @BindColor(R2.color.color_gray_light1)
    int dividerColor;
    @BindView(R2.id.btn_trade_back)
    LinearLayout mBtnTradeBack;
    @BindView(R2.id.tv_trade_market_left)
    TextView mTvTradeMarketLeft;
    @BindView(R2.id.tv_trade_market_right)
    TextView mTvTradeMarketRight;
    @BindView(R2.id.btn_trade_title)
    LinearLayout mBtnTradeTitle;
    @BindView(R2.id.btn_trade_right)
    RelativeLayout mBtnTradeRight;
    @BindView(R2.id.rl_trade_title)
    RelativeLayout mRlTradeTitle;
    @BindView(R2.id.tab_trade_change)
    CustomTabLayout mTabTradeChange;
    @BindView(R2.id.tv_trade_price)
    TextView mTvTradePrice;
    @BindView(R2.id.tv_trade_price_equal)
    TextView mTvTradePriceEqual;
    @BindView(R2.id.tv_trade_price_gain)
    TextView mTvTradePriceGain;
    @BindView(R2.id.tv_trade_buy)
    TextView mTvTradeBuy;
    @BindView(R2.id.rv_trade_buy)
    RecyclerView mRvTradeBuy;
    @BindView(R2.id.tv_trade_sell)
    TextView mTvTradeSell;
    @BindView(R2.id.rv_trade_sell)
    RecyclerView mRvTradeSell;
    @BindView(R2.id.et_trade_input_price)
    EditText mEtTradeInputPrice;
    @BindView(R2.id.btn_trade_reduce)
    RelativeLayout mBtnTradeReduce;
    @BindView(R2.id.btn_trade_add)
    RelativeLayout mBtnTradeAdd;
    @BindView(R2.id.tv_trade_input_price_equal)
    TextView mTvTradeInputPriceEqual;
    @BindView(R2.id.et_trade_input_number)
    EditText mEtTradeInputNumber;
    @BindView(R2.id.btn_trade_1_4)
    TextView mBtnTrade14;
    @BindView(R2.id.btn_trade_1_2)
    TextView mBtnTrade12;
    @BindView(R2.id.btn_trade_3_4)
    TextView mBtnTrade34;
    @BindView(R2.id.btn_trade_all)
    TextView mBtnTradeAll;
    @BindView(R2.id.tv_trade_available)
    TextView mTvTradeAvailable;
    @BindView(R2.id.tv_trade_translation)
    TextView mTvTradeTranslation;
    @BindView(R2.id.btn_trade_commit)
    Button mBtnTradeCommit;
    @BindView(R2.id.textView)
    TextView mTextView;
    @BindView(R2.id.rcv_trade_container)
    RecyclerView mRcvTradeContainer;
    @BindView(R2.id.view_trade_pop_bg)
    View mViewTradePopBg;

    //0:买入 1:卖出
    private int type;
    private int pricePrecision = 0;
    private int volumePrecision = 0;
    private double mDicNum = 6.5;
    private String mMarketId;
    private String mCountNum;
    private String mTradingPair = "";
    private String mExchangeId = "";
    private String mBalance = "10000.00";

    private TradeBarAdapter mTradeBuyAdapter;
    private TradeBarAdapter mTradeSellAdapter;
    private TradeOrderAdapter mTradeOrderAdapter;

    private Entrust mTradeEntrust = new Entrust();
    private DecimalFormat mFormat = new DecimalFormat("#.########");

    @Override
    protected void initIntent() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
            if (bundle != null) {
                mTradingPair = bundle.getString(TRADING_PAIR);
                mExchangeId = bundle.getString(EXCHANGE_ID);
                mMarketId = bundle.getString(MARKET_ID);
            }
        }
    }

    @Override
    protected TradeContract.Presenter getPresenter() {
        return new TradePresenter(this);
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    protected int getContentView() {
        return R.layout.market_activity_trade_new;
    }

    @Override
    protected void initView() {

        setRVConfig(mRvTradeBuy);
        setRVConfig(mRvTradeSell);
        setRVConfig(mRcvTradeContainer);

        mTvTradeMarketLeft.setText("BTC");
        mTvTradeMarketRight.setText("/".concat("USDT"));

        mTvTradeAvailable.setText(getString(R.string.market_trade_available, mBalance, "BTC"));

        mTradeBuyAdapter = getTradeBuyAdapter(mRvTradeBuy, false);
        mTradeSellAdapter = getTradeBuyAdapter(mRvTradeSell, true);

        mTradeBuyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                setPriceEt(mTradeBuyAdapter, position);
            }
        });

        mTradeSellAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                setPriceEt(mTradeSellAdapter, position);
            }
        });

        initOrderList();
        initTab();

        setListener();
    }

    private void setCommit() {
        if (UserInfoManager.getInstance().isLogin()) {
            mBtnTradeCommit.setText(type == 1 ? R.string.market_trade_sell : R.string.market_trade_buy);
        } else {
            mBtnTradeCommit.setText(R.string.market_order_login);
        }
    }

    private void setListener() {
        mEtTradeInputPrice.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                limitInputPrecision(s, true);
            }
        });

        mEtTradeInputNumber.addTextChangedListener(new SingleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                limitInputPrecision(s, false);
            }
        });
    }

    private void limitInputPrecision(Editable s, boolean isPrice) {
        try {
            String editableStr;
            String etStr;
            int numPrecision;
            if (isPrice) {
                etStr = mEtTradeInputNumber.getText().toString();
                numPrecision = pricePrecision;
            } else {
                etStr = mEtTradeInputPrice.getText().toString();
                numPrecision = volumePrecision;
            }

            TradeHelper.checkInput(s, numPrecision);
            if (s.toString().endsWith(".")) {
                editableStr = s.toString().substring(0, s.toString().length() - 1);
            } else {
                editableStr = s.toString();
            }

            if (etStr.endsWith(".")) {
                etStr = etStr.substring(0, etStr.length() - 1);
            }

            if (TextUtils.isEmpty(editableStr) || TextUtils.isEmpty(etStr)) {
                mTvTradeTranslation.setText("0");
            } else {
                String account = ArithUtil.mul(editableStr, etStr);
                String num = mFormat.format(Double.parseDouble(account));
                mTvTradeTranslation.setText(num);
            }
            mTradeEntrust.setTrades_count(mTvTradeTranslation.getText().toString());

            if (isPrice) {
                mTradeEntrust.setPrice(editableStr);
                if (!TextUtils.isEmpty(editableStr)) {
                    mTvTradeInputPriceEqual.setText(getString(R.string.market_trade_cny, ArithUtil.mul(editableStr, mDicNum + "", 2)));
                } else {
                    mTvTradeInputPriceEqual.setText("- -");
                }
            } else {
                mTradeEntrust.setAmounts(editableStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPriceEt(TradeBarAdapter adapter, int position) {
        CoinPair pair = adapter.getItem(position);
        if (pair == null) {
            return;
        }
        String price = pair.getPrice();
        if (!TextUtils.isEmpty(price)) {
            mTradeEntrust.setPrice(price);
            mCountNum = TradeHelper.getDecimalEndNum(mEtTradeInputPrice.getText().toString().trim());
            mEtTradeInputPrice.setText(price);
            mEtTradeInputPrice.setSelection(mEtTradeInputPrice.getText().length());
        }
    }

    private void setRVConfig(RecyclerView rlExchange) {
        rlExchange.setHasFixedSize(true);
        rlExchange.setNestedScrollingEnabled(false);
    }

    private void initTab() {
        mTabTradeChange.setTitleArr(new String[]{getString(R.string.market_order_buy_space),
                getString(R.string.market_order_sell_space)});
        mTabTradeChange.initUnderlinePosition(type);
        mTabTradeChange.setOnTabClickListener(new CustomTabLayout.OnTabClickListener() {
            @Override
            public void tabClick(int position, String str) {
                switch (position) {
                    case 0:
                        changeTab(0);
                        break;
                    case 1:
                        changeTab(1);
                        break;
                }
            }
        });
        changeTab(type);
    }

    private void changeTab(int type) {
        this.type = type;
        mTabTradeChange.setUnderlineColor(
                mContext.getResources().getColor(type == 0 ?
                        R.color.color_green_dark : R.color.color_red_dark));
        buySellOperation(type);
    }

    private void buySellOperation(int type) {
        if (type == 0) {
            mBtnTradeCommit.setBackgroundResource(R.drawable.common_corner_green_green_selector);
        } else {
            mBtnTradeCommit.setBackgroundResource(R.drawable.common_corner_red_red_selector);
        }
        setCommit();
    }

    private void initOrderList() {
        mRcvTradeContainer.setNestedScrollingEnabled(false);
        mRcvTradeContainer.addItemDecoration(
                new CustomRecyclerViewDivider(DensityUtil.dp2px(0.5f), dividerColor, 0));
        mTradeOrderAdapter = new TradeOrderAdapter(mContext);
        mRcvTradeContainer.setLayoutManager(new TradeRcvLayoutManager(getContext()));
        mRcvTradeContainer.setAdapter(mTradeOrderAdapter);
        mTradeOrderAdapter.setOnCancelOrderItemListener(new TradeOrderAdapter.OnCancelOrderItemListener() {
            @Override
            public void onCancelOrder(EntrustsOrder.ItemsBean bean) {
                mPresenter.entrustCancel(bean.getId());
            }
        });
    }

    private TradeBarAdapter getTradeBuyAdapter(RecyclerView recycler, boolean isSell) {
        TradeBarAdapter tradeBarAdapter = new TradeBarAdapter(this);
        recycler.setLayoutManager(new TradeRcvLayoutManager(this));
        tradeBarAdapter.setSell(isSell);
        recycler.setAdapter(tradeBarAdapter);
        return tradeBarAdapter;
    }

    @Override
    protected void initData() {
        mPresenter.getTradeList();
        mPresenter.entrustsList(1, null, null);
    }


    @OnClick({R2.id.btn_trade_back, R2.id.btn_trade_title, R2.id.btn_trade_right,
            R2.id.btn_trade_1_4, R2.id.btn_trade_1_2, R2.id.btn_trade_3_4,
            R2.id.btn_trade_all, R2.id.btn_trade_commit, R2.id.btn_trade_reduce,
            R2.id.btn_trade_add})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_trade_back) {
            finish();
        } else if (i == R.id.btn_trade_title) {
            showMarket();
        } else if (i == R.id.btn_trade_right) {
            ARouter.getInstance().build(MarketArouterConstant.MARKET_ORDER_HISTORY).navigation(this);
        } else if (i == R.id.btn_trade_1_4) {
            resetPercentTv("0.25");
        } else if (i == R.id.btn_trade_1_2) {
            resetPercentTv("0.5");
        } else if (i == R.id.btn_trade_3_4) {
            resetPercentTv("0.75");
        } else if (i == R.id.btn_trade_all) {
            resetPercentTv("1.0");
        } else if (i == R.id.btn_trade_commit) {
            trade();
        } else if (i == R.id.btn_trade_reduce) {
            reduce();
        } else if (i == R.id.btn_trade_add) {
            add();
        }
    }

    private void showMarket() {
        MarketFactory factory = new MarketFactory();
        IMarketDialog dialog = factory.createMarketDialog(mContext);
        dialog.setOnItemClick(new OnMarketItemClickListener() {
            @Override
            public void onItemClick(String marketId) {

            }

            @Override
            public void onDismiss() {
                mViewTradePopBg.setVisibility(View.GONE);
            }
        });
        dialog.showMarketDialog(getSupportFragmentManager(), mRlTradeTitle, TradeActivity1.class.getSimpleName());
        mViewTradePopBg.setVisibility(View.VISIBLE);
    }

    private void add() {
        mCountNum = TradeHelper.getDecimalEndNum(mEtTradeInputPrice.getText().toString().trim());
        if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
            mTradeEntrust.setPrice(ArithUtil.add(mTradeEntrust.getPrice(), mCountNum));
            mEtTradeInputPrice.setText(mTradeEntrust.getPrice());
            mEtTradeInputPrice.setSelection(mEtTradeInputPrice.getText().length());
        }
    }

    private void reduce() {
        mCountNum = TradeHelper.getDecimalEndNum(mEtTradeInputPrice.getText().toString().trim());
        if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
            mTradeEntrust.setPrice(ArithUtil.sub(mTradeEntrust.getPrice(), mCountNum));
            if (Double.parseDouble(mTradeEntrust.getPrice()) < 0) {
                mTradeEntrust.setPrice("0");
            }
            mEtTradeInputPrice.setText(mTradeEntrust.getPrice());
            mEtTradeInputPrice.setSelection(mEtTradeInputPrice.getText().length());
        }

    }

    public void trade() {
        if (!UserInfoManager.getInstance().isLogin()) {
            ARouter.getInstance().build(AccountArouterConstant.ACCOUNT_LOGIN).navigation(this);
            return;
        }
        String price = mEtTradeInputPrice.getText().toString();
        String volume = mEtTradeInputNumber.getText().toString();
        if (TextUtils.isEmpty(price)) {
            showLongToast(getString(R.string.market_order_input_price));
            return;
        }
        if (TextUtils.isEmpty(volume)) {
            showLongToast(getString(R.string.market_order_input_number));
            return;
        }
        mPresenter.entrusts(new EntrustReq(mMarketId, type == 0 ? "buy" : "sell",
                price, volume, 0));
    }

    @Override
    public void resultEntrustMock(List<EntrustsOrder.ItemsBean> list) {
        mTradeOrderAdapter.updateList(list);
    }

    @Override
    public void resultTrades(List<CoinPair> topList, List<CoinPair> botList) {
        updateTrade(topList, botList);
    }

    @Override
    public void resultEntrust(Object object) {

    }

    @Override
    public void resultEntrustList(List<EntrustsOrder.ItemsBean> items) {
        mTradeOrderAdapter.updateList(items);
    }

    @Override
    public void resultEntrustCancel(Object object) {
        mPresenter.entrustsList(1, null, null);
    }

    @Override
    public void resultCoinAssert(CoinAssetsBean bean) {
        if (bean == null) {
            return;
        }
    }

    public void updateTrade(List<CoinPair> topList, List<CoinPair> botList) {
        mTradeBuyAdapter.updateList(topList);
        mTradeSellAdapter.updateList(botList);
    }


    private void resetPercentTv(String percentStr) {
        String availableCount = mBalance;
        String price = mEtTradeInputPrice.getText().toString();
        if (TextUtils.isEmpty(availableCount)) {
            showLongToast(getString(R.string.market_trade_getting_balance));
            return;
        }
        if (TextUtils.isEmpty(mBalance) || mBalance.matches("[0-0,.]{1,}")) {
            showLongToast(getString(R.string.market_trade_not_get_balance));
            return;
        }
        String amount = TradeHelper.getAmount(availableCount, price, type == 0 ? "buy" : "sell", percentStr);
        mEtTradeInputNumber.setText(amount);
        mEtTradeInputNumber.setSelection(mEtTradeInputNumber.getText().length());
    }

}
