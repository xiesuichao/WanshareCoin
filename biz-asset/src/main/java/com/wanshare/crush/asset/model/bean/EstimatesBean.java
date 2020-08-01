package com.wanshare.crush.asset.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richard on 2018/9/21
 */
public class EstimatesBean implements Parcelable {

    /**
     * usdt : 0
     * btc : 0
     * cny : 0
     */

    private int usdt;
    private int btc;
    private int cny;

    protected EstimatesBean(Parcel in) {
        usdt = in.readInt();
        btc = in.readInt();
        cny = in.readInt();
    }

    public static final Creator<EstimatesBean> CREATOR = new Creator<EstimatesBean>() {
        @Override
        public EstimatesBean createFromParcel(Parcel in) {
            return new EstimatesBean(in);
        }

        @Override
        public EstimatesBean[] newArray(int size) {
            return new EstimatesBean[size];
        }
    };

    public int getUsdt() {
        return usdt;
    }

    public void setUsdt(int usdt) {
        this.usdt = usdt;
    }

    public int getBtc() {
        return btc;
    }

    public void setBtc(int btc) {
        this.btc = btc;
    }

    public int getCny() {
        return cny;
    }

    public void setCny(int cny) {
        this.cny = cny;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(usdt);
        dest.writeInt(btc);
        dest.writeInt(cny);
    }
}
