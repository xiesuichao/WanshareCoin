package com.wanshare.crush.account.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richard on 2018/9/11
 */
public class GeetestVerifyBean implements Parcelable {

    /**
     * geetest_challenge : 9249db207e3af859ecaafe22b5ba2da09o
     * geetest_validate : 5e238d1b5b1ae82dfc55a376384b9d29
     * geetest_seccode : 5e238d1b5b1ae82dfc55a376384b9d29|jordan
     */

    private String geetest_challenge;
    private String geetest_validate;
    private String geetest_seccode;

    public GeetestVerifyBean() {
    }

    public GeetestVerifyBean(String geetest_challenge, String geetest_validate, String geetest_seccode) {
        this.geetest_challenge = geetest_challenge;
        this.geetest_validate = geetest_validate;
        this.geetest_seccode = geetest_seccode;
    }


    protected GeetestVerifyBean(Parcel in) {
        geetest_challenge = in.readString();
        geetest_validate = in.readString();
        geetest_seccode = in.readString();
    }

    public static final Creator<GeetestVerifyBean> CREATOR = new Creator<GeetestVerifyBean>() {
        @Override
        public GeetestVerifyBean createFromParcel(Parcel in) {
            return new GeetestVerifyBean(in);
        }

        @Override
        public GeetestVerifyBean[] newArray(int size) {
            return new GeetestVerifyBean[size];
        }
    };

    public String getGeetest_challenge() {
        return geetest_challenge;
    }

    public void setGeetest_challenge(String geetest_challenge) {
        this.geetest_challenge = geetest_challenge;
    }

    public String getGeetest_validate() {
        return geetest_validate;
    }

    public void setGeetest_validate(String geetest_validate) {
        this.geetest_validate = geetest_validate;
    }

    public String getGeetest_seccode() {
        return geetest_seccode;
    }

    public void setGeetest_seccode(String geetest_seccode) {
        this.geetest_seccode = geetest_seccode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(geetest_challenge);
        dest.writeString(geetest_validate);
        dest.writeString(geetest_seccode);
    }
}
