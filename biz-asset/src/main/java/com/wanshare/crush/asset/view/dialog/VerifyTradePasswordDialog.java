package com.wanshare.crush.asset.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.wanshare.common.widget.text.ClearAbleEditText;
import com.wanshare.crush.asset.R;
import com.wanshare.wscomponent.utils.KeyBoardUtils;
import com.wanshare.wscomponent.utils.ToastUtil;

import butterknife.OnClick;

/**
 * 提现确认对话框
 *
 * @author wangdunwei
 * @date 2018/5/3
 */
public class VerifyTradePasswordDialog extends Dialog implements View.OnClickListener {

    ClearAbleEditText mEtPassword;

    private OnConfirmClickListener mOnConfirmClickListener;

    public VerifyTradePasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initView();
    }

    protected int getContentView() {
        return R.layout.asset_dialog_verify_trade_password;
    }

    protected void initView() {
        setCanceledOnTouchOutside(false);
        mEtPassword = findViewById(R.id.et_password);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.iv_close).setOnClickListener(this);
    }

    public void setOnConfirmClickListener(OnConfirmClickListener onConfirmClickListener) {
        this.mOnConfirmClickListener = onConfirmClickListener;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_close) {
            KeyBoardUtils.closeKeybord(mEtPassword, getContext());
            dismiss();
        } else if (id == R.id.btn_confirm) {
            if (TextUtils.isEmpty(mEtPassword.getText()) || mEtPassword.getText().length() < 8) {
                ToastUtil.showShort(getContext(), R.string.asset_trade_password_length_error);
                return;
            }
            KeyBoardUtils.closeKeybord(mEtPassword, getContext());
            if (mOnConfirmClickListener != null) {
                mOnConfirmClickListener.onConfirmClick(mEtPassword.getText());
            }
        }
    }

    public String getPassword() {
        if (mEtPassword != null) {
            return mEtPassword.getText().trim();
        }

        return "";
    }

    @Override
    public void show() {
        KeyBoardUtils.openKeybord(mEtPassword, getContext());
        super.show();
    }

    public void clearPassword() {
        if (mEtPassword != null) {
            mEtPassword.setText("");
        }
    }

    public interface OnConfirmClickListener {
        void onConfirmClick(String password);
    }


}
