package com.wanshare.crush.market.trade.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.market.trade.model.bean.OverviewBean;

/**
 * 市场行情k线页面
 * </br>
 * Date: 2018/9/6 11:50
 *
 * @author hemin
 */
public class QuotationDetailContract {

    public interface View extends IView{

        /**
         * 显示概要信息
         * @param bean
         */
        void showOverview(OverviewBean bean);
    }

    public interface Presenter extends IPresenter{

        /**
         * 获取概要数据
         * @param symbol
         */
        void getOverView(String symbol);
    }
}
