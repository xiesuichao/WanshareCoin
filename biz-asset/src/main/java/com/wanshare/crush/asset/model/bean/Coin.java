package com.wanshare.crush.asset.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangdunwei
 * @date 2018/8/22
 */
public class Coin implements Parcelable {

    /**
     * id : 0
     * coinId : string
     * remark : string
     * address : string
     */

    //地址id
    private String id;
    private String coinId;
    private String remark;
    private String address;

    public Coin(String id, String coinId, String remark, String address) {
        this.id = id;
        this.coinId = coinId;
        this.remark = remark;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoinId() {
        return coinId;
    }

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.coinId);
        dest.writeString(this.remark);
        dest.writeString(this.address);
    }

    public Coin() {
    }

    protected Coin(Parcel in) {
        this.id = in.readString();
        this.coinId = in.readString();
        this.remark = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<Coin> CREATOR = new Parcelable.Creator<Coin>() {
        @Override
        public Coin createFromParcel(Parcel source) {
            return new Coin(source);
        }

        @Override
        public Coin[] newArray(int size) {
            return new Coin[size];
        }
    };
}
