package com.svmc.android.locationsupportteam.data.local.entity;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by TungTS on 5/5/2019
 */

public class DBMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        // DynamicRealm exposes an editable schema
        RealmSchema schema = realm.getSchema();

        // Migrate to version 1
        if (oldVersion == 0) {
            schema.create("LocationCache")
                    .addField("lat", double.class)
                    .addField("lng", double.class)
                    .addField("time", long.class)
                    .addField("accuracy", float.class);

            schema.create("RecentedSearchUserCache")
                    .addField("userId", String.class)
                    .addPrimaryKey("userId")
                    .addField("email", String.class)
                    .addField("username", String.class)
                    .addField("displayName", String.class)
                    .addField("time", long.class);

            schema.create("RecentedSearchPlaceCache")
                    .addField("placeId", String.class)
                    .addPrimaryKey("placeId")
                    .addField("mainText", String.class)
                    .addField("secondaryText", String.class)
                    .addField("createdTime", long.class);
            oldVersion++;
        }

        //Migrate to version 2
        if (oldVersion == 1) {
            schema.create("PlaceSaved")
                    .addField("placeId", String.class)
                    .addPrimaryKey("placeId")
                    .addField("jsonPlace", String.class);
            oldVersion++;
        }

        //Migrate to version 3
        if (oldVersion == 2) {
            schema.get("PlaceSaved")
                    .addField("createdTime", long.class)
                    .addField("isSynchronize", boolean.class);
            oldVersion++;
        }
    }

    @Override
    public int hashCode() {
        return DBMigration.class.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if(object == null) {
            return false;
        }
        return object instanceof DBMigration;
    }

}
