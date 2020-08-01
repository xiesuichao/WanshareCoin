package com.wanshare.crush.asset.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richard on 2018/9/21
 */
public class AssetInfoBean implements Parcelable {

    /**
     * total : 5001.00000000
     * balance : 5001.00000000
     * frozen : 0.00000000
     * coinId : 1
     * logo : xxx/xxx.png
     * warning :
     * shortName : test1
     * fullName : TEST1
     * isAllowRecharge : true
     * isAllowWithdraw : true
     */

    private String total;
    private String balance;
    private String frozen;
    private String coinId;
    private String logo;
    private String warning;
    private String shortName;
    private String fullName;
    private boolean isAllowRecharge;
    private boolean isAllowWithdraw;

    protected AssetInfoBean(Parcel in) {
        total = in.readString();
        balance = in.readString();
        frozen = in.readString();
        coinId = in.readString();
        logo = in.readString();
        warning = in.readString();
        shortName = in.readString();
        fullName = in.readString();
        isAllowRecharge = in.readByte() != 0;
        isAllowWithdraw = in.readByte() != 0;
    }

    public static final Creator<AssetInfoBean> CREATOR = new Creator<AssetInfoBean>() {
        @Override
        public AssetInfoBean createFromParcel(Parcel in) {
            return new AssetInfoBean(in);
        }

        @Override
        public AssetInfoBean[] newArray(int size) {
            return new AssetInfoBean[size];
        }
    };

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFrozen() {
        return frozen;
    }

    public void setFrozen(String frozen) {
        this.frozen = frozen;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isIsAllowRecharge() {
        return isAllowRecharge;
    }

    public void setIsAllowRecharge(boolean isAllowRecharge) {
        this.isAllowRecharge = isAllowRecharge;
    }

    public boolean isIsAllowWithdraw() {
        return isAllowWithdraw;
    }

    public void setIsAllowWithdraw(boolean isAllowWithdraw) {
        this.isAllowWithdraw = isAllowWithdraw;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total);
        dest.writeString(balance);
        dest.writeString(frozen);
        dest.writeString(coinId);
        dest.writeString(logo);
        dest.writeString(warning);
        dest.writeString(shortName);
        dest.writeString(fullName);
        dest.writeByte((byte) (isAllowRecharge ? 1 : 0));
        dest.writeByte((byte) (isAllowWithdraw ? 1 : 0));
    }
}
