package com.wanshare.common.biz.account.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Richard on 2018/9/14
 */
public class SecondVerifyParams implements Parcelable {
    private String baseToken;
    private String secondToken;
    private String newPassword;
    private String verifyType;

    public SecondVerifyParams() {
    }


    public String getBaseToken() {
        return baseToken;
    }

    public void setBaseToken(String baseToken) {
        this.baseToken = baseToken;
    }

    public String getSecondToken() {
        return secondToken;
    }

    public void setSecondToken(String secondToken) {
        this.secondToken = secondToken;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType;
    }

    protected SecondVerifyParams(Parcel in) {
        baseToken = in.readString();
        secondToken = in.readString();
        newPassword = in.readString();
        verifyType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(baseToken);
        dest.writeString(secondToken);
        dest.writeString(newPassword);
        dest.writeString(verifyType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SecondVerifyParams> CREATOR = new Creator<SecondVerifyParams>() {
        @Override
        public SecondVerifyParams createFromParcel(Parcel in) {
            return new SecondVerifyParams(in);
        }

        @Override
        public SecondVerifyParams[] newArray(int size) {
            return new SecondVerifyParams[size];
        }
    };
}
