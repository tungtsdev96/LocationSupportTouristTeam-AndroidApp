package com.svmc.android.locationsupportteam.receivers.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.ui.alert.DialogShowAlertRoomActivity;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by TungTS on 5/02/2019
 */

public class NotificationReceived implements OneSignal.NotificationReceivedHandler {

    private Context context;

    public NotificationReceived(Context context) {
        this.context = context;
    }

    @Override
    public void notificationReceived(OSNotification notification) {

        AppPreferencens.getInstance().innit(context);
        boolean isCheckReceiveNotification = AppPreferencens.getInstance().checkReceiveNotification();
        if (!isCheckReceiveNotification) return;

        JSONObject data = notification.payload.additionalData;

        if (data == null) return;

        Log.i("OneSignalExample", "data " + data);

        String type = data.optString("type");

        if (type.equals(RoomLocationNotification.STATUS_SOS) || type.equals(RoomLocationNotification.STATUS_ALERT)) {
            Log.i("OneSignalExample", "alert ");

            if (new Date().getTime() - data.optLong("createdTime") > 3600) {
                return;
            }

            Intent i = DialogShowAlertRoomActivity.innitIntent(context, data.toString());
            context.startActivity(i);
        }

    }

}
