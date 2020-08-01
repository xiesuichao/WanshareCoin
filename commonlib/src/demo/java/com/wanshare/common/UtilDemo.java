package com.wanshare.common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.base.CommonWebViewActivity;
import com.wanshare.common.listdemo.MultipleItemListActivity;
import com.wanshare.common.listdemo.NormalListActivity;
import com.wanshare.wscomponent.utils.DeviceUtil;
import com.wanshare.wscomponent.utils.NetworkUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/7/21.
 */

public class UtilDemo extends BaseActivity {

    @BindView(R2.id.text_width)
    TextView mTextWidth;
    @BindView(R2.id.text_height)
    TextView mTextHeight;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, UtilDemo.class));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(getString(R.string.test_util_demo));
        mMyToolbar.setRightButtonText(getString(R.string.test));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_demo_util;
    }

    @OnClick({R2.id.btn_short_toast, R2.id.btn_long_toast, R2.id.btn_fast_click, R2.id.btn_get_width,
            R2.id.btn_get_height, R2.id.btn_check_network, R2.id.btn_compute_test, R2.id.btn_list_test,
            R2.id.btn_mul_list,R2.id.btn_webview_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //短时吐司
            case R2.id.btn_short_toast:
                showShortToast("short_toast");
                break;
            //长时吐司
            case R2.id.btn_long_toast:
                showLongToast("long_toast");
                break;
            //防止快速点击
            case R2.id.btn_fast_click:
                if (!fastClick()) {
                    return;
                }
                //这里可以做点击事件处理

                break;
            //获取屏幕宽度
            case R2.id.btn_get_width:
                int width = DeviceUtil.deviceWidth(mContext);
                mTextWidth.setVisibility(View.VISIBLE);
                mTextWidth.setText(String.valueOf(width));
                break;
            //获取屏幕高度
            case R2.id.btn_get_height:
                int height = DeviceUtil.deviceHeight(mContext);
                mTextHeight.setVisibility(View.VISIBLE);
                mTextHeight.setText(String.valueOf(height));
                break;
            //检测网络的工具方法
            //可以断开WiFi和移动数据网络进行测试
            case R2.id.btn_check_network:
                if (!NetworkUtils.isNetworkAvailable(mContext)) {
                    showShortToast("当前网络不可用，请检查网络");
                } else {
                    showShortToast("网络连接良好");
                }
                break;
            //跳转测试精确计算
            case R2.id.btn_compute_test:
                ComputeTestDemo.startActivity(mContext);
                break;
            case R2.id.btn_list_test:
                NormalListActivity.startActivity(mContext);
                break;
            case R2.id.btn_mul_list:
                MultipleItemListActivity.startActivity(mContext);
                break;
            case R2.id.btn_webview_test:
//                CommonWebViewActivity.startActivity(mContext,"https://www.baidu.com/");
                break;
        }
    }


    @Override
    protected void onRightButton(View view) {
        showShortToast("测试");
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }
}
