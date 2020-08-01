package com.wanshare.crush.account.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.wanshare.crush.account.R;

public class CardTypeDialog extends Dialog implements View.OnClickListener{


    public interface OnItemCardTypeListener{
        void onSelect(int type, String card);
    }

    private OnItemCardTypeListener mOnItemCardTypeListener;

    public void setOnItemCardTypeListener(OnItemCardTypeListener onItemCardTypeListener) {
        mOnItemCardTypeListener = onItemCardTypeListener;
    }

    public CardTypeDialog(@NonNull Context context) {
        super(context, R.style.common_sheet_dialog_Style);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_dialog_card_type);
        Window window = getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        findViewById(R.id.btn_card_type_id).setOnClickListener(this);
        findViewById(R.id.btn_card_type_passport).setOnClickListener(this);
        findViewById(R.id.btn_card_type_driver).setOnClickListener(this);
        findViewById(R.id.btn_card_type_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_card_type_id){
            select(1, getContext().getString(R.string.account_author_id_type_card));
        } else if (v.getId() == R.id.btn_card_type_passport){
            select(2, getContext().getString(R.string.account_author_id_type_passport));
        } else if (v.getId() == R.id.btn_card_type_driver){
            select(3, getContext().getString(R.string.account_author_id_type_driver));
        } else if (v.getId() == R.id.btn_card_type_cancel){
            dismiss();
        }
    }

    private void select(int type, String card){
        if (mOnItemCardTypeListener != null) {
            mOnItemCardTypeListener.onSelect(type, card);
        }
        dismiss();
    }
}
