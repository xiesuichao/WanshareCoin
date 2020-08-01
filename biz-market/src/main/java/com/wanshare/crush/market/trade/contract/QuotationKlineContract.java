package com.wanshare.crush.market.trade.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.wscomponent.chart.kline.model.bean.HisData;

import java.util.List;

/**
 * Kline
 * </br>
 * Date: 2018/9/6 14:59
 *
 * @author hemin
 */
public class QuotationKlineContract {
    public interface View extends IView{

        /**
         * 显示k线
         * @param hisDatas
         */
        void showKline(List<HisData> hisDatas);
    }

    public interface Presenter extends IPresenter{

        /**
         * 获取k线历史数据
         * @param symbol
         * @param period
         * @param from
         * @param to
         */
        void getKlineHistory(String symbol, String period, String from, String to);
    }
}
