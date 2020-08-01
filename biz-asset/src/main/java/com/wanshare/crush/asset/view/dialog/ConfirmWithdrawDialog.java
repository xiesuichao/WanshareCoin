package com.wanshare.crush.asset.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wanshare.crush.asset.R;

/**
 * 提现确认对话框
 *
 * @author wangdunwei
 * @date 2018/5/3
 */
public class ConfirmWithdrawDialog extends AlertDialog implements View.OnClickListener {

    ImageView mIvClose;
    TextView mTvCurrency;
    TextView mTvAddress;
    TextView mTvCount;
    Button mBtnSubmit;

    private String coin, address, amount;

    private OnConfirmClickListener mOnConfirmClickListener;

    public ConfirmWithdrawDialog(@NonNull Context context) {
        this(context, 0);
    }

    public ConfirmWithdrawDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
    }

    protected int getContentView() {
        return R.layout.asset_dialog_confirm_withdraw;
    }

    protected void initView() {
        setCanceledOnTouchOutside(false);
        mIvClose = findViewById(R.id.iv_close);
        mTvCurrency = findViewById(R.id.tv_currency);
        mTvAddress = findViewById(R.id.tv_address);
        mTvCount = findViewById(R.id.tv_count);
        mBtnSubmit = findViewById(R.id.btn_submit);

        mIvClose.setOnClickListener(this);
        mBtnSubmit.setOnClickListener(this);

        mTvCurrency.setText(coin);
        mTvAddress.setText(address);
        mTvCount.setText(amount);
    }

    public void setCoin(String coin) {
        this.coin = coin;
        if (mTvCurrency != null && this.coin != null) {
            mTvCurrency.setText(coin);
        }
    }

    public void setAddress(String address) {
        this.address = address;
        if (mTvAddress != null && this.address != null) {
            mTvAddress.setText(address);
        }
    }

    public void setAmount(String amount) {
        this.amount = amount;
        if (mTvCount != null && this.amount != null) {
            mTvCount.setText(this.amount);
        }
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.mOnConfirmClickListener = onConfirmClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close) {
            dismiss();
        } else if (id == R.id.btn_submit) {
            dismiss();
            if (mOnConfirmClickListener != null) {
                mOnConfirmClickListener.onConfirmClick();
            }
        }
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }


}
