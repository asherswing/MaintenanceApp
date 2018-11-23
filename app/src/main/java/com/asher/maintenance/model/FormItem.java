package com.asher.maintenance.model;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FormItem extends RealmObject {
    private String item;
    private String answers;
    private boolean allowsNotes;
    private boolean allowsSignature;
    private int formId;

    public FormItem(String item, String answers, boolean allowsNotes, boolean allowsSignature, int formId) {
        this.item = item;
        this.answers = answers;
        this.allowsNotes = allowsNotes;
        this.allowsSignature = allowsSignature;
        this.formId = formId;
    }

    public FormItem() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isAllowsNotes() {
        return allowsNotes;
    }

    public void setAllowsNotes(boolean allowsNotes) {
        this.allowsNotes = allowsNotes;
    }

    public boolean isAllowsSignature() {
        return allowsSignature;
    }

    public void setAllowsSignature(boolean allowsSignature) {
        this.allowsSignature = allowsSignature;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
}
