package com.svmc.android.locationsupportteam;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.onesignal.OneSignal;
import com.svmc.android.locationsupportteam.manager.AlarmManagerSync;
import com.svmc.android.locationsupportteam.receivers.notification.NotificationOpened;
import com.svmc.android.locationsupportteam.receivers.notification.NotificationReceived;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;

import io.realm.Realm;

/**
 * Created by TUNGTS on 2/23/2019
 */

public class LocationSupportTeamApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // innit App Preference
        AppPreferencens.getInstance().innit(getApplicationContext());

        Realm.init(this);

//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new NotificationOpened(getApplicationContext()))
                .setNotificationReceivedHandler(new NotificationReceived(getApplicationContext()))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // innit sync Data
        AlarmManagerSync.getInstance().innit(getApplicationContext());

    }
}
