package com.wanshare.crush.market.trade.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2018/5/11.
 */

public class CoinPair implements Parcelable {

    private String price;
    private String amount;
    private String tradeType;
    private String symbolName;
    private String buyCurrency;
    private String sellCurrency;
    private int tradePricePrecision;
    private int tradeVolumePrecision;



    public CoinPair() {
    }

    public CoinPair(String price, String amount, String tradeType, String symbolName, String buyCurrency, String sellCurrency, int tradePricePrecision, int tradeVolumePrecision) {
        this.price = price;
        this.amount = amount;
        this.tradeType = tradeType;
        this.symbolName = symbolName;
        this.buyCurrency = buyCurrency;
        this.sellCurrency = sellCurrency;
        this.tradePricePrecision = tradePricePrecision;
        this.tradeVolumePrecision = tradeVolumePrecision;
    }

    protected CoinPair(Parcel in) {
        price = in.readString();
        amount = in.readString();
        tradeType = in.readString();
        symbolName = in.readString();
        buyCurrency = in.readString();
        sellCurrency = in.readString();
        tradePricePrecision = in.readInt();
        tradeVolumePrecision = in.readInt();
    }

    public static final Creator<CoinPair> CREATOR = new Creator<CoinPair>() {
        @Override
        public CoinPair createFromParcel(Parcel in) {
            return new CoinPair(in);
        }

        @Override
        public CoinPair[] newArray(int size) {
            return new CoinPair[size];
        }
    };

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getBuyCurrency() {
        return buyCurrency;
    }

    public void setBuyCurrency(String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getSellCurrency() {
        return sellCurrency;
    }

    public void setSellCurrency(String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }

    public int getTradePricePrecision() {
        return tradePricePrecision;
    }

    public void setTradePricePrecision(int tradePricePrecision) {
        this.tradePricePrecision = tradePricePrecision;
    }

    public int getTradeVolumePrecision() {
        return tradeVolumePrecision;
    }

    public void setTradeVolumePrecision(int tradeVolumePrecision) {
        this.tradeVolumePrecision = tradeVolumePrecision;
    }

    @Override
    public String toString() {
        return "CoinPair{" +
                "price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                ", tradeType='" + tradeType + '\'' +
                ", symbolName='" + symbolName + '\'' +
                ", buyCurrency='" + buyCurrency + '\'' +
                ", sellCurrency='" + sellCurrency + '\'' +
                ", tradePricePrecision='" + tradePricePrecision + '\'' +
                ", tradeVolumePrecision='" + tradeVolumePrecision + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeString(amount);
        dest.writeString(tradeType);
        dest.writeString(symbolName);
        dest.writeString(buyCurrency);
        dest.writeString(sellCurrency);
        dest.writeInt(tradePricePrecision);
        dest.writeInt(tradeVolumePrecision);
    }
}
