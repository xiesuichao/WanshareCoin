package com.wanshare.crush.market.order.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.common.biz.market.TradingCoins;

/**
 * Created by yangwenwu on 2018/8/20.
 */

public class HistoryOrderContract {

    public interface Presenter extends IPresenter {
        void getHistoryTrades(int page, String exchangeId, String tradingPair,String type,String[] status);

        void getTradingCoins();
    }

    public interface View extends IView {
        void showHistoryOrder(EntrustsOrder entrustsOrder);

        void showTradingCoins(TradingCoins tradingCoins);
    }

}
