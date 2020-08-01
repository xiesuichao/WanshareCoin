package com.wanshare.common.biz.market;

/**
 * 市场模块， 路由跳转的常量类
 * </br>
 * Date: 2018/8/18 15:57
 *
 * @author hemin
 */
public class MarketArouterConstant {

    private static final String BIZ_GROUP = "/market/";

    public static final String MARKET_ORDER_HISTORY = BIZ_GROUP + "history";

    public static final String MARKET_TRADE = BIZ_GROUP + "trade";
    public static final String MARKET_ORDER_HISTORY_FILTER = BIZ_GROUP + "filter";

    public static final String MARKET_TRADE_QUOTATION_DETAIL = BIZ_GROUP + "trade/quotation_detail";
    public static final String MARKET_TRADE_KLINE_LANDSCAPE= BIZ_GROUP + "trade/kline_landscape";
}
