package com.wanshare.crush.asset.view;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.contract.SelectCoinContract;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.presenter.RechargeSelectCoinPresenter;

/**
 *  充币 选择币种
 * </br>
 * Date: 2018/8/25 11:47
 *
 * @author hemin
 */
@Route(path = AssetArouterConstant.ASSET_RECHARGE_SELECT_COIN)
public class RechargeSelectCoinActivity extends SelectCoinActivity{

    @Override
    protected SelectCoinContract.Presenter getPresenter() {
        return new RechargeSelectCoinPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mMyToolbar.setTitle(R.string.asset_select_recharge_coin);
    }

    @Override
    protected void onItemClick(CoinInfo item) {
        ARouter.getInstance().build(AssetArouterConstant.ASSET_RECHARGE_COIN).withSerializable(IntentConstant.EXTRA_COIN_INFO,item).navigation(this);
    }


}
