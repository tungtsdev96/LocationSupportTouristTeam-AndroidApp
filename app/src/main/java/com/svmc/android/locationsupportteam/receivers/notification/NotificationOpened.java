package com.svmc.android.locationsupportteam.receivers.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.ui.notification.NotificationActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.utils.Constans;

import org.json.JSONObject;

/**
 * Created by TungTS on 5/2/2019
 */

public class NotificationOpened implements OneSignal.NotificationOpenedHandler {

    private Context context;
    JSONObject data;

    public NotificationOpened(Context context) {
        this.context = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {

        OSNotificationAction.ActionType actionType = result.action.type;
        data = result.notification.payload.additionalData;

        if (data == null) return;

        Log.i("OneSignalExample_", "data " + data);
        String type = data.optString("type");

        switch (type) {
            case RoomLocationNotification.STATUS_INVITED:
                Log.i("OneSignalExample_", "invited" );
                openUINotifications();
                break;
            case RoomLocationNotification.STATUS_SOS:
                Log.i("OneSignalExample_", "sos" );
                openUISOS();
                break;
            case RoomLocationNotification.STATUS_ALERT:
                Log.i("OneSignalExample_", "alert" );
                openUISOS();
                break;
            case RoomLocationNotification.STATUS_SHARE_PLACE:
                Log.i("OneSignalExample_", "share place");
//                openUIShowPlaceShare();
                openUINotifications();
                break;
        }
    }

    private void openUIShowPlaceShare() {
        ParamsPostNotification postNotification = new Gson().fromJson(data.toString(), ParamsPostNotification.class);

        Intent i = RoomLocationActivity.innitIntent(
                context,
                RoomLocationActivity.DETAIL_ROOM,
                new RoomLocation(postNotification.getRoom().getRoomId(), postNotification.getRoom().getNameRoom()),
                postNotification.getType(),
                postNotification.getPoint(),
                postNotification.getSender().getUserId(),
                postNotification.getUrlImage());
        context.startActivity(i);
    }

    private void openUISOS() {
        Intent i = new Intent(this.context, RoomLocationActivity.class);
        i.putExtra(Constans.KeyBundle.CURRENT_FRAG_ON_ROOM_LOCATION, 1);
        i.putExtra(Constans.KeyBundle.ROOM_LOCATION, data.toString());
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void openUINotifications() {
        Intent intent = new Intent(this.context, NotificationActivity.class);
        intent.putExtra(Constans.KeyBundle.ROOM_LOCATION, data.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}

