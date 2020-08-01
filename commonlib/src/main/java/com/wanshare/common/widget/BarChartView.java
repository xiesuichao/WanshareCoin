package com.wanshare.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wanshare.wscomponent.utils.ArithUtil;
import com.wanshare.wscomponent.utils.DensityUtil;

/**
 * Created by xiesuichao on 2018/5/2.
 */

public class BarChartView extends View {

    private String baseWidth = "10";
    private float centerRectHeight = 21f;
    private String leftRectCol = "#33FF486A";
    private String rightRectCol = "#3300BC9B";
    private Paint leftRectPaint;
    private String dataNum;
    private float centerX;
    private float centerY;

    private RectF leftRectF;

    public BarChartView(Context context) {
        super(context);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        leftRectPaint = new Paint();
        leftRectPaint.setAntiAlias(true);
        leftRectPaint.setColor(Color.parseColor(rightRectCol));

        leftRectF = new RectF();
        centerY = 0;
        dataNum = "0";
    }

    public void setData(String dataNum) {
        this.dataNum = dataNum;
        requestLayout();
    }

    public void changeBackgroundCol(boolean isChange){
        if (isChange){
            leftRectPaint.setColor(Color.parseColor(leftRectCol));
        }else {
            leftRectPaint.setColor(Color.parseColor(rightRectCol));
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX = getWidth() - getPaddingRight();

        leftRectF.set(centerX - DensityUtil.dip2px(getContext(), widthCalculate(dataNum)),
                centerY,
                centerX,
                centerY + DensityUtil.dip2px(getContext(), centerRectHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
    }

    private void drawRect(Canvas canvas) {
        canvas.drawRect(leftRectF, leftRectPaint);
    }

    private float widthCalculate(String num) {
        return Float.parseFloat(ArithUtil.mul(ArithUtil.mul(num, "1"), baseWidth));
    }


}
