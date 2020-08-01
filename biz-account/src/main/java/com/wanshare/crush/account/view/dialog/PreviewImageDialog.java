package com.wanshare.crush.account.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.wanshare.crush.account.R;

/**
 * @author Xu wenxiang
 * create at 2018/9/20
 * description: 样例对话框
 */
public class PreviewImageDialog extends Dialog {


    int resourceId = 0;

    ImageView mIvImage;


    public PreviewImageDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_dialog_preview_image);
        initView();
    }


    protected void initView() {
        mIvImage = findViewById(R.id.iv_image);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (resourceId != 0) {
            mIvImage.setImageResource(resourceId);
        }
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }


}
