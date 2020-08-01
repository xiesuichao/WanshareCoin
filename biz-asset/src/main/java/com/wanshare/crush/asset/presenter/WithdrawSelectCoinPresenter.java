package com.wanshare.crush.asset.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.asset.contract.SelectCoinContract;
import com.wanshare.crush.asset.model.api.AssetServer;
import com.wanshare.crush.asset.model.bean.CoinInfo;

import java.util.List;

import com.wanshare.wscomponent.http.ApiClient;


/**
 * 提币选择币种
 * </br>
 * Date: 2018/8/25 16:54
 *
 * @author hemin
 */
public class WithdrawSelectCoinPresenter extends BasePresenter<SelectCoinContract.View> implements   SelectCoinContract.Presenter {

    public WithdrawSelectCoinPresenter(SelectCoinContract.View view){
        super(view);
    }

    @Override
    public void getCoinLists() {
        ApiClient.getInstance().create(AssetServer.class)
                .getAvailableWithdrawCoinList()
                .compose(RxSchedulers.<List<CoinInfo>>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<List<CoinInfo>>(this) {

                    @Override
                    public void onNext(List<CoinInfo> resultList) {
                        if (mRootView == null) {
                            return;
                        }

                        mRootView.showCoinList(resultList);
                    }
                });

    }
}
