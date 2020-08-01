package com.wanshare.crush.asset.view;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wanshare.common.base.BaseActivity;
import com.wanshare.common.biz.asset.AssetArouterConstant;
import com.wanshare.common.constant.IntentConstant;
import com.wanshare.crush.asset.R;
import com.wanshare.crush.asset.R2;
import com.wanshare.crush.asset.contract.AssetsDetailContract;
import com.wanshare.crush.asset.model.bean.AssetByCoinBean;
import com.wanshare.crush.asset.model.bean.AssetInfoBean;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.EstimatesBean;
import com.wanshare.crush.asset.presenter.AssetsDetailPresenter;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Richard on 2018/8/29
 */
@Route(path = AssetArouterConstant.ASSET_ASSETS_DETAIL)
public class AssetsDetailActivity extends BaseActivity<AssetsDetailContract.Presenter> implements AssetsDetailContract.View {


    @BindView(R2.id.tv_total_assets)
    TextView tvTotalAssets;
    @BindView(R2.id.tv_cny)
    TextView tvCny;
    @BindView(R2.id.tv_currency_available)
    TextView tvCurrencyAvailable;
    @BindView(R2.id.tv_available)
    TextView tvAvailable;
    @BindView(R2.id.tv_currency_frozen)
    TextView tvCurrencyFrozen;
    @BindView(R2.id.tv_frozen)
    TextView tvFrozen;

    private AssetInfoBean mAssetInfoBean;

    @Override
    protected AssetsDetailContract.Presenter getPresenter() {
        return new AssetsDetailPresenter(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.asset_activity_assets_detail;
    }

    @Override
    protected void initView() {
        mMyToolbar.setTitle(mAssetInfoBean.getShortName());
        tvCurrencyAvailable.setText(getString(R.string.asset_format_bracket, mAssetInfoBean.getFullName()));
        tvCurrencyFrozen.setText(getString(R.string.asset_format_bracket, mAssetInfoBean.getFullName()));
    }

    @Override
    protected void initData() {
        mPresenter.getAsset(mAssetInfoBean.getCoinId());
    }

    @Override
    protected void initIntent() {
        super.initIntent();
        mAssetInfoBean = getIntent().getParcelableExtra(IntentConstant.EXTRA_CURRENCY);
    }

    @OnClick({R2.id.btn_withdraw, R2.id.btn_recharge})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_withdraw) {// 跳转提币界面
            // TODO TASK: 2018/9/10 从接口获取数据
            CoinInfo coinInfo = new CoinInfo();
            coinInfo.setId("1");
            coinInfo.setShortName("BTC");
            coinInfo.setFullName("bitcoin");
            ARouter.getInstance().build(AssetArouterConstant.ASSET_WITHDRAW_COIN).withSerializable(IntentConstant.EXTRA_COIN_INFO, coinInfo).navigation(this);

        } else if (i == R.id.btn_recharge) {// 跳转充值界面
            // TODO TASK: 2018/9/10 从接口获取数据
            CoinInfo coinInfo = new CoinInfo();
            coinInfo.setId("1");
            coinInfo.setShortName("BTC");
            coinInfo.setFullName("bitcoin");
            ARouter.getInstance().build(AssetArouterConstant.ASSET_RECHARGE_COIN).withSerializable(IntentConstant.EXTRA_COIN_INFO, coinInfo).navigation(this);

        }
    }

    @Override
    public void showAsset(AssetByCoinBean bean) {
        if (bean == null) {
            return;
        }
        EstimatesBean estimatesBean = bean.getEstimates();
        if (estimatesBean != null) {
            tvTotalAssets.setText(estimatesBean.getBtc() + "");
            tvCny.setText(getString(R.string.asset_format_approximate_cny, estimatesBean.getCny() + ""));
        }
        tvAvailable.setText(bean.getBalance());
        tvFrozen.setText(bean.getFrozen());
    }
}
