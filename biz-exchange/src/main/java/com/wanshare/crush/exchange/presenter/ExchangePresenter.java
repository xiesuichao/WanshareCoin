package com.wanshare.crush.exchange.presenter;

import com.wanshare.common.biz.exchange.ExchangeCoins;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.exchange.contract.ExchangeContract;
import com.wanshare.crush.exchange.model.api.ExchangeServer;
import com.wanshare.wscomponent.http.ApiClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author wangdunwei
 * @date 2018/8/29
 */
public class ExchangePresenter extends BasePresenter<ExchangeContract.View>
        implements ExchangeContract.Presenter {
    private final ExchangeServer exchangeServer;

    public ExchangePresenter(ExchangeContract.View view) {
        super(view);
        exchangeServer = ApiClient.getInstance().create(ExchangeServer.class);
    }

    @Override
    public void requestExchangeCoins(String id) {
        exchangeServer.getExchangeCoins(id)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .unsubscribeOn(Schedulers.io())
                      .subscribe(new ErrorHandleObserver<ExchangeCoins>(this) {
                          @Override
                          public void onNext(ExchangeCoins exchangeCoins) {
                              super.onNext(exchangeCoins);

                              if(mRootView != null) {
                                  mRootView.onGotMarketTitles(exchangeCoins.getBuyerCoin());
                              }
                          }
                      });
    }
}
