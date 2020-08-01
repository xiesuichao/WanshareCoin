package com.wanshare.crush.asset.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;

import com.wanshare.common.widget.NumberPickerView;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author wangdunwei
 * @date 2018/8/21
 */
public class SelectCoinDialog extends Dialog {
    @BindView(R2.id.number_picker_view)
    NumberPickerView numberPickerView;
    @BindView(R2.id.btnCancel)
    Button btnCancel;

    String[] coinArray;

    public SelectCoinDialog(@NonNull Context context, String[] availableCoinArray) {
        super(context, R.style.TransparentDialog);
        coinArray = availableCoinArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asset_dialog_select_coin);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        numberPickerView.setDisplayedValues(coinArray);
        numberPickerView.setMinValue(1);
        numberPickerView.setMaxValue(coinArray.length);
    }

    /**
     * 设置数字选择控件值变化的监听器
     */
    public void setOnValueChangeRelativeToRaw(NumberPickerView.OnValueChangeListenerRelativeToRaw l) {
        numberPickerView.setOnValueChangedListenerRelativeToRaw(l);
    }

    @OnClick(R2.id.btnCancel)
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void show() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;
        super.show();
    }
}
