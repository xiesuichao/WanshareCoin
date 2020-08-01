package com.wanshare.crush.market.trade.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanshare.crush.market.R;
import com.wanshare.crush.market.R2;
import com.wanshare.crush.market.trade.model.bean.Entrust;
import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.DensityUtil;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 快捷交易弹出框
 */

public class TradeDialog extends Dialog {


    @BindView(R2.id.btn_trade_dialog_reduce)
    RelativeLayout mBtnTradeDialogReduce;
    @BindView(R2.id.et_trade_dialog_input_price)
    EditText mEtTradeDialogInputPrice;
    @BindView(R2.id.btn_trade_dialog_add)
    RelativeLayout mBtnTradeDialogAdd;
    @BindView(R2.id.et_trade_dialog_input_number)
    EditText mEtTradeDialogInputNumber;
    @BindView(R2.id.tv_trade_dialog_available)
    TextView mTvTradeDialogAvailable;
    @BindView(R2.id.tv_trade_dialog_unit)
    TextView mTvTradeDialogUnit;
    @BindView(R2.id.btn_trade_dialog_25)
    TextView mBtnTradeDialog25;
    @BindView(R2.id.btn_trade_dialog_50)
    TextView mBtnTradeDialog50;
    @BindView(R2.id.btn_trade_dialog_75)
    TextView mBtnTradeDialog75;
    @BindView(R2.id.btn_trade_dialog_100)
    TextView mBtnTradeDialog100;
    @BindView(R2.id.btn_trade_dialog_commit)
    Button mBtnTradeDialogCommit;

    //0:买入 1:卖出
    private int type;
    private String mCountNum;
    private String mPrice;
    private String mMarketId;
    private Entrust mTradeEntrust = new Entrust();

    public TradeDialog(@NonNull Context context, int type, String marketId, String price) {
        super(context, R.style.common_dialog_theme);
        this.type = type;
        mMarketId = marketId;
        mPrice = price;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.market_dialog_trade, null);
        ButterKnife.bind(this, view);
        setContentView(view);

        Window window = getWindow();
        window.setLayout(DensityUtil.dip2px(getContext(), 255), DensityUtil.dip2px(getContext(), 280));

        buySellOperation(type);
    }

    @OnClick({R2.id.btn_trade_dialog_reduce, R2.id.btn_trade_dialog_add,
            R2.id.btn_trade_dialog_25, R2.id.btn_trade_dialog_50,
            R2.id.btn_trade_dialog_75, R2.id.btn_trade_dialog_100,
            R2.id.btn_trade_dialog_commit})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_trade_dialog_reduce) {
            reduce();
        } else if (i == R.id.btn_trade_dialog_add) {
            add();
        } else if (i == R.id.btn_trade_dialog_25) {
            resetPercentTv("0.25");
        } else if (i == R.id.btn_trade_dialog_50) {
            resetPercentTv("0.50");
        } else if (i == R.id.btn_trade_dialog_75) {
            resetPercentTv("0.75");
        } else if (i == R.id.btn_trade_dialog_100) {
            resetPercentTv("1.0");
        } else if (i == R.id.btn_trade_dialog_commit) {
        }
    }

    private void reduce() {
        mCountNum = TradeHelper.getDecimalEndNum(mEtTradeDialogInputPrice.getText().toString().trim());
        if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
            mTradeEntrust.setPrice(ArithUtil.sub(mTradeEntrust.getPrice(), mCountNum));
            if (Double.parseDouble(mTradeEntrust.getPrice()) < 0) {
                mTradeEntrust.setPrice("0");
            }
            mEtTradeDialogInputPrice.setText(mTradeEntrust.getPrice());
            mEtTradeDialogInputPrice.setSelection(mEtTradeDialogInputPrice.getText().length());
        }
    }

    private void add() {
        mCountNum = TradeHelper.getDecimalEndNum(mEtTradeDialogInputPrice.getText().toString().trim());
        if (!TextUtils.isEmpty(mTradeEntrust.getPrice())) {
            mTradeEntrust.setPrice(ArithUtil.add(mTradeEntrust.getPrice(), mCountNum));
            mEtTradeDialogInputPrice.setText(mTradeEntrust.getPrice());
            mEtTradeDialogInputPrice.setSelection(mEtTradeDialogInputPrice.getText().length());
        }
    }

    private void buySellOperation(int type) {
        if (type == 0) {
            mBtnTradeDialogCommit.setBackgroundResource(R.drawable.common_corner_green_green_selector);
            mBtnTradeDialogCommit.setText(R.string.market_trade_buy);
        } else {
            mBtnTradeDialogCommit.setBackgroundResource(R.drawable.common_corner_red_red_selector);
            mBtnTradeDialogCommit.setText(R.string.market_trade_sell);
        }
    }

    private void resetPercentTv(String percentStr) {

        mTvTradeDialogAvailable.setText("10000");
        String price = mEtTradeDialogInputPrice.getText().toString();
        String availableCount = mTvTradeDialogAvailable.getText().toString();
        if (TextUtils.isEmpty(availableCount)) {
            ToastUtil.showLong(getContext(), R.string.market_trade_getting_balance);
            return;
        }
        if (TextUtils.isEmpty(price) || price.matches("[0-0,.]{1,}")) {
            ToastUtil.showLong(getContext(), R.string.market_trade_not_get_balance);
            return;
        }
        String amount = TradeHelper.getAmount(availableCount, price, type == 0 ? "buy" : "sell", percentStr);
        mEtTradeDialogInputNumber.setText(amount);
        mEtTradeDialogInputNumber.setSelection(mEtTradeDialogInputNumber.getText().length());

    }
}
