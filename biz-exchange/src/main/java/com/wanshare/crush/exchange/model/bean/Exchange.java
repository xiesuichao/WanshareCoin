package com.wanshare.crush.exchange.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangdunwei
 * @date 2018/8/29
 */
public class Exchange implements Parcelable {

    /**
     * id : 2
     * logoUrl : logo/exchange.png
     * name : exchangeName2
     * usdAmount : 340466.51386397856
     * cnyAmount : 2315172.2942750542
     * btcAmount : 54.04230378793311
     * tradingPairCount : 3
     * summary : aaaaa
     * tags : biaoqian
     * nationality : China
     */

    private String id;
    private String logoUrl;
    private String name;
    private String usdAmount;
    private String cnyAmount;
    private String btcAmount;
    private String tradingPairCount;
    private String summary;
    private String tags;
    private String nationality;

    public Exchange(String id, String logoUrl, String name, String usdAmount, String cnyAmount, String btcAmount,
                    String tradingPairCount, String summary, String tags, String nationality) {
        this.id = id;
        this.logoUrl = logoUrl;
        this.name = name;
        this.usdAmount = usdAmount;
        this.cnyAmount = cnyAmount;
        this.btcAmount = btcAmount;
        this.tradingPairCount = tradingPairCount;
        this.summary = summary;
        this.tags = tags;
        this.nationality = nationality;
    }

    @Deprecated
    public Exchange(String id, String logoUrl, String name, String usdAmount, int i, String tradingPairCount,
                    String summary, String tags, String country) {
        this.id = id;
        this.logoUrl = logoUrl;
        this.name = name;
        this.usdAmount = usdAmount;
        this.cnyAmount = usdAmount;
        this.btcAmount = usdAmount;
        this.tradingPairCount = tradingPairCount;
        this.summary = summary;
        this.tags = tags;
        this.nationality = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsdAmount() {
        return usdAmount;
    }

    public void setUsdAmount(String usdAmount) {
        this.usdAmount = usdAmount;
    }

    public String getCnyAmount() {
        return cnyAmount;
    }

    public void setCnyAmount(String cnyAmount) {
        this.cnyAmount = cnyAmount;
    }

    public String getBtcAmount() {
        return btcAmount;
    }

    public void setBtcAmount(String btcAmount) {
        this.btcAmount = btcAmount;
    }

    public String getTradingPairCount() {
        return tradingPairCount;
    }

    public void setTradingPairCount(String tradingPairCount) {
        this.tradingPairCount = tradingPairCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.logoUrl);
        dest.writeString(this.name);
        dest.writeString(this.cnyAmount);
        dest.writeString(this.usdAmount);
        dest.writeString(this.btcAmount);
        dest.writeString(this.tradingPairCount);
        dest.writeString(this.summary);
        dest.writeString(this.tags);
        dest.writeString(this.nationality);
    }

    public Exchange() {
    }

    protected Exchange(Parcel in) {
        this.id = in.readString();
        this.logoUrl = in.readString();
        this.name = in.readString();
        this.cnyAmount = in.readString();
        this.usdAmount = in.readString();
        this.btcAmount = in.readString();
        this.tradingPairCount = in.readString();
        this.summary = in.readString();
        this.tags = in.readString();
        this.nationality = in.readString();
    }

    public static final Parcelable.Creator<Exchange> CREATOR = new Parcelable.Creator<Exchange>() {
        @Override
        public Exchange createFromParcel(Parcel source) {
            return new Exchange(source);
        }

        @Override
        public Exchange[] newArray(int size) {
            return new Exchange[size];
        }
    };
}
