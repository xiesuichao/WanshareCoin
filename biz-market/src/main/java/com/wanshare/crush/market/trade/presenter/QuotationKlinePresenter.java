package com.wanshare.crush.market.trade.presenter;

import com.google.gson.Gson;
import com.wanshare.common.mvp.BasePresenter;
import com.wanshare.common.mvp.ErrorHandleObserver;
import com.wanshare.common.mvp.RxSchedulers;
import com.wanshare.crush.market.trade.contract.QuotationKlineContract;
import com.wanshare.crush.market.trade.model.api.QuotationServer;
import com.wanshare.crush.market.trade.model.bean.QuotationHistory;
import com.wanshare.wscomponent.chart.kline.model.KLineQuotaModel;
import com.wanshare.wscomponent.chart.kline.model.bean.HisData;
import com.wanshare.wscomponent.logger.LogUtil;

import java.util.ArrayList;
import java.util.List;

import com.wanshare.wscomponent.http.ApiClient;
import com.wanshare.wscomponent.utils.ArithUtil;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * </br>
 * Date: 2018/9/6 15:01
 *
 * @author hemin
 */
public class QuotationKlinePresenter extends BasePresenter<QuotationKlineContract.View> implements QuotationKlineContract.Presenter {

    KLineQuotaModel model = new KLineQuotaModel();

    public QuotationKlinePresenter(QuotationKlineContract.View rootView) {
        super(rootView);
    }

    @Override
    public void getKlineHistory(String symbol, String period, String from, String to) {

        Observable.just(1)
                .map(new Function<Integer, List<HisData>>() {
                    @Override
                    public List<HisData> apply(Integer integer) throws Exception {
                        return testData();
                    }
                })
                .compose(RxSchedulers.<List<HisData>>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<List<HisData>>(this) {
                    @Override
                    public void onNext(List<HisData> hisDatas) {
                        if (mRootView == null || hisDatas == null) {
                            return;
                        }
                        model.initData(hisDatas);
                        mRootView.showKline(hisDatas);
                    }
                });

      /*  ApiClient.getInstance().create(QuotationServer.class)
                .getHistoricalData(symbol, period, from, to)
                .compose(RxSchedulers.<List<QuotationHistory>>applySchedulers(mRootView))
                .subscribe(new ErrorHandleObserver<List<QuotationHistory>>(this) {
                    @Override
                    public void onNext(List<QuotationHistory> result) {
                        if (mRootView == null || result == null) {
                            return;
                        }

                        try {
                            List<HisData> hisDatas = new ArrayList<>();
                            HisData data = null;

                            for(QuotationHistory quo:result){
                                data = new HisData();
                                data.setOpen(Double.parseDouble(quo.getOpenPrice()));
                                data.setHigh(Double.parseDouble(quo.getHighestPrice()));
                                data.setLow(Double.parseDouble(quo.getLowestPrice()));
                                data.setClose(Double.parseDouble(quo.getClosePrice()));
                                data.setVol(Double.parseDouble(quo.getVolume()));
                                data.setDate(Long.parseLong(quo.getTime()));
                                hisDatas.add(data);
                            }
                            model.initData(hisDatas);

                            mRootView.showKline(hisDatas);
                        } catch (Exception ex) {
                            LogUtil.ex(ex);
                        }
                    }
                });*/
    }

