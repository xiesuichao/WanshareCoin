package com.wanshare.crush.exchange.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.exchange.model.bean.TopExchange;

/**
 * @author wangdunwei
 * @date 2018/8/29
 */
public class HotExchangeContract {
    public interface Presenter extends IPresenter {
        void requestExchangeByTradeAmount(int currentPage, int i);
    }

    public interface View extends IView{
        void showExchangeList(TopExchange topExchange);
    }
}
