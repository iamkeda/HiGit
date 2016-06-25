package com.kd.higit.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KD on 2016/6/25.
 */
public class ReposSearch implements Parcelable{
    public int total_count;
    public boolean incomplete_results;
    public List<Repository> items;

    public ReposSearch() {
    }

    public ReposSearch(Parcel in) {
        readFromParcel(in);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(total_count);
        dest.writeByte((byte)(incomplete_results ? 1 : 0));
        dest.writeList(items);
    }

    public void readFromParcel(Parcel in) {
        total_count = in.readInt();
        incomplete_results = (in.readByte() != 0);
        items = new ArrayList<>();
        in.readList(items, Repository.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReposSearch> CREATOR = new Creator<ReposSearch>() {
        @Override
        public ReposSearch createFromParcel(Parcel source) {
            return new ReposSearch(source);
        }

        @Override
        public ReposSearch[] newArray(int size) {
            return new ReposSearch[size];
        }
    };

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public boolean isIncomplete_results() {
        return incomplete_results;
    }

    public void setIncomplete_results(boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    public List<Repository> getItems() {
        return items;
    }

    public void setItems(List<Repository> items) {
        this.items = items;
    }
}
