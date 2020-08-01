package com.wanshare.crush.market.trade.presenter;

import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.crush.market.trade.contract.QuotationDetailContract;
import com.wanshare.crush.market.trade.model.bean.OverviewBean;

/**
 *
 * </br>
 * Date: 2018/9/6 14:12
 *
 * @author hemin
 */
public class QuotationDetailPresenter extends BasePresenter<QuotationDetailContract.View> implements QuotationDetailContract.Presenter {
    public QuotationDetailPresenter(QuotationDetailContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getOverView(String symbol) {
        // TODO TASK: 2018/9/6 从socket获取数据
        OverviewBean bean = new OverviewBean();

        String lastPrice = "7340.8672";
        String priceToCNY ="49183.81";
        String open = "7040";
        String change = "0.0518";
        String vol = "1395862.34242";
        String buyCurrency = "BTC";
        String high = "7953.23";
        String low = "7025.32";
        String changeExtent="828.6624";

        bean.setLatestPrice(lastPrice);
        bean.setCnyLatestPrice(priceToCNY);
        bean.setBuyerCoin(buyCurrency);
        bean.setSellerCoin("USDT");
        bean.setChangePct(change);
        bean.setChangeExtent(changeExtent);
        bean.setHighestPrice(high);
        bean.setLowestPrice(low);
        bean.setVolume(vol);

        mRootView.showOverview(bean);
    }
}
