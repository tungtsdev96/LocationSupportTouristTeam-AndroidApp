package com.svmc.android.locationsupportteam.service;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;

import org.json.JSONObject;

import java.math.BigInteger;

/**
 * Created by TungTS on 5/13/2019
 */

public class NotificationAlertExtenderService extends NotificationExtenderService {

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {

        AppPreferencens.getInstance().innit(getApplicationContext());
        boolean isReceiveNotification = AppPreferencens.getInstance().checkReceiveNotification();

        // check user recei notification
        if (!isReceiveNotification) return true;

        JSONObject data = notification.payload.additionalData;

        if (data == null) return true;

        String type = data.optString("type");

        // Read Properties from result
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                // Sets the background notification color to Red on Android 5.0+ devices.
                return builder
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setColor(new BigInteger("00a680", 16).intValue());
            }
        };

        if (type.equals(RoomLocationNotification.STATUS_SOS) || type.equals(RoomLocationNotification.STATUS_ALERT)) {
            Log.d("OneSignalExample__", "no display");
            return true;
        }

        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        Log.d("OneSignalExample__", "Notification displayed with id: " + displayedResult.androidNotificationId);

        return false;
    }

}
