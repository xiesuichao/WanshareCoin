package com.wanshare.crush.market.trade.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.market.order.model.bean.HistoryOrder;

import java.util.List;

/**
 *
 * </br>
 * Date: 2018/9/6 15:41
 *
 * @author hemin
 */
public class QuotationDealContract {

    public interface View extends IView{

        /**
         * 显示成交记录
         * @param datas
         */
        void showTradeHistory(List<HistoryOrder.ItemsBean> datas);
    }

    public interface Presenter extends IPresenter{

        void getTradeHistory(String symbol);
    }

}
