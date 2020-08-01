package com.wanshare.crush.market.trade.model.bean;

/**
 * Created by Talon on 2018/5/15.
 */

public class Entrust {

    /**
     * orderid : 5af9b056fc441d0007822b1b
     * trade_type : buy
     * market : ETH/BTC
     * memberid : 200041
     * price : 12
     * volume : 22
     * origin_volume : 22
     * done_at : null
     * created_at : 2018-05-14T15:50:46.000Z
     * updated_at : null
     * source : WEB
     * ord_type : limit
     * freezed : 264
     * state : wait
     * origin_freezed : 264
     * funds_received : 0
     * trades_count : 0
     */

    private String orderid;
    private String trade_type;
    private String market;
    private String memberid;
    private String price;
    private String volume;
    private String origin_volume;
    private String done_at;
    private String created_at;
    private String updated_at;
    private String source;
    private String ord_type;
    private String freezed;
    private String state;
    private String origin_freezed;
    private String funds_received;
    private String trades_count;
    private String origin_amounts;
    private String amounts;
    private String already_volume;
    private String avg_price;

    public Entrust() {
    }

    public Entrust(String orderid, String trade_type, String market, String memberid, String price, String volume, String origin_volume, String done_at, String created_at, String updated_at, String source, String ord_type, String freezed, String state, String origin_freezed, String funds_received, String trades_count, String origin_amounts, String amounts, String already_volume, String avg_price) {
        this.orderid = orderid;
        this.trade_type = trade_type;
        this.market = market;
        this.memberid = memberid;
        this.price = price;
        this.volume = volume;
        this.origin_volume = origin_volume;
        this.done_at = done_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.source = source;
        this.ord_type = ord_type;
        this.freezed = freezed;
        this.state = state;
        this.origin_freezed = origin_freezed;
        this.funds_received = funds_received;
        this.trades_count = trades_count;
        this.origin_amounts = origin_amounts;
        this.amounts = amounts;
        this.already_volume = already_volume;
        this.avg_price = avg_price;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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

    public String getOrigin_volume() {
        return origin_volume;
    }

    public void setOrigin_volume(String origin_volume) {
        this.origin_volume = origin_volume;
    }

    public String getDone_at() {
        return done_at;
    }

    public void setDone_at(String done_at) {
        this.done_at = done_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getOrd_type() {
        return ord_type;
    }

    public void setOrd_type(String ord_type) {
        this.ord_type = ord_type;
    }

    public String getFreezed() {
        return freezed;
    }

    public void setFreezed(String freezed) {
        this.freezed = freezed;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrigin_freezed() {
        return origin_freezed;
    }

    public void setOrigin_freezed(String origin_freezed) {
        this.origin_freezed = origin_freezed;
    }

    public String getFunds_received() {
        return funds_received;
    }

    public void setFunds_received(String funds_received) {
        this.funds_received = funds_received;
    }

    public String getTrades_count() {
        return trades_count;
    }

    public void setTrades_count(String trades_count) {
        this.trades_count = trades_count;
    }

    public String getOrigin_amounts() {
        return origin_amounts;
    }

    public void setOrigin_amounts(String origin_amounts) {
        this.origin_amounts = origin_amounts;
    }

    public String getAmounts() {
        return amounts;
    }

    public void setAmounts(String amounts) {
        this.amounts = amounts;
    }

    public String getAlready_volume() {
        return already_volume;
    }

    public void setAlready_volume(String already_volume) {
        this.already_volume = already_volume;
    }

    public String getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(String avg_price) {
        this.avg_price = avg_price;
    }

    @Override
    public String toString() {
        return "Entrust{" +
                "orderid='" + orderid + '\'' +
                ", trade_type='" + trade_type + '\'' +
                ", market='" + market + '\'' +
                ", memberid=" + memberid +
                ", price='" + price + '\'' +
                ", volume='" + volume + '\'' +
                ", origin_volume='" + origin_volume + '\'' +
                ", done_at=" + done_at +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", source='" + source + '\'' +
                ", ord_type='" + ord_type + '\'' +
                ", freezed='" + freezed + '\'' +
                ", state='" + state + '\'' +
                ", origin_freezed='" + origin_freezed + '\'' +
                ", funds_received='" + funds_received + '\'' +
                ", trades_count='" + trades_count + '\'' +
                ", origin_amounts='" + origin_amounts + '\'' +
                ", amounts='" + amounts + '\'' +
                ", already_volume='" + already_volume + '\'' +
                ", avg_price='" + avg_price + '\'' +
                '}';
    }
}
