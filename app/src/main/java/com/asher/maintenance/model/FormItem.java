package com.asher.maintenance.model;

import java.util.HashMap;
import java.util.List;

import io.realm.RealmObject;

public class FormItem extends RealmObject {
    private String item;
    private HashMap<String, String> answers;
    private boolean allowsNotes;
    private boolean allowsSignature;
    private int formId;

    public FormItem(String item, HashMap<String, String> answers, boolean allowsNotes, boolean allowsSignature, int formId) {
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

    public HashMap<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<String, String> answers) {
        this.answers = answers;
    }
}
