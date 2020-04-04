package com.svmc.android.locationsupportteam.data.local;

import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.data.local.entity.PlaceSaved;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by TungTS on 5/10/2019
 */

public class PlaceSaveController extends BaseDataLocal {

    private Gson mGson;

    public PlaceSaveController() {
        this.mGson = new Gson();
    }

    public boolean addPlace(Place place) {
        if (!checkExits(place.getPlaceId())) {
            realm.beginTransaction();
            realm.copyToRealm(new PlaceSaved(place.getPlaceId(), mGson.toJson(place)));
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    public boolean checkExits(String placeId) {
        return getPlace(placeId) != null;
    }

    public Place getPlace(String placeId) {
        PlaceSaved cache = realm.where(PlaceSaved.class)
                .equalTo("placeId", placeId).findFirst();
        if (cache != null) {
            return mGson.fromJson(cache.getJsonPlace(), Place.class);
        }
        return null;
    }

    public boolean delete(String placeId) {
        final PlaceSaved cache = realm.where(PlaceSaved.class)
                .equalTo("placeId", placeId).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (cache != null) {
                    cache.deleteFromRealm();
                }
            }
        });
        return true;
    }

    public ArrayList<Place> getAllPlaceSaved() {
        RealmResults<PlaceSaved> realmResults = realm.where(PlaceSaved.class).findAll();
        ArrayList<Place> places = new ArrayList<>();
        Gson mGson = new Gson();
        for (PlaceSaved p : realmResults) {
            places.add(mGson.fromJson(p.getJsonPlace(), Place.class));
        }
        return places;
    }

    public ArrayList<Place> getAllToSync() {
        RealmResults<PlaceSaved> realmResults = realm
                .where(PlaceSaved.class)
                .equalTo("isSynchronize", false)
                .findAll();
        ArrayList<Place> places = new ArrayList<>();
        Gson mGson = new Gson();
        for (PlaceSaved p : realmResults) {
            places.add(mGson.fromJson(p.getJsonPlace(), Place.class));
        }
        return places;
    }

    public void updatePlaceSynced(final String placeId) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<PlaceSaved> caches = realm.where(PlaceSaved.class).equalTo("placeId", placeId).findAll();
                caches.setValue("isSynchronize", "true");
            }
        });
    }
}
