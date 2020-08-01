package com.wanshare.crush.exchange.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.exchange.contract.TradeAmountExchangeContract;
import com.wanshare.crush.exchange.model.api.ExchangeServer;
import com.wanshare.crush.exchange.model.bean.TopExchange;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * @author wangdunwei
 * @date 2018/8/29
 */
public class TradeAmountExchangePresenter extends BasePresenter<TradeAmountExchangeContract.View>
        implements TradeAmountExchangeContract.Presenter {
    private final ExchangeServer exchangeServer;

    public TradeAmountExchangePresenter(TradeAmountExchangeContract.View view) {
        super(view);
        exchangeServer = ApiClient.getInstance().create(ExchangeServer.class);
    }

    @Override
    public void requestExchangeByTradeAmount(int currentPage, int pageSize) {
        exchangeServer.getExchangeBySort(currentPage, ExchangeServer.SORT_AMOUNT, pageSize)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .unsubscribeOn(Schedulers.io())
                      .subscribe(new ErrorHandleObserver<TopExchange>(this) {
                          @Override
                          public void onNext(TopExchange topExchange) {
                              super.onNext(topExchange);
                              if(mRootView != null) {
                                  mRootView.showExchangeList(topExchange);
                              }
                          }
                      });
    }
}
