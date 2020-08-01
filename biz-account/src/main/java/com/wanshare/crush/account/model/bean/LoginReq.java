package com.wanshare.crush.account.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Richard on 2018/9/11
 */
public class LoginReq implements Parcelable {


    /**
     * challenge : string
     * email : hello.world@email.com
     * password : ABCD1234$#@!
     * seccode : string
     * validate : string
     */

    private String challenge;
    private String email;
    private String password;
    private String seccode;
    private String validate;

    public LoginReq() {
    }

    protected LoginReq(Parcel in) {
        challenge = in.readString();
        email = in.readString();
        password = in.readString();
        seccode = in.readString();
        validate = in.readString();
    }

    public static final Creator<LoginReq> CREATOR = new Creator<LoginReq>() {
        @Override
        public LoginReq createFromParcel(Parcel in) {
            return new LoginReq(in);
        }

        @Override
        public LoginReq[] newArray(int size) {
            return new LoginReq[size];
        }
    };

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSeccode() {
        return seccode;
    }

    public void setSeccode(String seccode) {
        this.seccode = seccode;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(challenge);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(seccode);
        dest.writeString(validate);
    }
}
