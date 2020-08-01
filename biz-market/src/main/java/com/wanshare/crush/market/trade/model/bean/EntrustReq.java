package com.wanshare.crush.market.trade.model.bean;

public class EntrustReq {

   /**
    *  description:
    委托单表单

    marketId	string
    example: 12345
    市场ID

    tradeType	string
    交易类型 buy:买入 sell:买出

    Enum:
    Array [ 2 ]
    price	string($double)
    example: 3.2211
    价格

    volume	string($double)
    example: 12.11
    数量

    entrustType	string
    委托类型 limit限价 market市价

    Enum:
            [ 0, 1 ]*/

    private String marketId;
    private String tradeType;
    private String price;
    private String volume;
    private int entrustType;

    public EntrustReq() {
    }

    public EntrustReq(String marketId, String tradeType, String price, String volume, int entrustType) {
        this.marketId = marketId;
        this.tradeType = tradeType;
        this.price = price;
        this.volume = volume;
        this.entrustType = entrustType;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(int entrustType) {
        this.entrustType = entrustType;
    }


}