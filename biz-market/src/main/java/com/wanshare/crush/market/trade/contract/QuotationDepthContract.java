package com.wanshare.crush.market.trade.contract;

import com.wanshare.common.mvp.IPresenter;
import com.wanshare.common.mvp.IView;
import com.wanshare.crush.market.trade.model.bean.DepthBean;

import java.util.List;

/**
 *
 * </br>
 * Date: 2018/9/6 15:27
 *
 * @author hemin
 */
public class QuotationDepthContract {
    public interface View extends IView{
        /**
         *  显示深度数据
         * @param buyBillList
         * @param sellBillList
         */
        void showDepth(List<DepthBean> buyBillList, List<DepthBean> sellBillList);
    }

    public interface Presenter extends IPresenter{

        /**
         * 获取深度数据
         * @param symbol
         */
        void getDepthData(String symbol);
    }
}
