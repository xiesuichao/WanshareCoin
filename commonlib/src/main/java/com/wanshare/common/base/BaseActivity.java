package com.wanshare.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;
import com.wanshare.common.R;
import com.wanshare.common.biz.setting.SettingInfoManager;
import com.wanshare.common.mvp.BaseMVPActivity;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.wscomponent.dialog.CommonProgressDialog;
import com.wanshare.wscomponent.logger.LogUtil;
import com.wanshare.wscomponent.toolbar.MyToolbar;
import com.wanshare.wscomponent.utils.StatusBarUtil;
import com.wanshare.wscomponent.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Created by Jason on 2018/7/21.
 * <p>
 * Activity的基类，所有Activity必须继承此Activity
 */

public abstract class BaseActivity<P extends IPresenter> extends BaseMVPActivity<P> implements IView {

    protected Context mContext;
    private RelativeLayout mLlBasetitleRoot;
    protected MyToolbar mMyToolbar;
    private CommonProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //初始化状态栏
        initStatus();
        super.onCreate(savedInstanceState);
        if (showTitle()) {
            setContentView(R.layout.activity_base);
            findView();
            addView();
            initTitleButtonClick();
        } else {
            setContentView(getContentView());
        }
        mContext = this;
        ButterKnife.bind(this);
        initIntent();
        initView();
        initData();
    }

    protected void initIntent() {

    }

    protected Bundle getArguments() {
        return getIntent() == null ? null : getIntent().getExtras();
    }

    public Activity getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }

    /**
     * 初始化状态栏
     */
    protected void initStatus() {
        boolean nightMode = SettingInfoManager.isNightMode();
        StatusBarUtil.transparencyBar(this);
        if (!nightMode) {
            StatusBarUtil.StatusBarLightMode(this);
        }
    }


    private void findView() {
        mLlBasetitleRoot = (RelativeLayout) findViewById(R.id.ll_basetitle_root);
        mMyToolbar = (MyToolbar) findViewById(R.id.my_toolbar);
    }

    /**
     * 添加标题栏和activity界面视图
     */
    private void addView() {
        View view = getLayoutInflater().inflate(getContentView(), null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.BELOW, R.id.rl_title_container);
        if (null != mLlBasetitleRoot) {
            mLlBasetitleRoot.addView(view, lp);
        }
    }

    /**
     * 初始化标题栏左右两边按钮点击事件
     */
    private void initTitleButtonClick() {
        if (null != mMyToolbar) {
            //返回按钮点击事件处理
            mMyToolbar.setOnBackButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackButton(view);
                }
            });

            //标题栏右边按钮点击事件处理
            mMyToolbar.setOnRightButtonTextClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightButton(view);
                }
            });
        }
    }

    /**
     * 返回按钮点击事件
     *
     * @param view
     */
    protected void onBackButton(View view) {
        finish();
    }

    /**
     * 右边按钮点击事件处理
     *
     * @param view
     */
    protected void onRightButton(View view) {

    }

    /**
     * 防止快速点击
     */
    private long lastClick = 0;

    protected boolean fastClick() {
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }

    /**
     * 设置是否展示标题栏
     *
     * @return true 展示 false 不展示
     */
    protected boolean showTitle() {
        return true;
    }

    /**
     * 初始化EvenBus
     */
    protected void initEvent() {
        EventBus.getDefault().register(this);
    }

    /**
     * 取消注册EvenBus
     */
    protected void unRegister() {
        if (isRegister()) {
            EventBus.getDefault().unregister(this);
        }
    }

    private boolean isRegister() {
        return EventBus.getDefault().isRegistered(this);
    }

    /**
     * 短时toast
     *
     * @param text
     */
    protected void showShortToast(String text) {
        ToastUtil.showShort(mContext, text);
    }

    /**
     * 长时toast
     *
     * @param text
     */
    protected void showLongToast(String text) {
        ToastUtil.showLong(mContext, text);
    }

    @Override
    public void showLoading(String tips) {
        if (mProgressDialog == null) {
            mProgressDialog = new CommonProgressDialog(this, R.style.my_progress_dialog_theme);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMsg(tips);
        }
        mProgressDialog.show();
    }

    public void showLoading() {
        showLoading("");
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showHintMessage(@NonNull String message) {
        showShortToast(message);
    }

    /**
     * 如果activity下有使用到fragment，需要在activity的入口处调用此方法，目的是去除重复统计
     */
    protected void deletActivityAgent() {
        MobclickAgent.openActivityDurationTrack(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟基础指标统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟基础指标统计
        MobclickAgent.onPause(this);
    }

    @Override
    protected P getPresenter() {
        return null;
    }

    protected abstract int getContentView();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegister();
        try {
            fixInputMethod(this);
        } catch (Exception e) {
            LogUtil.d("" + e.getMessage());
        }
    }

    /**
     * 解决输入法导致的内存泄漏
     *
     * @param context
     */
    public static void fixInputMethod(Context context) {
        if (context == null) {
            return;
        }
        InputMethodManager inputMethodManager = null;
        try {
            inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (inputMethodManager == null) {
            return;
        }
        Field[] declaredFields = inputMethodManager.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            try {
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                Object obj = declaredField.get(inputMethodManager);
                if (obj == null || !(obj instanceof View)) {
                    continue;
                }
                View view = (View) obj;
                if (view.getContext() == context) {
                    declaredField.set(inputMethodManager, null);
                } else {
                    return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

}
