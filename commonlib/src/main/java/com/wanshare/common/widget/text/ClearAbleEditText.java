package com.wanshare.common.widget.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wanshare.common.R;
import com.wanshare.wscomponent.utils.DensityUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 可点击清除按钮清除文本的EditText
 * 最新修改，修改字体Padding，请使用textPadding，textPaddingLeft, textPaddingRight, textPaddingTop, textPaddingBottom.
 *
 * @author wangdunwei
 * @date 2018/5/7
 */
public class ClearAbleEditText extends LinearLayout {

    private EditText inputEt;
    private ImageView clearIv;
    private ImageButton eyePwdIb;

    private TextWatcher textWatcher;
    private TextWatcher inputNameTextWatcher;
    private View underLine;

    private OnFocusChangeCustomListener mFocusChangeListener;

    public ClearAbleEditText(Context context) {
        this(context, null);
    }

    public ClearAbleEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearAbleEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public interface OnFocusChangeCustomListener {
        void focusChange(boolean hasFocus);
    }

    public void setOnFocusChangeCustomListener(OnFocusChangeCustomListener focusChangeListener) {
        this.mFocusChangeListener = focusChangeListener;
    }

    private void init(AttributeSet attrs) {
        View.inflate(getContext(), R.layout.layout_clearable_edittext, this);
        inputEt = findViewById(R.id.et_input);
        clearIv = findViewById(R.id.iv_clear);
        eyePwdIb = findViewById(R.id.ib_eye_pwd);
        underLine = findViewById(R.id.line);

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                clearIv.setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);
            }
        };
        clearIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEt.setText(null);
            }
        });

        if(attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ClearAbleEditText);
            setEnabled(ta.getBoolean(R.styleable.ClearAbleEditText_android_enabled, true));
            inputEt.setText(ta.getString(R.styleable.ClearAbleEditText_android_text));

            final int inputType = ta.getInt(R.styleable.ClearAbleEditText_android_inputType, InputType.TYPE_CLASS_TEXT);
            inputEt.setInputType(inputType);

            boolean eyePwd = ta.getBoolean(R.styleable.ClearAbleEditText_eye_pwd, false);
            if(eyePwd) {
                eyePwdIb.setVisibility(VISIBLE);
                eyePwdIb.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        eyePwdIb.setSelected(!eyePwdIb.isSelected());
                        inputEt.setTransformationMethod(
                                eyePwdIb.isSelected() ? HideReturnsTransformationMethod.getInstance() :
                                        PasswordTransformationMethod.getInstance());
                        inputEt.setSelection(inputEt.getText().length());
                    }
                });
            } else {
                eyePwdIb.setVisibility(GONE);
                inputEt.addTextChangedListener(textWatcher);
            }

            inputEt.setHint(ta.getString(R.styleable.ClearAbleEditText_android_hint));
            int color = ta.getColor(R.styleable.ClearAbleEditText_android_textColorHint, -1);
            if(color != -1) {
                inputEt.setHintTextColor(color);
            }

            inputEt.setBackgroundDrawable(ta.getDrawable(R.styleable.ClearAbleEditText_android_background));
            //输入长度限制
            int maxLength = ta.getInt(R.styleable.ClearAbleEditText_android_maxLength, -1);
            if(maxLength >= 0) {
                inputEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            } else {
                inputEt.setFilters(new InputFilter[0]);
            }

            int paddingTop, paddingRight, paddingLeft = 0, paddingBottom;
            int tempPadding = ta.getDimensionPixelSize(R.styleable.ClearAbleEditText_textPadding,
                    DensityUtil.dip2px(getContext(), -1));
            if(tempPadding != -1) {
                paddingBottom = paddingLeft = paddingRight = paddingTop = tempPadding;
            }

            tempPadding = ta.getDimensionPixelSize(R.styleable.ClearAbleEditText_textPaddingLeft, -1);
            if(tempPadding != -1) {
                paddingLeft = tempPadding;
            }
            paddingRight = ta.getDimensionPixelSize(R.styleable.ClearAbleEditText_textPaddingRight, -1);
            if(tempPadding != -1) {
                paddingRight = tempPadding;
            }
            paddingTop = ta.getDimensionPixelSize(R.styleable.ClearAbleEditText_textPaddingTop, -1);
            if(tempPadding != -1) {
                paddingTop = tempPadding;
            }
            paddingBottom = ta.getDimensionPixelSize(R.styleable.ClearAbleEditText_textPaddingBottom, -1);
            if(tempPadding != -1) {
                paddingBottom = tempPadding;
            }
            inputEt.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

            //输入内容限制
            final String digits = ta.getString(R.styleable.ClearAbleEditText_android_digits);
            if(digits != null) {
                inputEt.setKeyListener(new NumberKeyListener() {
                    @NonNull
                    @Override
                    protected char[] getAcceptedChars() {
                        return digits.toCharArray();
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                    }
                });
            }

            boolean inputName = ta.getBoolean(R.styleable.ClearAbleEditText_inputName, false);
            if(inputName) {
                addInputNameTextWatcher();
            }

            Drawable drawable = ta.getDrawable(R.styleable.ClearAbleEditText_android_drawableRight);
            if(drawable != null) {
                clearIv.setImageDrawable(drawable);
            }

            boolean underlineVisible = ta.getBoolean(R.styleable.ClearAbleEditText_underline_visible, true);
            underLine.setVisibility(underlineVisible ? VISIBLE : GONE);

            int underlineResId = ta.getResourceId(R.styleable.ClearAbleEditText_underline_bg, 0);
            if(underlineResId != 0) {
                underLine.setBackgroundResource(underlineResId);
            }

            ta.recycle();

            inputEt.setEnabled(isEnabled());
            clearIv.setEnabled(isEnabled());

        }

        inputEt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(underLine.getVisibility() == VISIBLE) {
                    underLine.setSelected(hasFocus);
                }
                if(mFocusChangeListener != null) {
                    mFocusChangeListener.focusChange(hasFocus);
                }
            }
        });
    }

    private void addInputNameTextWatcher() {
        if(inputNameTextWatcher == null) {
            inputNameTextWatcher = new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String editable = inputEt.getText().toString();
                    String str = chineseFilter(editable);
                    if(!editable.equals(str)) {
                        inputEt.setText(str);
                        //设置新的光标所在位置
                        inputEt.setSelection(str.length());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    clearIv.setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);
                }

            };
            inputEt.addTextChangedListener(inputNameTextWatcher);


        }
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        inputEt.setEnabled(isEnabled());
        clearIv.setEnabled(isEnabled());
    }

    public String getText() {
        return inputEt.getText().toString();
    }

    public void setText(String text) {
        inputEt.setText(text);
    }

    public void setText(@StringRes int id) {
        inputEt.setText(id);
    }

    public void setHint(String hint) {
        inputEt.setHint(hint);
    }

    public void setHint(@StringRes int id) {
        inputEt.setHint(id);
    }

    public void setDrawableRight(Drawable drawable) {
        clearIv.setImageDrawable(drawable);
    }

    public void setDrawableRight(@DrawableRes int id) {
        clearIv.setImageResource(id);
    }

    public void addTextChangedListener(TextWatcher watcher) {
        inputEt.addTextChangedListener(watcher);
    }

    public void setSelection(int index) {
        inputEt.setSelection(index);
    }

    public void setKeyListener(KeyListener input) {
        inputEt.setKeyListener(input);
    }

    public void setUnderLineVisible(boolean visible) {
        underLine.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public static String chineseFilter(String str) {
        try {
            String regEx = "[^\u4E00-\u9FA5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        } catch(Exception e) {
            return str;
        }

    }
}
