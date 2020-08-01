package com.wanshare.common.widget.viewpager;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wanshare.common.R;

public class ViewPagerIndicator extends LinearLayout {

    /***黑色字体***/
    private static final int COLOR_TEXT_NORMAL = 0xFF333333;
    //    /***金色字体***/
    private static final int COLOR_TEXT_SELECTED = 0xFFc8a66b;
    /*** 默认字体大小 单位px ***/
    private static final int DEFAULT_TEXT_SIZE = 60;
    /*** 线的默认颜色 ***/
    private static final int DEFAULT_INDICATOR_COLOR = 0xFFc8a66b;
    /*** 线的默认高度 单位px ***/
    private static final int DEFAULT_INDICATOR_HEIGHT = 3;
    /***金色导航条***/
    private int mIndicatorColor = DEFAULT_INDICATOR_COLOR;
    /***线的宽度***/
    private float mIndicatorWidth;
    /***线的高度***/
    private float mIndicatorHeight = DEFAULT_INDICATOR_HEIGHT;

    /***tab文字与线之间的间距***/
    private int mIndicatorPaddingBottom;

    /***字体大小 px ***/
    private float mTextSize = DEFAULT_TEXT_SIZE;

    /***未选中字体颜色***/
    @ColorInt
    private int normalTextColor = COLOR_TEXT_NORMAL;

    private int mTabWidth;
    @ColorInt
    private int mSelectedTextColor = 0xFFc8a66b;

    private String[] mTitles;
    private int mTabCount;
    private float mTranslationX;
    private Paint mPaint = new Paint();

    private ViewPager viewPager;


    private onTabChangeListener mListener;
    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            ViewPagerIndicator.this.scroll(position, positionOffset);
        }

        @Override
        public void onPageSelected(int position) {
            ViewPagerIndicator.this.scroll(position, 0);
            ViewPagerIndicator.this.setTitleViewColor(mSelectedTextColor, position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private float startX;
    private boolean smoothScroll=false;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
            parseAttrs(typedArray);
            typedArray.recycle();
        }
        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(mIndicatorHeight);
    }

    private void parseAttrs(TypedArray ta) {
        mTextSize = ta.getDimension(R.styleable.ViewPagerIndicator_tabTextSize, DEFAULT_TEXT_SIZE);
        normalTextColor = ta.getColor(R.styleable.ViewPagerIndicator_nomalTextColor, COLOR_TEXT_NORMAL);
        mSelectedTextColor = ta.getColor(R.styleable.ViewPagerIndicator_selectedTextColor, COLOR_TEXT_SELECTED);
        mIndicatorColor = ta.getColor(R.styleable.ViewPagerIndicator_indicatorColor, DEFAULT_INDICATOR_COLOR);
        mIndicatorHeight = ta.getDimension(R.styleable.ViewPagerIndicator_indicatorHeight, DEFAULT_INDICATOR_HEIGHT);
        mIndicatorWidth = ta.getDimension(R.styleable.ViewPagerIndicator_indicatorWidth, 0);
        mIndicatorPaddingBottom = (int) ta.getDimension(R.styleable.ViewPagerIndicator_indicatorPaddingBottom, 0);
    }

    public void setOnTabChangeListener(onTabChangeListener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mTabCount != 0) {
            mTabWidth = w / mTabCount;
            if (mTabWidth >= 0 && (mIndicatorWidth <= 0 || mIndicatorWidth > mTabWidth)) {
                mIndicatorWidth = mTabWidth;
            }

            startX = (mTabWidth - mIndicatorWidth) / 2;
            if (startX < 0) {
                startX = 0;
            }
        }


    }

    public void setTitles(String[] titles, ViewPager viewPager) {
        mTitles = titles;
        mTabCount = titles.length;
        this.viewPager = viewPager;
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(listener);
        }
        generateTitleView();
    }

    public void setTitles(String[] titles) {
        setTitles(titles, null);
    }


    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.translate(mTranslationX, getHeight() - 2);

        canvas.drawLine(startX, 0, startX + mIndicatorWidth, 0, mPaint);
        canvas.restore();
    }

    /**
     * <br> Description: dip转px
     * <br> Author:      zhongweijie
     * <br> Date:        2017/8/3 14:06
     */
    private int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public void scroll(int position, float offset) {
        /**
         * <pre>
         *  0-1:position=0 ;1-0:postion=0;
         * </pre>
         */
        mTranslationX = getWidth() / mTabCount * (position + offset);
        invalidate();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void generateTitleView() {
        if (getChildCount() > 0)
            this.removeAllViews();
        int count = mTitles.length;
        setWeightSum(count);
        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0,
                    LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setGravity(Gravity.CENTER);
            if (i == 0) {
                tv.setTextColor(mSelectedTextColor);
            } else {
                tv.setTextColor(normalTextColor);
            }
            tv.setText(mTitles[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            tv.setLayoutParams(lp);
            tv.setPadding(0,0,0,mIndicatorPaddingBottom);
            final int selectedPosition = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!ViewPagerIndicator.this.isEnabled()){
                        return;
                    }

                    if (viewPager != null && mTitles != null && selectedPosition >= 0 && selectedPosition < mTitles.length) {
                        viewPager.setCurrentItem(selectedPosition,smoothScroll);
                    }

                    ViewPagerIndicator.this.scroll(selectedPosition, 0);
                    ViewPagerIndicator.this.setTitleViewColor(mSelectedTextColor, selectedPosition);
                    if (mListener != null) {
                        mListener.onTabSelected(selectedPosition);
                    }
                }
            });
            addView(tv);
        }
    }

    public void setTitleViewColor(int color, int index) {
        for (int i = 0; i < mTabCount; i++) {
            if (i == index) {
                TextView childAt = (TextView) getChildAt(i);
                childAt.setTextColor(color);
            } else {
                TextView childAt = (TextView) getChildAt(i);
                childAt.setTextColor(normalTextColor);
            }
        }
    }

    public interface onTabChangeListener {
        void onTabSelected(int position);
    }
}