    private List<HisData> testData() {
        String dataStr = "[[\"32500000\",\"32880000\",\"31570000\",\"31570000\",\"4108508697966\",\"1534836600\"],[\"31570000\",\"32400000\",\"30350000\",\"31160000\",\"1540475093256\",\"1534838400\"],[\"31160000\",\"32300000\",\"31160000\",\"32000000\",\"668264955383\",\"1534840200\"],[\"32000000\",\"33490000\",\"32000000\",\"33450000\",\"338093627660\",\"1534842000\"],[\"33450000\",\"33490000\",\"33400000\",\"33430000\",\"572240028431\",\"1534843800\"],[\"33430000\",\"33480000\",\"32270000\",\"33000000\",\"312984978761\",\"1534845600\"],[\"33000000\",\"33480000\",\"33000000\",\"33480000\",\"134074246780\",\"1534847400\"],[\"33480000\",\"33480000\",\"33040000\",\"33430000\",\"103046403375\",\"1534849200\"],[\"33430000\",\"33430000\",\"33430000\",\"33430000\",\"2880121534\",\"1534851000\"],[\"33430000\",\"33450000\",\"33000000\",\"33450000\",\"332485439028\",\"1534852800\"],[\"33450000\",\"33450000\",\"33000000\",\"33430000\",\"514893863579\",\"1534854600\"],[\"33430000\",\"33440000\",\"33430000\",\"33430000\",\"283088434632\",\"1534856400\"],[\"33430000\",\"33430000\",\"32300000\",\"32300000\",\"344767419632\",\"1534858200\"],[\"32300000\",\"33440000\",\"32290000\",\"32800000\",\"475462616734\",\"1534860000\"],[\"32800000\",\"33320000\",\"32280000\",\"33320000\",\"194227256961\",\"1534861800\"],[\"33320000\",\"33320000\",\"33320000\",\"33320000\",\"24656800000\",\"1534863600\"],[\"33320000\",\"33320000\",\"32270000\",\"33300000\",\"137562527712\",\"1534865400\"],[\"33300000\",\"33300000\",\"33290000\",\"33300000\",\"166690459330\",\"1534867200\"],[\"33300000\",\"33300000\",\"33300000\",\"33300000\",\"41886744660\",\"1534869000\"],[\"33300000\",\"33300000\",\"33190000\",\"33190000\",\"94142040280\",\"1534870800\"],[\"33190000\",\"33190000\",\"32280000\",\"32280000\",\"5768436\",\"1534874400\"],[\"32280000\",\"33190000\",\"32280000\",\"33190000\",\"66380000000\",\"1534878000\"],[\"33190000\",\"33190000\",\"33000000\",\"33000000\",\"4926900\",\"1534888800\"],[\"33000000\",\"33000000\",\"32500000\",\"32500000\",\"49166227500\",\"1534890600\"],[\"32500000\",\"32500000\",\"32500000\",\"32500000\",\"22744185750\",\"1534894200\"],[\"32500000\",\"33000000\",\"32500000\",\"33000000\",\"24283741857203\",\"1534896000\"],[\"33000000\",\"33300000\",\"32280000\",\"33300000\",\"343630576215\",\"1534897800\"],[\"33300000\",\"33300000\",\"32780000\",\"32780000\",\"11071518518984\",\"1534899600\"],[\"32780000\",\"32850000\",\"32780000\",\"32850000\",\"23795231161016\",\"1534901400\"],[\"32850000\",\"33260000\",\"32270000\",\"33240000\",\"354480579404\",\"1534903200\"],[\"33240000\",\"33240000\",\"32330000\",\"33000000\",\"12869534190218\",\"1534905000\"],[\"33000000\",\"33140000\",\"32330000\",\"32500000\",\"1528730072106\",\"1534906800\"],[\"32500000\",\"32500000\",\"32330000\",\"32330000\",\"255179184636\",\"1534908600\"],[\"32330000\",\"32400000\",\"32000000\",\"32000000\",\"906958517553\",\"1534910400\"],[\"32000000\",\"32500000\",\"31200000\",\"31250000\",\"510391963670\",\"1534912200\"],[\"31250000\",\"32450000\",\"31250000\",\"32400000\",\"213487888213\",\"1534914000\"],[\"32400000\",\"32400000\",\"31450000\",\"32100000\",\"147058955350\",\"1534915800\"],[\"32100000\",\"32450000\",\"31250000\",\"31260000\",\"1395330019246\",\"1534917600\"],[\"31260000\",\"32050000\",\"31260000\",\"31260000\",\"115528819242\",\"1534919400\"],[\"31260000\",\"31260000\",\"30630000\",\"31230000\",\"1091730459229\",\"1534921200\"]]";

        List<List<String>> datas = new Gson().fromJson(dataStr, List.class);
        HisData data = null;
        List<HisData> hisDatas = new ArrayList<>();
        for (List<String> itemArr : datas) {
            try {
                String open = itemArr.get(0);
                String high = itemArr.get(1);
                String low = itemArr.get(2);
                String close = itemArr.get(3);
                String vol = itemArr.get(4);
                String time = itemArr.get(5);

                data = new HisData();
                data.setOpen(Double.parseDouble(ArithUtil.div(open, "100000000")));
                data.setHigh(Double.parseDouble(ArithUtil.div(high, "100000000")));
                data.setLow(Double.parseDouble(ArithUtil.div(low, "100000000")));
                data.setClose(Double.parseDouble(ArithUtil.div(close, "100000000")));
                data.setVol(Double.parseDouble(ArithUtil.div(vol, "100000000")));
                data.setDate(Long.parseLong(time) * 1000);

                hisDatas.add(data);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return hisDatas;
    }
}
