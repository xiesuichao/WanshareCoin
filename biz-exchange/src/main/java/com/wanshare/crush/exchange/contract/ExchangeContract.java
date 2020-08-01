package com.wanshare.crush.exchange.contract;

import com.wanshare.common.biz.exchange.ExchangeCoins;
import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;

import java.util.List;

/**
 * @author wangdunwei
 * @date 2018/8/31
 */
public class ExchangeContract {
    public interface Presenter extends IPresenter{
        void requestExchangeCoins(String id);
    }

    public interface View extends IView{
        void onGotMarketTitles(List<ExchangeCoins.BuyerCoinBean> strings);
    }
}
