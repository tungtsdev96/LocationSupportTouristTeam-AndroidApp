package com.svmc.android.locationsupportteam.manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.svmc.android.locationsupportteam.receivers.syncdata.SyncDataReceiver;

import java.util.Calendar;

/**
 * Created by TUNGTS on 6/6/2019
 */

public class AlarmManagerSync {

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;

    private static AlarmManagerSync alarmManagerSync;

    private AlarmManagerSync() {
    }

    public static AlarmManagerSync getInstance() {
        if (alarmManagerSync == null) {
            alarmManagerSync = new AlarmManagerSync();
        }
        return alarmManagerSync;
    }

    public void innit(Context context) {
        this.context = context;
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void innitTime(int hour, int minute) {
        Log.d("SyncDataReceiver", hour + " " + minute);
        Intent intent = new Intent(context, SyncDataReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Set the alarm to start at approximately
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        // With setInexactRepeating(), you have to use one of the AlarmManager interval
        // constants--in this case, AlarmManager.INTERVAL_DAY.
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }

    public void cancel() {
        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

}
