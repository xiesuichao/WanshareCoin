package com.wanshare.crush.market.trade.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.market.order.model.api.MarketOrderServer;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.crush.market.trade.contract.TradeContract;
import com.wanshare.crush.market.trade.model.api.QuotationServer;
import com.wanshare.crush.market.trade.model.bean.CoinAssetsBean;
import com.wanshare.crush.market.trade.model.bean.CoinPair;
import com.wanshare.crush.market.trade.model.bean.Entrust;
import com.wanshare.crush.market.trade.model.bean.EntrustReq;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.http.BaseRequestBody;

public class TradePresenter extends BasePresenter<TradeContract.View> implements TradeContract.Presenter {

    public TradePresenter(TradeContract.View rootView) {
        super(rootView);
    }

    public TradePresenter() {
    }

    @Override
    public void getEntrustList() {
        List<EntrustsOrder.ItemsBean> list = new ArrayList<>();
        list.add(new EntrustsOrder.ItemsBean("", "2017-01-22T09:28:33Z", "98743.33", "7843.22", "5632.11", "875.23"));
        list.add(new EntrustsOrder.ItemsBean("", "2017-01-22T09:28:33Z", "98743.33", "7843.22", "5632.11", "875.23"));
        list.add(new EntrustsOrder.ItemsBean("", "2017-01-22T09:28:33Z", "98743.33", "7843.22", "5632.11", "875.23"));
        list.add(new EntrustsOrder.ItemsBean("", "2017-01-22T09:28:33Z", "98743.33", "7843.22", "5632.11", "875.23"));
        list.add(new EntrustsOrder.ItemsBean("", "2017-01-22T09:28:33Z", "98743.33", "7843.22", "5632.11", "875.23"));
        mRootView.resultEntrustMock(list);
    }

    @Override
    public void getTradeList() {
        CoinPair pair;
        List<CoinPair> topList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pair = new CoinPair();
            pair.setTradeType("buy");
            pair.setPrice("9847.23");
            pair.setAmount((i + 10) +"");
            topList.add(pair);
        }
        List<CoinPair> botList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            pair = new CoinPair();
            pair.setTradeType("sell");
            pair.setPrice("9847.23");
            pair.setAmount((i + 10)+"");
            botList.add(pair);
        }
        mRootView.resultTrades(topList, botList);
    }

    @Override
    public void entrusts(EntrustReq req) {
        ApiClient.getInstance().create(MarketOrderServer.class).entrusts(BaseRequestBody.create(req))
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultEntrust(object);
                    }
                });
    }

    @Override
    public void entrustsList(int page, String exchangeId, String tradingPair) {
        ApiClient.getInstance().create(MarketOrderServer.class)
                .getEntrusts(page, exchangeId, tradingPair, "both", new String[]{"entrusting"})
                .compose(RxSchedulers.<EntrustsOrder>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<EntrustsOrder>(this) {
                    @Override
                    public void onNext(EntrustsOrder object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultEntrustList(object.getItems());
                    }
                });
    }

    @Override
    public void entrustCancel(String id) {
        ApiClient.getInstance().create(MarketOrderServer.class)
                .entrustsCancel(id)
                .compose(RxSchedulers.<Object>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultEntrustCancel(object);
                    }
                });
    }

    @Override
    public void queryCoinAssert(String coinId) {
        ApiClient.getInstance().create(QuotationServer.class)
                .queryCoinAssert(coinId)
                .compose(RxSchedulers.<CoinAssetsBean>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<CoinAssetsBean>(this) {
                    @Override
                    public void onNext(CoinAssetsBean object) {
                        super.onNext(object);
                        if (mRootView == null) {
                            return;
                        }
                        mRootView.resultCoinAssert(object);
                    }
                });
    }
}
