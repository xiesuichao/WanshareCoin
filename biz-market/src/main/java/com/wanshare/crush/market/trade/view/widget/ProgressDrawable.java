package com.wanshare.crush.market.trade.view.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * 自定义进度条drawable
 * </br>
 * Date: 2018/9/26 15:52
 *
 * @author hemin
 */
public class ProgressDrawable extends Drawable {

    // 进度条方向
    // 顺时针.
    public static final int PROGRESS_ORIENTATION_LEFT_TO_RIGHT = 0x00;
    // 逆时针.
    public static final int PROGRESS_ORIENTATION_RIGHT_TO_LEFT = 0x01;

    private Paint mPaint;

    float mPercent = 0;
    private int mOrientation = PROGRESS_ORIENTATION_LEFT_TO_RIGHT;

    public ProgressDrawable() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        // 设置抗锯齿
        mPaint.setAntiAlias(true);
    }

    public ProgressDrawable(int color) {
        this(color, PROGRESS_ORIENTATION_LEFT_TO_RIGHT);
    }

    public ProgressDrawable(int color, int oritation) {
        this();
        // 设置颜色
        mPaint.setColor(color);
        this.mOrientation = oritation;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        // 获取尺寸
        final Rect bounds = getBounds();

        if (PROGRESS_ORIENTATION_LEFT_TO_RIGHT == mOrientation) {
            canvas.drawRect(bounds.left, bounds.top, mPercent * bounds.right, bounds.bottom, mPaint);
        } else {
            canvas.drawRect((1 - mPercent) * (bounds.right - bounds.left), bounds.top, bounds.right, bounds.bottom, mPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }

    @Override
    public int getIntrinsicHeight() {
        return getBounds().height();
    }

    @Override
    public int getIntrinsicWidth() {
        return getBounds().width();
    }

    public void setOrientation(int orientation) {
        if (orientation == PROGRESS_ORIENTATION_LEFT_TO_RIGHT || orientation == PROGRESS_ORIENTATION_RIGHT_TO_LEFT) {
            mOrientation = orientation;
        } else {
            throw new RuntimeException("Orientation value illegal !");
        }
    }

    public void setPercent(float percent) {
        this.mPercent = percent;
        if (this.mPercent <= 0) {
            this.mPercent = 0;
        }
        invalidateSelf();
    }

}
