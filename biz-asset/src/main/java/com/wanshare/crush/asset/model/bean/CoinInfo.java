package com.wanshare.crush.asset.model.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author wangdunwei
 * @date 2018/8/22
 */
public class CoinInfo implements Serializable{

    /**
     * id : 1
     * logo : string
     * shortName : string
     * fullName : string
     * confirmationNumber : 0
     * minWithdraw : string
     * maxWithdraw : string
     * withdrawFee : string
     * minFee : string
     * maxWithdrawOneDay : string
     */

    @SerializedName(value = "id",alternate = {"coinId"})
    private String id;
    private String logo;
    private String shortName;
    private String fullName;
    /**
     * 区块确认数量
     */
    private int confirmationNumber;
    /**
     * 充币最小金额
     */
    private String minRechargeNumber;
    /**
     * 单笔最小提币数量
     */
    private String minWithdraw;
    /**
     * 单笔最大提币数量
     */
    private String maxWithdraw;
    /**
     * 提币手续费率
     */
    private String withdrawFee;
    /**
     * 提币最小手续费
     */
    private String minFee;
    /**
     * 每日提币最大数量
     */
    private String maxWithdrawOneDay;

    public String getId() {
        if(id==null){
            return "";
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShortName() {
        if(shortName==null){
            return "";
        }
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        if(fullName==null){
            return "";
        }
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getMinWithdraw() {
        return minWithdraw;
    }

    public void setMinWithdraw(String minWithdraw) {
        this.minWithdraw = minWithdraw;
    }

    public String getMaxWithdraw() {
        return maxWithdraw;
    }

    public void setMaxWithdraw(String maxWithdraw) {
        this.maxWithdraw = maxWithdraw;
    }

    public String getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(String withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public String getMinFee() {
        return minFee;
    }

    public void setMinFee(String minFee) {
        this.minFee = minFee;
    }

    public String getMaxWithdrawOneDay() {
        return maxWithdrawOneDay;
    }

    public void setMaxWithdrawOneDay(String maxWithdrawOneDay) {
        this.maxWithdrawOneDay = maxWithdrawOneDay;
    }

    public String getMinRechargeNumber() {
        return minRechargeNumber;
    }

    public void setMinRechargeNumber(String minRechargeNumber) {
        this.minRechargeNumber = minRechargeNumber;
    }
}
