package com.wanshare.crush.asset.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangdunwei
 * @date 2018/8/24
 */
public class AssetRecord implements Parcelable {
    /**
     * address : string
     * amount : string
     * coinId : 6466654
     * completedAt : 2018-03-08 22:39
     * confirmationNumber : 30
     * confirmedTimes : 12
     * id : 6466654
     * logo : string
     * remark : 丑
     * shortName : eth
     * submittedAt : 2018-03-08 22:39
     * txid : string
     * withdrawStatus : successed
     * status : successed
     */

    private String address;
    private String amount;
    private String coinId;
    private String completedAt;
    //确认总次数
    private int confirmationNumber;
    //已确认次数
    private int confirmedTimes;
    private String id;
    private String logo;
    private String remark;
    private String shortName;
    private String submittedAt;
    private String txid;
    private String status;
    private String fullName;
    //提币的手续费
    private String fee;

    public AssetRecord(String address, String amount, String coinId, String completedAt, int confirmationNumber,
                       int confirmedTimes, String id, String logo, String remark, String shortName,
                       String submittedAt, String txid, String status, String fullName, String fee) {
        this.address = address;
        this.amount = amount;
        this.coinId = coinId;
        this.completedAt = completedAt;
        this.confirmationNumber = confirmationNumber;
        this.confirmedTimes = confirmedTimes;
        this.id = id;
        this.logo = logo;
        this.remark = remark;
        this.shortName = shortName;
        this.submittedAt = submittedAt;
        this.txid = txid;
        this.status = status;
        this.fullName = fullName;
        this.fee = fee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(int confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public int getConfirmedTimes() {
        return confirmedTimes;
    }

    public void setConfirmedTimes(int confirmedTimes) {
        this.confirmedTimes = confirmedTimes;
    }

    public String getId() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(String submittedAt) {
        this.submittedAt = submittedAt;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.amount);
        dest.writeString(this.coinId);
        dest.writeString(this.completedAt);
        dest.writeInt(this.confirmationNumber);
        dest.writeInt(this.confirmedTimes);
        dest.writeString(this.id);
        dest.writeString(this.logo);
        dest.writeString(this.remark);
        dest.writeString(this.shortName);
        dest.writeString(this.submittedAt);
        dest.writeString(this.txid);
        dest.writeString(this.status);
        dest.writeString(this.fullName);
        dest.writeString(this.fee);
    }

    public AssetRecord() {
    }

    protected AssetRecord(Parcel in) {
        this.address = in.readString();
        this.amount = in.readString();
        this.coinId = in.readString();
        this.completedAt = in.readString();
        this.confirmationNumber = in.readInt();
        this.confirmedTimes = in.readInt();
        this.id = in.readString();
        this.logo = in.readString();
        this.remark = in.readString();
        this.shortName = in.readString();
        this.submittedAt = in.readString();
        this.txid = in.readString();
        this.status = in.readString();
        this.fullName = in.readString();
        this.fee = in.readString();
    }

    public static final Parcelable.Creator<AssetRecord> CREATOR = new Parcelable.Creator<AssetRecord>() {
        @Override
        public AssetRecord createFromParcel(Parcel source) {
            return new AssetRecord(source);
        }

        @Override
        public AssetRecord[] newArray(int size) {
            return new AssetRecord[size];
        }
    };
}
