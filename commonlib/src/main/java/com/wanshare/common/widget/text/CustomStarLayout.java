package com.wanshare.common.widget.text;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wanshare.common.R;

/**
 * 评分星级layout
 * Created by xiesuichao on 2018/8/20.
 */

public class CustomStarLayout extends LinearLayout {

    private LinearLayout mLinearLayout;
    private LayoutParams mParams;
    private Context mContext;

    public CustomStarLayout(Context context) {
        this(context, null);
    }

    public CustomStarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomStarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.common_layout_custom_stars, this, true);
        mLinearLayout = findViewById(R.id.ll_common_star_layout);
        mParams = new LayoutParams(dp2px(context, 10), dp2px(context, 10));
        mContext = context;
    }

    public void setScore(float starNum) {
        int integerNum = (int) starNum;
        float decimalNum = starNum - integerNum;

        for (int i = 0; i < 5; i++) {
            ImageView starIv = new ImageView(mContext);
            if (i != 4) {
                mParams.rightMargin = dp2px(mContext, 2);
            }
            if (i < integerNum) {
                starIv.setImageResource(R.drawable.ic_home_xinixnq);
            } else if (i == integerNum && decimalNum >= 0.5) {
                starIv.setImageResource(R.drawable.ic_home_xinxinb);
            } else {
                starIv.setImageResource(R.drawable.ic_home_xinxink);
            }
            mLinearLayout.addView(starIv, mParams);
        }
    }

    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
