package com.asher.maintenance.realm;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.asher.maintenance.model.Form;
import com.asher.maintenance.model.FormItem;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //find all objects
    public RealmResults<Form> getForms() {
        return realm.where(Form.class).findAll();
    }

    //query a single item with the given id
    public Form getForm(String id) {
        return realm.where(Form.class).equalTo("id", Integer.valueOf(id)).findFirst();
    }

    public void deleteForms(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void insertForm(Form form){
        realm.beginTransaction();
        realm.copyToRealm(form);
        realm.commitTransaction();
    }


    //find all objects
    public RealmResults<FormItem> getFormItems() {
        return realm.where(FormItem.class).findAll();
    }

    //query a single item with the given id
    public RealmResults<FormItem> getFormItemsById(int id) {
        return realm.where(FormItem.class)
                .equalTo("formId", id).findAll();
    }

    public void insertFormItem(FormItem formItem){
        realm.beginTransaction();
        realm.copyToRealm(formItem);
        realm.commitTransaction();
    }

    public void deleteFormItemsById(final int id){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<FormItem> rows = realm.where(FormItem.class)
                        .equalTo("formId",id).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }


}
