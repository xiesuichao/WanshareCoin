package com.wanshare.crush.account.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import com.wanshare.crush.account.R;
import com.wanshare.wscomponent.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 联系人列表右侧字母索引控件.
 *
 * @author Ayvytr <a href="https://github.com/Ayvytr" target="_blank">'s GitHub</a>
 * @since 1.2.0
 */

public class QuickIndexView extends View {
    /**
     * 默认字母索引宽高
     */
    private static final int DEFAULT_WIDTH_DP = 50;

    /**
     * 绘制字母索引的paint
     */
    private Paint paint;

    @ColorInt
    private int textColor;

    /**
     * 索引文字尺寸
     */
    private int textSize;

    /**
     * 字母索引List
     */
    private List<String> indexList = new ArrayList<>(0);

    /**
     * 字母变化监听器
     * {@link OnLetterChangeListener}
     */
    private OnLetterChangeListener onLetterChangeListener;

    //索引文字重心，只有Top，Center，Center_Vertical有效
    private int gravity;

    //实际绘制字母索引对应的顶部和底部y轴坐标，不开放给外部.
    private int bottomTextY;
    private int topTextY;

    //字母索引文字上下间距值
    private int lineSpacing;

    public QuickIndexView(Context context) {
        this(context, null);
    }

    public QuickIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化，包括必要的 {@link #paint} 等变量初始化，xml属性获取等.
     *
     * @param attrs {@link AttributeSet}
     */
    private void init(AttributeSet attrs) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QuickIndexView);

        textColor = typedArray.getColor(R.styleable.QuickIndexView_android_textColor, 0x8A000000);
        textSize = typedArray.getDimensionPixelSize(R.styleable.QuickIndexView_android_textSize, -1);
        setBackgroundDrawable(typedArray.getDrawable(R.styleable.QuickIndexView_android_background));
        gravity = typedArray.getInt(R.styleable.QuickIndexView_android_gravity, Gravity.CENTER);
        lineSpacing = typedArray.getDimensionPixelSize(R.styleable.QuickIndexView_android_lineSpacingExtra, 0);

        CharSequence[] textArray = typedArray.getTextArray(R.styleable.QuickIndexView_indexArray);
        if(textArray != null) {
            indexList = new ArrayList<>();
            for(CharSequence charSequence : textArray) {
                indexList.add(charSequence.toString());
            }
        } else {
            setIndexArray(getResources().getStringArray(R.array.defaultQuickIndexViewLetters));
        }

