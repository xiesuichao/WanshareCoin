package com.wanshare.crush.asset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.wscomponent.logger.LogUtil;

import okhttp3.logging.HttpLoggingInterceptor;
import wanshare.wscomponent.http.ApiClient;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.init(getApplication());
        LogUtil.init();
        ApiClient.init("http://192.168.80.211:8888/v1/", new HttpLoggingInterceptor());
        ApiClient.getInstance().initHttpClient();
        setContentView(R.layout.activity_main);
    }

    public void onRouteWithdrawAddressList(View view) {
        ARouter.getInstance().build(AssetArouterConstant.WITHDRAW_ADDRESS_LIST).navigation(this);
//        startActivity(new Intent(this, WithdrawAddressListActivity.class));
    }

    public void onAssetRecord(View view) {
        ARouter.getInstance().build(AssetArouterConstant.ASSET_RECORD).navigation(this);
    }

    public void onSelectCoin(View view) {
//        ARouter.getInstance().build(AssetArouterConstant.ASSET_SELECT_COIN).navigation(this);
    }

    public void onReCharge(View view) {
        ARouter.getInstance().build(AssetArouterConstant.ASSET_RECHARGE_SELECT_COIN).navigation(this);
    }

    public void onWithdraw(View view) {
        ARouter.getInstance().build(AssetArouterConstant.ASSET_WITHDRAW_SELECT_COIN).navigation(this);
    }

    public void onTotalAssets(View view) {
        ARouter.getInstance().build(AssetArouterConstant.ASSET_TOTAL_ASSETS).navigation(this);
    }

}
