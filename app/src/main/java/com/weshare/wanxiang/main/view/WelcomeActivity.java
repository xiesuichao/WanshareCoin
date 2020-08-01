package com.weshare.wanxiang.main.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.app.AppArouterConstant;
import com.weshare.wanxiang.R;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 欢迎页
 * Created by Venn on 2018/09/04
 */
@Route(path = AppArouterConstant.APP_WELCOME)
public class WelcomeActivity extends BaseActivity {
    @BindView(R.id.ll_welcome)
    LinearLayout mLlWelcome;

    private Handler mHandler = new WeakHandler(this);

    @Override
    protected int getContentView() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        mHandler.sendEmptyMessageDelayed(1,1500);
    }

    private static class WeakHandler extends Handler {

        WeakReference<WelcomeActivity> weakReference;

        public WeakHandler(WelcomeActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            WelcomeActivity activity = weakReference.get();
            if (activity == null) {
                return;
            }
            activity.enter();
        }
    }

    public void enter() {
        ARouter.getInstance().build(AppArouterConstant.APP_MAIN)
                .withTransition(R.anim.common_anim_activity_open_in, R.anim.common_anim_activity_open_out)
                .navigation(this);
        finish();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Process.killProcess(Process.myPid());
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mLlWelcome == null) {
            return;
        }
        mLlWelcome.setBackgroundResource(0);
    }
}
