package com.wanshare.crush.exchange.model.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangdunwei
 * @deprecated 测试使用
 * //TODO 不再使用时需删除
 */
@Deprecated
public class TestData {
    private static final Gson gson = new Gson();
    private static final String exchange_list = " [{\n" +
            "      \"id\": \"4\",\n" +
            "      \"logoUrl\": \"logo/exchange.png\",\n" +
            "      \"name\": \"天空\",\n" +
            "      \"usdAmount\": \"585082.7325934126\",\n" +
            "      \"tradingPairCount\": \"4\",\n" +
            "      \"summary\": \"aaaaa\",\n" +
            "      \"tags\": \"biaoqian\",\n" +
            "      \"nationality\": \"China\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"7\",\n" +
            "      \"logoUrl\": \"logo/exchange.png\",\n" +
            "      \"name\": \"打的\",\n" +
            "      \"usdAmount\": \"567327.6926292941\",\n" +
            "      \"tradingPairCount\": \"9\",\n" +
            "      \"summary\": \"aaaaa\",\n" +
            "      \"tags\": \"biaoqian\",\n" +
            "      \"nationality\": \"China\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"5\",\n" +
            "      \"logoUrl\": \"logo/exchange.png\",\n" +
            "      \"name\": \"河流\",\n" +
            "      \"usdAmount\": \"466278.7537955565\",\n" +
            "      \"tradingPairCount\": \"8\",\n" +
            "      \"summary\": \"aaaaa\",\n" +
            "      \"tags\": \"biaoqian\",\n" +
            "      \"nationality\": \"China\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6\",\n" +
            "      \"logoUrl\": \"logo/exchange.png\",\n" +
            "      \"name\": \"山川\",\n" +
            "      \"usdAmount\": \"441928.0438675974\",\n" +
            "      \"tradingPairCount\": \"0\",\n" +
            "      \"summary\": \"aaaaa\",\n" +
            "      \"tags\": \"biaoqian\",\n" +
            "      \"nationality\": \"China\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"3\",\n" +
            "      \"logoUrl\": \"logo/exchange.png\",\n" +
            "      \"name\": \"么么哒\",\n" +
            "      \"usdAmount\": \"387552.20840488456\",\n" +
            "      \"tradingPairCount\": \"12\",\n" +
            "      \"summary\": \"aaaaa\",\n" +
            "      \"tags\": \"biaoqian\",\n" +
            "      \"nationality\": \"China\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"logoUrl\": \"logo/exchange.png\",\n" +
            "      \"name\": \"呵呵哒\",\n" +
            "      \"usdAmount\": \"340466.51386397856\",\n" +
            "      \"tradingPairCount\": \"3\",\n" +
            "      \"summary\": \"aaaaa\",\n" +
            "      \"tags\": \"biaoqian\",\n" +
            "      \"nationality\": \"China\"\n" +
            "    }\n" +
            "  ]";

    public static List<Exchange> ofExchangeList() {
        return gson.fromJson(exchange_list, new TypeToken<List<Exchange>>() {}.getType());
    }

    public static List<String> ofBannerList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("http://pic21.photophoto.cn/20111106/0020032891433708_b.jpg");
        list.add("http://pic5.photophoto.cn/20071228/0034034901778224_b.jpg");
        list.add("http://pic9.photophoto.cn/20081128/0033033999061521_b.jpg");
        return list;
    }

    public static List<Market> ofMarketList() {
        ArrayList<Market> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            list.add(new Market(i + "", "" + i, "btc", "icon", "eth buyer", "" + i, "" + i, "" + i, "" + i, "" + i,
                    "" + i, "" + i));
        }

        return list;
    }
}
