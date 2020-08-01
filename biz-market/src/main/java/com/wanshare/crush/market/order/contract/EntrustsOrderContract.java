package com.wanshare.crush.market.order.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.wscomponent.websocket.SocketListener;
import com.wanshare.wscomponent.websocket.bean.KlineEntity;

import org.java_websocket.WebSocketListener;

import java.lang.reflect.Array;

/**
 * Created by yangwenwu on 2018/8/31.
 */

public class EntrustsOrderContract{

    public interface Presenter extends IPresenter {
        void getEntrustsOrder(int page, String exchangeId, String tradingPair, String type, String[] status);

        void entrustsCancel(String orderId);

        void sendSubscribeKline(String marketId,String period );
    }

    public interface View extends IView {
        void showEntrustsOrder(EntrustsOrder entrustsOrder);

        void showEntrustsCancel(Object object);

        void showKline(KlineEntity klineEntity);
    }
}
