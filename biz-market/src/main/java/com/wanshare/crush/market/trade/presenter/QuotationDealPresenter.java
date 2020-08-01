package com.wanshare.crush.market.trade.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.crush.market.order.model.bean.HistoryOrder;
import com.wanshare.crush.market.trade.contract.QuotationDealContract;
import com.wanshare.wscomponent.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * </br>
 * Date: 2018/9/6 15:41
 *
 * @author hemin
 */
public class QuotationDealPresenter extends BasePresenter<QuotationDealContract.View> implements QuotationDealContract.Presenter {
    public QuotationDealPresenter(QuotationDealContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getTradeHistory(String symbol) {
        List<HistoryOrder.ItemsBean> datas=new ArrayList<>();
        for(int i=0;i<50;i++){
            HistoryOrder.ItemsBean bean = new HistoryOrder.ItemsBean();
            bean.setPrice("123"+i);
            bean.setTradeAmount("8932"+i);
            bean.setCreatedAt(DateUtil.getCurrentDate());
            datas.add(bean);
        }
        mRootView.showTradeHistory(datas);

    }
}
