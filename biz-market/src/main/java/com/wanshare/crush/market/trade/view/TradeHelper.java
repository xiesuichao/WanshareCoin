package com.wanshare.crush.market.trade.view;

import android.text.Editable;
import android.text.TextUtils;

import com.wanshare.wscomponent.utils.ArithUtil;

/**
 * @author Xu wenxiang
 * create at 2018/9/12
 * description: 交易帮助类
 * */
public class TradeHelper {

    /**
     * @param str
     * @return
     */
    public static String getDecimalEndNum(String str){
        String num = "1";
        if (str.contains(".")) {
            String priceDecimal = str.substring(str.indexOf(".") + 1);
            switch (priceDecimal.length()) {
                case 1:
                    num = "0.1";
                    break;

                case 2:
                    num = "0.01";
                    break;

                case 3:
                    num = "0.001";
                    break;

                case 4:
                    num = "0.0001";
                    break;

                case 5:
                    num = "0.00001";
                    break;

                case 6:
                    num = "0.000001";
                    break;

                case 7:
                    num = "0.0000001";
                    break;

                case 8:
                    num = "0.00000001";
                    break;
            }
        }
        return num;
    }


    public static String getAmount(String availableCount, String price, String tradeType, String percentStr) {
        String amount;
        if ("0".equals(availableCount)) {
            amount = "0";
        } else {
            if ("buy".equals(tradeType)) {
                String maxCount = ArithUtil.div(availableCount, price, 8);
                amount = maxCount.contains("E") ? "0" : ArithUtil.mul(maxCount, percentStr, 8);
            } else {
                amount = ArithUtil.mul(availableCount, percentStr, 8);
            }
        }
        return TextUtils.isEmpty(amount) ? "0" : amount;
    }

    public static void checkInput(Editable s, int numPrecision){
        if (s.toString().contains(".")) {
            int dotPos = s.toString().indexOf(".");
            int length = s.toString().length();
            if (dotPos == 0) {
                s.insert(0, "0");
            } else if (length <= 9 && numPrecision > 0 && length - 1 - dotPos > numPrecision) {
                s.delete(dotPos + numPrecision + 1, dotPos + numPrecision + 2);
            } else if (length > 9) {
                s.delete(9, 10);
            }

        } else if (s.toString().length() > 8) {
            s.delete(8, 9);
        }
    }

}
