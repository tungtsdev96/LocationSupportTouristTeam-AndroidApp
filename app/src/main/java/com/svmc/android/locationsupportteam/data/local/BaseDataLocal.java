package com.svmc.android.locationsupportteam.data.local;

import com.svmc.android.locationsupportteam.data.local.entity.DBMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TungTS on 5/5/2019
 */

public abstract class BaseDataLocal {

    protected RealmConfiguration config;
    protected Realm realm;

    public BaseDataLocal(){
        config = new RealmConfiguration.Builder()
                .schemaVersion(3) // Must be bumped when the schema changes
                .migration(new DBMigration()) // Migration to run instead of throwing an exception
                .build();
        realm = Realm.getInstance(config);
    }

}
