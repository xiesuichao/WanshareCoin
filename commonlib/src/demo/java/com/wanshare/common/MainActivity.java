package com.wanshare.common;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.constant.CommonArouterCanstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.common.mvpdemo.view.HomeActivity;
import com.wanshare.common.search.KeywordsFragment;
import com.wanshare.common.search.ResultFragment;
import com.wanshare.wscomponent.logger.LogUtil;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    protected void initData() {
        LogUtil.init();

        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(getApplication());

    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @OnClick({R2.id.btn_util_test,R2.id.tv_mvp,R2.id.btn_search})
    public void onViewClicked(View v) {
            int id = v.getId();
        if(id == R.id.btn_util_test){
            UtilDemo.startActivity(mContext);
        }else if(id == R.id.tv_mvp){
            startActivity(new Intent(this, HomeActivity.class));
        }else if(id == R.id.btn_search){
            LogUtil.d("搜索");
            ARouter.getInstance().build(CommonArouterCanstant.COMMON_SEARCH)
                    .withString(IntentConstant.EXTRA_KEYWORDS_FRAGMENT, KeywordsFragment.class.getName())
                    .withString(IntentConstant.EXTRA_RESULT_FRAGMENT, ResultFragment.class.getName())
                    .navigation(this);
        }
    }

    @Override
    protected boolean showTitle() {
        return false;
    }

    @Override
    public void showHintMessage(@NonNull String message) {

    }
}