        typedArray.recycle();
    }

    /**
     * 获取字体大小
     *
     * @return {@link #textSize}
     */
    public int getTextSize() {
        return textSize;
    }

    /**
     * 设置字体大小，如果字体大小和原来的相同，或者字体大小小于0，设置不生效.
     *
     * @param textSize 字体大小，px
     */
    public void setTextSize(int textSize) {
        if(this.textSize == textSize || textSize < 0) {
            return;
        }

        this.textSize = textSize;
        invalidate();
    }

    /**
     * 获取当前 indexList Gravity
     *
     * @return Gravity
     */
    public int getGravity() {
        return gravity;
    }

    /**
     * 设置字母索引重心,只有 Gravity.TOP, Gravity.CENTER, Gravity.CENTER_VERTICAL有效，也就是说只有靠上对齐或者靠中间对齐有效.
     *
     * @param gravity Gravity
     */
    public void setGravity(int gravity) {
        if(gravity != this.gravity) {
            if(gravity == Gravity.TOP || gravity == Gravity.CENTER || gravity == Gravity.CENTER_VERTICAL) {
                this.gravity = gravity;
                invalidate();
            }
        }
    }

    /**
     * 获取 {@link #indexList}
     */
    public List<String> getIndexList() {
        return indexList;
    }

    /**
     * 设置 {@link #indexList}
     *
     * @param indexList 要设置的字母索引列表
     */
    public void setIndexList(List<String> indexList) {
        if(indexList != null) {
            this.indexList = indexList;
            invalidate();
        }
    }

    /**
     * 清空字母索引.
     */
    public void clearIndexList() {
        indexList.clear();
        invalidate();
    }

    /**
     * 设置 {@link #indexList}
     *
     * @param letterArray 要设置的字母索引数组
     */
    public void setIndexArray(String[] letterArray) {
        if(letterArray == null || letterArray.length == 0) {
            return;
        }

        indexList.clear();
        indexList.addAll(Arrays.asList(letterArray));
        invalidate();
    }

    /**
     * 获取字体颜色 {@link #textColor}
     */
    public int getTextColor() {
        return textColor;
    }

    /**
     * 设置字体颜色 {@link #textColor}
     */
    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        invalidate();
    }

    /**
     * 只对宽度进行限制，尺寸默认为 {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT}
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.AT_MOST) {
            width = DensityUtil.dip2px(getContext(), DEFAULT_WIDTH_DP);
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calcMaxTextSize();
        calcMaxLineSpacing();

        paint.setColor(textColor);
        paint.setTextSize(textSize);

        int y = getPaddingTop();

        int x = getWidth() >> 1;
        int size = indexList.size();

        if(gravity == Gravity.CENTER || gravity == Gravity.CENTER_VERTICAL) {
            y = (getHeight() - getPaddingTop() - size * (textSize + lineSpacing)) >> 1 + getPaddingTop();
        }

        this.topTextY = y;
        y += lineSpacing >> 1;

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int halfLetterLength = textSize >> 1;
        int fontY = (int) (halfLetterLength - fontMetrics.top / 2 - fontMetrics.bottom / 2);

        for(int i = 0; i < size; i++) {
            canvas.drawText(indexList.get(i), x, y + fontY, paint);
            y += textSize;

            if(i == size - 1) {
                y += lineSpacing >> 1;
            } else {
                y += lineSpacing;
            }
        }

        this.bottomTextY = y;
    }

    /**
     * 校验修正文字上下间距，同理，间距太大，绘制出来没有意义
     */
    private void calcMaxLineSpacing() {
        if(indexList.isEmpty()) {
            return;
        }

        int maxLineSpacing = (getHeight() - getPaddingTop() - getPaddingBottom() - textSize * indexList
                .size()) / indexList.size();
        if(lineSpacing > maxLineSpacing) {
            lineSpacing = maxLineSpacing;
        }
    }

    /**
     * 校验修正每个Letter的支持的最大可绘制文字尺寸，因为动态设置时，如果设置的文字尺寸太大，被控件边界截断，绘制出来没有意义
     * onDraw时，如果文字大小为-1，修正为最大可绘制尺寸。
     */
    private void calcMaxTextSize() {
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int letterCount = getIndexList().size();
        if(letterCount == 0) {
            return;
        }

        int maxTextSize = Math.min(width, (getHeight() - getPaddingTop() - getPaddingBottom()) / letterCount);
        if(textSize == -1) {
            textSize = maxTextSize;
        }

        if(textSize > maxTextSize) {
            textSize = maxTextSize;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getY() < topTextY || event.getY() > bottomTextY) {
            return true;
        }

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE: {
                int index = (int) ((event.getY() - topTextY) / (textSize + lineSpacing));

                if(onLetterChangeListener != null) {
                    onLetterChangeListener.onLetterChange(index, indexList.get(index), this);
                }
            }
            break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        paint = null;
        super.onDetachedFromWindow();
    }


    /**
     * 获取字体上下间距值
     *
     * @return {@link #lineSpacing}
     */
    public int getLineSpacing() {
        return lineSpacing;
    }

    /**
     * 设置字体上下间距值
     *
     * @param lineSpacing 上下间距值，px
     */
    public void setLineSpacing(int lineSpacing) {
        if(lineSpacing >= 0) {
            this.lineSpacing = lineSpacing;
            invalidate();
        }
    }

    /**
     * 设置字母索引变化监听器
     *
     * @param onLetterChangeListener {@link OnLetterChangeListener}
     */
    public void setOnLetterChangeListener(OnLetterChangeListener onLetterChangeListener) {
        this.onLetterChangeListener = onLetterChangeListener;
    }

    /**
     * 字母索引变化监听器
     */
    public interface OnLetterChangeListener {
        /**
         * {@link #onTouchEvent(MotionEvent)} 触发时，调用此字母索引变化方法
         *
         * @param indexPosition  当前指向的position
         * @param indexText      当前指向的文本
         * @param quickIndexView {@link QuickIndexView}
         */
        void onLetterChange(int indexPosition, String indexText, QuickIndexView quickIndexView);
    }
}
