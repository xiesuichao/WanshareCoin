package com.wanshare.crush.account.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author wangdunwei
 * @date 2018/6/20
 */
public class Notice implements Parcelable {


    private String _id;
    private String content;
    private String createdAt;
    private String model;
    private String status;
    private String stick;
    private String test;
    private String title;
    private String updatedAt;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStick() {
        return stick;
    }

    public void setStick(String stick) {
        this.stick = stick;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.content);
        dest.writeString(this.createdAt);
        dest.writeString(this.model);
        dest.writeString(this.status);
        dest.writeString(this.stick);
        dest.writeString(this.test);
        dest.writeString(this.title);
        dest.writeString(this.updatedAt);
    }

    public Notice() {
    }

    protected Notice(Parcel in) {
        this._id = in.readString();
        this.content = in.readString();
        this.createdAt = in.readString();
        this.model = in.readString();
        this.status = in.readString();
        this.stick = in.readString();
        this.test = in.readString();
        this.title = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<Notice> CREATOR = new Creator<Notice>() {
        @Override
        public Notice createFromParcel(Parcel source) {
            return new Notice(source);
        }

        @Override
        public Notice[] newArray(int size) {
            return new Notice[size];
        }
    };

    @Override
    public String toString() {
        return "Notice{" +
                "_id='" + _id + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", model='" + model + '\'' +
                ", status='" + status + '\'' +
                ", stick='" + stick + '\'' +
                ", test='" + test + '\'' +
                ", title='" + title + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
