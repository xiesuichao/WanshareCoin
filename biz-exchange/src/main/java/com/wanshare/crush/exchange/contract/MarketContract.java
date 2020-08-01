package com.wanshare.crush.exchange.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.exchange.model.bean.Market;

import java.util.List;

/**
 * @author wangdunwei
 * @date 2018/8/28
 */
public class MarketContract {
    public interface Presenter extends IPresenter {
        void requestMarketList(String exchangeId, String groupId);
    }

    public interface View extends IView {
        void showExchangeList(List<Market> list);
    }
}
