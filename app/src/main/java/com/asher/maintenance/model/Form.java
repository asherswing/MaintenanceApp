package com.asher.maintenance.model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Form extends RealmObject implements Parcelable{
    private boolean allowsDate;
    private boolean allowsTime;
    private boolean allowsAuthor;
    private boolean allowsNotes;
    private boolean allowsSignature;
    //private List<FormItem> content;
    private String title;
    private String imageUrl;
    @PrimaryKey
    private int id;

    public Form() {
    }

    public Form(int id, boolean allowsDate, boolean allowsTime,
                boolean allowsAuthor, boolean allowsNotes, boolean allowsSignature,
                String title, String imageUrl) {
        this.id = id;
        this.allowsDate = allowsDate;
        this.allowsTime = allowsTime;
        this.allowsAuthor = allowsAuthor;
        this.allowsNotes = allowsNotes;
        this.allowsSignature = allowsSignature;
        //this.content = content;
        this.title = title;
        this.imageUrl = imageUrl;

    }

    protected Form(Parcel in) {
        id = in.readInt();
        allowsDate = in.readByte() != 0;
        allowsTime = in.readByte() != 0;
        allowsSignature = in.readByte() != 0;
        title = in.readString();
        imageUrl = in.readString();
        allowsAuthor = in.readByte() != 0;
        allowsNotes =in.readByte() != 0;
    }

    public static final Creator<Form> CREATOR = new Creator<Form>() {
        @Override
        public Form createFromParcel(Parcel in) {
            return new Form(in);
        }

        @Override
        public Form[] newArray(int size) {
            return new Form[size];
        }
    };

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isAllowsDate() {
        return allowsDate;
    }

    public void setAllowsDate(boolean allowsDate) {
        this.allowsDate = allowsDate;
    }

    public boolean isAllowsTime() {
        return allowsTime;
    }

    public void setAllowsTime(boolean allowsTime) {
        this.allowsTime = allowsTime;
    }

    public boolean isAllowsSignature() {
        return allowsSignature;
    }

    public void setAllowsSignature(boolean allowsSignature) {
        this.allowsSignature = allowsSignature;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (allowsDate ? 1 : 0));
        dest.writeByte((byte) (allowsTime ? 1 : 0));
        dest.writeByte((byte) (allowsSignature ? 1 : 0));
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeByte((byte) (allowsAuthor ? 1 : 0));
        dest.writeByte((byte) (allowsNotes ? 1 : 0));
    }

    public boolean isAllowsAuthor() {
        return allowsAuthor;
    }

    public void setAllowsAuthor(boolean allowsAuthor) {
        this.allowsAuthor = allowsAuthor;
    }

    public boolean isAllowsNotes() {
        return allowsNotes;
    }

    public void setAllowsNotes(boolean allowsNotes) {
        this.allowsNotes = allowsNotes;
    }
}
