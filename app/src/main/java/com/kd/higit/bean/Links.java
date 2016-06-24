package com.kd.higit.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by KD on 2016/6/23.
 */
public class Links implements Parcelable {
    public String self;
    public String git;
    public String html;

    public Links() {
    }

    public Links(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(self);
        dest.writeString(git);
        dest.writeString(html);
    }

    public void readFromParcel(Parcel in) {
        self = in.readString();
        git = in.readString();
        html = in.readString();
    }

    public static final Parcelable.Creator<Links> CREATOR = new Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getGit() {
        return git;
    }

    public void setGit(String git) {
        this.git = git;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}
