package com.svmc.android.locationsupportteam.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.util.Date;

/**
 * Created by TUNGTS on 3/3/2019
 */

public class AppPreferencens {

    private Context context;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Gson mGson;

    private static AppPreferencens instance;
    public static AppPreferencens getInstance() {
        if (instance == null) {
            instance = new AppPreferencens();
        }
        return instance;
    }

    public void innit(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(Constans.KeyPreference.PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        mGson = new Gson();
    }

    public boolean isFirstStartApp() {
        if (sharedPref != null) {
            boolean check = sharedPref.getBoolean(Constans.KeyPreference.IS_FIRST_START_APP, true);
            if (check) {
                editor.putBoolean(Constans.KeyPreference.IS_FIRST_START_APP, !check);
                editor.commit();
            }
            return check;
        }
        return false;
    }

    /**
     * save Current User
     */
    public void setCurrentUser(User user) {
        editor.putString(Constans.KeyPreference.USER, mGson.toJson(user));
        editor.commit();
    }

    public User getUser() {
        String json = sharedPref.getString(Constans.KeyPreference.USER, null);
        if (json != null) {
            return mGson.fromJson(json, User.class);
        }
        return null;
    }

    /**
     * save roomId
     */
    public void setRoomId(String roomIdJoined) {
        editor.putString(Constans.KeyPreference.ROOM_ID_JOINED, roomIdJoined);
        editor.commit();
    }

    public String getRoomId() {
        return sharedPref.getString(Constans.KeyPreference.ROOM_ID_JOINED, null);
    }

    /**
     * save check turn location
     * @return current turn on
     */
    public boolean setTurnOnLocation() {
        boolean isTurn = checkTurnOnLocation();
        editor.putBoolean(Constans.KeyPreference.TURN_ON_LOCATION, !isTurn);
        editor.commit();
        return !isTurn;
    }

    public boolean checkTurnOnLocation() {
        return sharedPref.getBoolean(Constans.KeyPreference.TURN_ON_LOCATION, true);
    }

    /***
     * save and check vibrate
     */
    public boolean setVibrate() {
        boolean isVibrate = checkVibrate();
        editor.putBoolean(Constans.KeyPreference.TURN_VIBRATE, !isVibrate);
        editor.commit();
        return !isVibrate;
    }

    public boolean checkVibrate() {
        return sharedPref.getBoolean(Constans.KeyPreference.TURN_VIBRATE, true);
    }

    /**
     * save and check synchronize
     */
    public boolean setSynchronize() {
        boolean isSynchronize = checkSynchronize();
        editor.putBoolean(Constans.KeyPreference.TURN_SYNCHRONIZE, !isSynchronize);
        editor.commit();
        return !isSynchronize;
    }

    public boolean checkSynchronize() {
        return sharedPref.getBoolean(Constans.KeyPreference.TURN_SYNCHRONIZE, false);
    }

    /**
     * save time sync
     */
    public void setTimeSync(String time) {
        editor.putString(Constans.KeyPreference.TIME_SYNC, time);
        editor.commit();
    }

    public String getTimeSync() {
        return sharedPref.getString(Constans.KeyPreference.TIME_SYNC, "22:00");
    }

    /**
     * list recommend place type
     * s : 1,2,3 <list id>
     */
    public void setListPlaceType(String s) {
        editor.putString(Constans.KeyPreference.LIST_PLACE_TYPE, s);
        editor.commit();
    }

    public String getListPlaceType() {
        return sharedPref.getString(Constans.KeyPreference.LIST_PLACE_TYPE, "0,1,2");
    }

    /**
     * receive notification
     */
    public boolean setReceiveNotification() {
        boolean checkReceiveNoti = checkReceiveNotification();
        editor.putBoolean(Constans.KeyPreference.TURN_ON_NOTIFICATION, !checkReceiveNoti);
        editor.commit();
        return !checkReceiveNoti;
    }

    public boolean checkReceiveNotification() {
        return sharedPref.getBoolean(Constans.KeyPreference.TURN_ON_NOTIFICATION, true);
    }

    public void logout() {
        if (!checkTurnOnLocation()) setTurnOnLocation();
        setListPlaceType("0,1,2");
        setCurrentUser(null);
        setRoomId(null);
        if (!checkVibrate()) setVibrate();
        if (!checkSynchronize()) setSynchronize();
        if (!checkReceiveNotification()) setReceiveNotification();
        setTimeSync("22:00");
    }
}
