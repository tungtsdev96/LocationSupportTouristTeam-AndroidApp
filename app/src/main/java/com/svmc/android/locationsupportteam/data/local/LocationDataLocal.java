package com.svmc.android.locationsupportteam.data.local;

import android.location.Location;

import com.svmc.android.locationsupportteam.data.local.entity.DBMigration;
import com.svmc.android.locationsupportteam.data.local.entity.LocationCache;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by TungTS on 4/25/2019
 */

public class LocationDataLocal extends BaseDataLocal{

    public LocationDataLocal() {
        super();
    }

    public void addLocation(Location location) {
        realm.beginTransaction();
        realm.copyToRealm(new LocationCache(location.getLatitude(), location.getLongitude(), location.getAccuracy(), new Date().getTime()));
        realm.commitTransaction();
    }

    public LocationCache getLastLocationCache() {
        final RealmResults<LocationCache> result = realm
                .where(LocationCache.class)
                .sort("time", Sort.DESCENDING)
                .limit(1)
                .findAll();
        return result.first(null);
    }

    public ArrayList<LocationPoint> getLastPointCache() {
        RealmResults<LocationCache> results = realm
                .where(LocationCache.class)
                .sort("time", Sort.DESCENDING)
                .limit(15)
                .findAll();

        ArrayList<LocationPoint> points = new ArrayList<>();
        for (LocationCache p : results) {
            points.add(new LocationPoint(p.getLat(), p.getLng(), p.getTime()));
        }

        return points;
    }

}
