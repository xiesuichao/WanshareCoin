package com.wanshare.crush.market.order.presenter;


import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.market.order.contract.HistoryOrderContract;
import com.wanshare.crush.market.order.model.api.MarketOrderServer;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.common.biz.market.TradingCoins;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;

/**
 * Created by yangwenwu on 2018/8/20.
 */

public class HistoryOrderPresenter extends BasePresenter<HistoryOrderContract.View> implements HistoryOrderContract.Presenter {

    private final MarketOrderServer mMarketOrderServer;

    public HistoryOrderPresenter(HistoryOrderContract.View rootView) {
        super(rootView);
        mMarketOrderServer = ApiClient.getInstance().create(MarketOrderServer.class);
    }

    @Override
    public void getHistoryTrades(int page, String exchangeId, String tradingPair,String type,String[] status) {
        mMarketOrderServer.getEntrusts(page,exchangeId,tradingPair,type,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<EntrustsOrder>(this) {
                    @Override
                    public void onNext(EntrustsOrder historyOrder) {
                        super.onNext(historyOrder);

                        if (mRootView == null) return;

                        mRootView.showHistoryOrder(historyOrder);
                    }
                });
    }


    @Override
    public void getTradingCoins() {
        mMarketOrderServer.getTradingCoins()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<TradingCoins>(this) {
                    @Override
                    public void onNext(TradingCoins tradingCoins) {
                        super.onNext(tradingCoins);

                        if (mRootView == null) return;

                        mRootView.showTradingCoins(tradingCoins);
                    }
                });
    }
}
