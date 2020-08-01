package com.wanshare.crush.market.order.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.crush.market.order.contract.EntrustsOrderContract;
import com.wanshare.crush.market.order.model.api.MarketOrderServer;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.websocket.ErrorResponse;
import com.wanshare.wscomponent.websocket.MessageType;
import com.wanshare.wscomponent.websocket.Response;
import com.wanshare.wscomponent.websocket.SocketListener;
import com.wanshare.wscomponent.websocket.bean.KlineEntity;

import org.java_websocket.WebSocketListener;

/**
 * Created by yangwenwu on 2018/8/31.
 */

public class EntrustsOrderPresenter extends BasePresenter<EntrustsOrderContract.View> implements EntrustsOrderContract.Presenter,SocketListener{

    private final MarketOrderServer mMarketOrderServer;

    public EntrustsOrderPresenter(EntrustsOrderContract.View rootView){
        super(rootView);
        mMarketOrderServer = ApiClient.getInstance().create(MarketOrderServer.class);
    }

    @Override
    public void getEntrustsOrder(int page, String exchangeId, String tradingPair, String type, String[] status) {
        mMarketOrderServer.getEntrusts(page, exchangeId, tradingPair, type, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<EntrustsOrder>(this) {
                    @Override
                    public void onNext(EntrustsOrder entrustsOrder) {
                        super.onNext(entrustsOrder);

                        if (mRootView == null) return;

                        mRootView.showEntrustsOrder(entrustsOrder);
                    }
                });
    }

    @Override
    public void entrustsCancel(String orderId) {

        mMarketOrderServer.entrustsCancel(orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new ErrorHandleObserver<Object>(this) {
                    @Override
                    public void onNext(Object object) {
                        super.onNext(object);

                        if (mRootView == null) return;

                        mRootView.showEntrustsCancel(object);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    @Override
    public void sendSubscribeKline(String marketId, String period) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onConnectError(Throwable throwable) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onMessageResponse(Response response) {
        if (mRootView == null) return;

        if (response.getType() == MessageType.KLINE) {
            mRootView.showKline((KlineEntity) response.getResponseEntity());
        }
    }

    @Override
    public void onSendMessageError(ErrorResponse errorResponse) {

    }

    @Override
    public void onDestroy() {
//        remove this;
        super.onDestroy();
    }
}
