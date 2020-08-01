package com.wanshare.crush.asset.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.asset.contract.RechargeContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.CoinInfo;
import com.wanshare.crush.asset.model.bean.RechargeAddressBean;
import com.wanshare.wscomponent.logger.LogUtil;

import com.wanshare.wscomponent.http.ApiClient;


/**
 * 充币
 * </br>
 * Date: 2018/8/25 14:58
 *
 * @author hemin
 */
public class RechargePersenter extends BasePresenter<RechargeContract.View>
        implements RechargeContract.Presenter  {

    public RechargePersenter(RechargeContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getRechargeAddress(String coinId) {
        ApiClient.getInstance().create(AssetServer.class)
                .getRechargeAddress(coinId)
                .compose(RxSchedulers.<RechargeAddressBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<RechargeAddressBean>(this) {

                    @Override
                    public void onNext(RechargeAddressBean result) {
                        if (mRootView == null&& result==null) {
                            return;
                        }
                        LogUtil.d("result:"+result);
                        mRootView.showRechargeAddress(result);
                    }
                });
    }

    @Override
    public void getCoinInfo(String coinId) {
        ApiClient.getInstance().create(AssetServer.class)
                .getCoinInfo(coinId)
                .compose(RxSchedulers.<CoinInfo>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<CoinInfo>(this) {

                    @Override
                    public void onNext(CoinInfo result) {
                        if (mRootView == null) {
                            return;
                        }
                        LogUtil.d("result:"+result);
                        mRootView.showCoinInfo(result);
                    }
                });
    }
}
