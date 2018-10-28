package com.asher.maintenance;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MaintenanceApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("maintenance.realm")
                .schemaVersion(0)
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
