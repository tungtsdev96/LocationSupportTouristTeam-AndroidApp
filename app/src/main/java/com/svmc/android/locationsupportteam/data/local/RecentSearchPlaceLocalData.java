package com.svmc.android.locationsupportteam.data.local;

import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchPlaceCache;
import com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete.Prediction;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;

import java.util.Date;
import java.util.List;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by TungTS on 5/7/2019
 */

public class RecentSearchPlaceLocalData extends BaseDataLocal {

    public RecentSearchPlaceLocalData() {
        super();
    }

    public void addRecenSearchPlace(Place place) {
        RecentedSearchPlaceCache cache =
                new RecentedSearchPlaceCache(place.getPlaceId(), place.getName(), place.getVicinity(), new Date().getTime());

        if (!checkExits(place.getPlaceId())) {
            realm.beginTransaction();
            realm.copyToRealm(cache);
            realm.commitTransaction();
        }
    }

    public void addRecenSearchPlace(Prediction prediction) {
        RecentedSearchPlaceCache cache =
                new RecentedSearchPlaceCache(prediction.getPlaceId(), prediction.getStructuredFormatting().getMainText(), prediction.getStructuredFormatting().getSecondaryText(), new Date().getTime());

        if (!checkExits(prediction.getPlaceId())) {
            realm.beginTransaction();
            realm.copyToRealm(cache);
            realm.commitTransaction();
        }
    }

    /***
     * check exits cache
     * @param placeId
     * @return
     */
    public boolean checkExits(String placeId) {
        RecentedSearchPlaceCache cache = realm.where(RecentedSearchPlaceCache.class)
                .equalTo("placeId", placeId).findFirst();
        if (cache != null) {
            realm.beginTransaction();
            cache.setCreatedTime(new Date().getTime());
            realm.commitTransaction();
            return true;
        }
        return false;
    }


    /***
     * Get list recented cache
     * @return
     */
    public List<RecentedSearchPlaceCache> getPlacesRescenedSearch() {
        final RealmResults<RecentedSearchPlaceCache> results = realm
                .where(RecentedSearchPlaceCache.class)
                .sort("createdTime", Sort.DESCENDING)
                .limit(6)
                .findAll();
        return results;
    }
}
