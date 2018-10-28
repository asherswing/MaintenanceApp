package com.asher.maintenance.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CompletedItem implements Parcelable{
    private String item;
    private String answer;

    public CompletedItem(String item, String answer) {
        this.item = item;
        this.answer = answer;
    }

    public CompletedItem() {
    }

    protected CompletedItem(Parcel in) {
        item = in.readString();
        answer = in.readString();
    }

    public static final Creator<CompletedItem> CREATOR = new Creator<CompletedItem>() {
        @Override
        public CompletedItem createFromParcel(Parcel in) {
            return new CompletedItem(in);
        }

        @Override
        public CompletedItem[] newArray(int size) {
            return new CompletedItem[size];
        }
    };

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item);
        dest.writeString(answer);
    }
}
