package com.wanshare.crush.exchange.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.exchange.contract.SearchCoinContract;
import com.wanshare.crush.exchange.model.api.ExchangeServer;
import com.wanshare.crush.exchange.model.bean.Market;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * @author wangdunwei
 * @date 2018/8/28
 */
public class SearchCoinPresenter extends BasePresenter<SearchCoinContract.View> implements SearchCoinContract.Presenter {
    private final ExchangeServer exchangeServer;
    public SearchCoinPresenter(SearchCoinContract.View view) {
        super(view);
        exchangeServer = ApiClient.getInstance().create(ExchangeServer.class);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void requestMarketList(String exchangeId, String groupId) {
        exchangeServer.getMarketFromExchange(exchangeId, groupId)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .unsubscribeOn(Schedulers.io())
                      .subscribe(new ErrorHandleObserver<List<Market>>(this) {
                          @Override
                          public void onNext(List<Market> list) {
                              super.onNext(list);

                              if(mRootView != null) {
                                  mRootView.showExchangeList(list);
                              }
                          }
                      });
    }
}
