package com.wanshare.crush.asset.view;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.contract.SelectCoinContract;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.presenter.RechargeSelectCoinPresenter;
import com.wanshare.crush.asset.presenter.WithdrawSelectCoinPresenter;
import com.wanshare.wscomponent.logger.LogUtil;

import java.io.FileInputStream;

/**
 * 提币 选择币种
 * </br>
 * Date: 2018/8/25 16:53
 *
 * @author hemin
 */
@Route(path = AssetArouterConstant.ASSET_WITHDRAW_SELECT_COIN)
public class WithdrawSelectCoinActivity extends SelectCoinActivity {

    public static final int CODE_WITHDRAW = 1000;

    @Override
    protected SelectCoinContract.Presenter getPresenter() {
        return new WithdrawSelectCoinPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setTitle(R.string.asset_select_withdraw_coin);
    }

    @Override
    protected void onItemClick(CoinInfo item) {
        ARouter.getInstance().build(AssetArouterConstant.ASSET_WITHDRAW_COIN).withSerializable(IntentConstant.EXTRA_COIN_INFO, item).navigation(this, CODE_WITHDRAW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_WITHDRAW && resultCode== Activity.RESULT_OK){
            LogUtil.d("WithdrawSelectCoinActivity 提币成功");
            finish();
        }
    }
}
