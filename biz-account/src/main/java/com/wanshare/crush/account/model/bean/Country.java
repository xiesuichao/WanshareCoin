package com.wanshare.crush.account.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangdunwei
 * @date 2018/5/10
 */
public class Country implements Parcelable {

    /**
     * cn : 中国大陆
     * en : China
     * key : CN
     * no : 86
     * origin : 中国大陆
     */

    private String cn;
    private String en;
    private String key;
    private String no;
    private String origin;
    private String pinyin;
    private int id;


    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cn);
        dest.writeString(this.en);
        dest.writeString(this.key);
        dest.writeString(this.no);
        dest.writeString(this.origin);
        dest.writeString(this.pinyin);
        dest.writeInt(this.id);
    }

    public Country() {
    }

    protected Country(Parcel in) {
        this.cn = in.readString();
        this.en = in.readString();
        this.key = in.readString();
        this.no = in.readString();
        this.origin = in.readString();
        this.pinyin = in.readString();
        this.id = in.readInt();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
