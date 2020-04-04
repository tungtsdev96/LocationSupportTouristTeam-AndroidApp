package com.svmc.android.locationsupportteam.data.local;

import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by TungTS on 4/23/2019
 */

public class UserLocalData extends BaseDataLocal {

    public UserLocalData() {
        super();
    }

    public void saveRecentSearch(ArrayList<User> users) {
        ArrayList<RecentedSearchUserCache> caches = new ArrayList<>();
        for (User user: users) {
            caches.add(new RecentedSearchUserCache(user.getUserId(), user.getEmail(), user.getUsername(), user.getDisplayName(), (new Date()).getTime()));
        }

        realm.beginTransaction();
        realm.insert(caches);
        realm.commitTransaction();
    }

    /***
     * Add user to cache
     * @param user
     */
    public void addRecentSearch(User user) {
        RecentedSearchUserCache cache = new RecentedSearchUserCache(user.getUserId(), user.getEmail(), user.getUsername(), user.getDisplayName(), (new Date()).getTime());
        if (!checkExits(user.getUserId())) {
            realm.beginTransaction();
            realm.copyToRealm(cache);
            realm.commitTransaction();
        }
    }

    /***
     * Add user to cache
     * @param user
     */
    public void addRecentSearch(MemberOfRoomLocation user) {
        RecentedSearchUserCache cache = new RecentedSearchUserCache(user.getUserId(), user.getEmail(), "", user.getDisplayName(), (new Date()).getTime());
        if (!checkExits(user.getUserId())) {
            realm.beginTransaction();
            realm.copyToRealm(cache);
            realm.commitTransaction();
        }
    }

    /***
     * check exits cache
     * @param userId
     * @return
     */
    public boolean checkExits(String userId) {
        RecentedSearchUserCache cache = realm.where(RecentedSearchUserCache.class)
                .equalTo("userId", userId).findFirst();
        if (cache != null) {
            realm.beginTransaction();
            cache.setTime(new Date().getTime());
            realm.commitTransaction();
            return true;
        }
        return false;
    }

    /***
     * Get list user cache
     * @return
     */
    public List<RecentedSearchUserCache> getUsersRescenedSearch() {
        final RealmResults<RecentedSearchUserCache> results = realm
                .where(RecentedSearchUserCache.class)
                .sort("time", Sort.DESCENDING)
                .limit(6)
                .findAll();
        return results;
    }

}
