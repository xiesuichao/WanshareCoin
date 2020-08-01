package com.wanshare.crush.market.trade.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.market.order.model.bean.EntrustsOrder;
import com.wanshare.crush.market.trade.model.bean.CoinAssetsBean;
import com.wanshare.crush.market.trade.model.bean.CoinPair;
import com.wanshare.crush.market.trade.model.bean.Entrust;
import com.wanshare.crush.market.trade.model.bean.EntrustReq;

import java.util.List;

public class TradeContract {

    public interface View extends IView{
        void resultEntrustMock(List<EntrustsOrder.ItemsBean> list);

        void resultTrades(List<CoinPair> topList, List<CoinPair> botList);

        void resultEntrust(Object object);

        void resultEntrustList(List<EntrustsOrder.ItemsBean> items);

        void resultEntrustCancel(Object object);

        void resultCoinAssert(CoinAssetsBean bean);
    }

    public interface Presenter extends IPresenter{

        void getEntrustList();

        void getTradeList();

        void entrusts(EntrustReq req);

        void entrustsList(int page, String exchangeId, String tradingPair);

        void entrustCancel(String id);

        void queryCoinAssert(String coinId);

    }

}
