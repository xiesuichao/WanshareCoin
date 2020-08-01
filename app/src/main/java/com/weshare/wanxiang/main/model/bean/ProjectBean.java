package com.weshare.wanxiang.main.model.bean;

/**
 * Created by Jason on 2018/9/4.
 */

public class ProjectBean {


    /**
     * projectId : 6
     * coinId : 6
     * fullName : xxxxBTC
     * shortName : BTC
     * coinLogo : xxx/xxx.png
     * issuedVolume : 1000000.00000000
     * latestPrice : 528910.2629552332
     * usdAmount24H : 3.269532149406433E9
     * chgPct24H : 99.87899648677187
     * listedExchange : 11
     * marketValue : 3.8465084110663925E10
     */

    private String projectId;
    private String coinId;
    private String fullName;
    private String shortName;
    private String coinLogo;
    private String issuedVolume;
    private String latestPrice;
    private String usdAmount24H;
    private String chgPct24H;
    private String listedExchange;
    private String marketValue;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCoinLogo() {
        return coinLogo;
    }

    public void setCoinLogo(String coinLogo) {
        this.coinLogo = coinLogo;
    }

    public String getIssuedVolume() {
        return issuedVolume;
    }

    public void setIssuedVolume(String issuedVolume) {
        this.issuedVolume = issuedVolume;
    }

    public String getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(String latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getUsdAmount24H() {
        return usdAmount24H;
    }

    public void setUsdAmount24H(String usdAmount24H) {
        this.usdAmount24H = usdAmount24H;
    }

    public String getChgPct24H() {
        return chgPct24H;
    }

    public void setChgPct24H(String chgPct24H) {
        this.chgPct24H = chgPct24H;
    }

    public String getListedExchange() {
        return listedExchange;
    }

    public void setListedExchange(String listedExchange) {
        this.listedExchange = listedExchange;
    }

    public String getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(String marketValue) {
        this.marketValue = marketValue;
    }
}
