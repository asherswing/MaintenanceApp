package com.asher.maintenance.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CompletedForm implements Parcelable{
    private List<CompletedItem> completedItems;
    private String notes;
    private String date;
    private String author;
    private String deviceId;
    private int formId;

    public CompletedForm(List<CompletedItem> completedItems, String notes, String date, String author, String deviceId, int formId) {
        this.completedItems = completedItems;
        this.notes = notes;
        this.date = date;
        this.author = author;
        this.deviceId = deviceId;
        this.formId = formId;
    }

    public CompletedForm() {
    }

    protected CompletedForm(Parcel in) {
        completedItems = in.createTypedArrayList(CompletedItem.CREATOR);
        notes = in.readString();
        date = in.readString();
        author = in.readString();
        deviceId = in.readString();
        formId = in.readInt();
    }

    public static final Creator<CompletedForm> CREATOR = new Creator<CompletedForm>() {
        @Override
        public CompletedForm createFromParcel(Parcel in) {
            return new CompletedForm(in);
        }

        @Override
        public CompletedForm[] newArray(int size) {
            return new CompletedForm[size];
        }
    };

    public List<CompletedItem> getCompletedItems() {
        return completedItems;
    }

    public void setCompletedItems(List<CompletedItem> completedItems) {
        this.completedItems = completedItems;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(completedItems);
        dest.writeString(notes);
        dest.writeString(date);
        dest.writeString(author);
        dest.writeString(deviceId);
        dest.writeInt(formId);
    }
}
