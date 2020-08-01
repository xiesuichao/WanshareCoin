package com.wanshare.crush.market.trade.model.bean;

/**
 * Created by snyyga on 2018/5/19.
 */

public class TradeDataModel {

    private String mLastPrice;
    private String mPriceToCNY;
    private String mPercent;
    private String mChange;

    public String getmChange() {
        return mChange;
    }

    public void setmChange(String mChange) {
        this.mChange = mChange;
    }

    public String getmLastPrice() {
        return mLastPrice;
    }

    public void setmLastPrice(String mLastPrice) {
        this.mLastPrice = mLastPrice;
    }

    public String getmPriceToCNY() {
        return mPriceToCNY;
    }

    public void setmPriceToCNY(String mPriceToCNY) {
        this.mPriceToCNY = mPriceToCNY;
    }

    public String getmPercent() {
        return mPercent;
    }

    public void setmPercent(String mPercent) {
        this.mPercent = mPercent;
    }
}
