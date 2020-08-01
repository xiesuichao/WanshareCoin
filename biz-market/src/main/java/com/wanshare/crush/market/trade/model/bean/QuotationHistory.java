package com.wanshare.crush.market.trade.model.bean;

/**
 * k线历史数据
 * </br>
 * Date: 2018/9/14 14:47
 *
 * @author hemin
 */
public class QuotationHistory {


    /**
     * openPrice : 6152.42079876
     * closePrice : 6153.56897383
     * highestPrice : 6178.39529986
     * lowestPrice : 6049.90284598
     * volume : 47.86270566
     * time : 1536015600000
     */

    private String openPrice;
    private String closePrice;
    private String highestPrice;
    private String lowestPrice;
    private String volume;
    private String time;

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(String highestPrice) {
        this.highestPrice = highestPrice;
    }

    public String getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(String lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
