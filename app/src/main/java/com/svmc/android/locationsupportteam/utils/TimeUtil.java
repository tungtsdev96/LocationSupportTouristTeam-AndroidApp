package com.svmc.android.locationsupportteam.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by buinam on 11/27/17.
 */

public class TimeUtil {

    private static final int SECOND = 1000;
    private static final int MINUTE = 60 * SECOND;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
    private static final int DAY2 = 48 * HOUR;

    public static String setTextTimeNew(long timeNew) {

        long ms = Calendar.getInstance().getTimeInMillis() - timeNew;

        StringBuffer text = new StringBuffer("");

        if (ms > DAY2) return convertTimeToDate(timeNew);

        else if (ms > DAY) return "1 ngày trước";

        else if (ms > HOUR) return (text.append(ms / HOUR).append(" giờ trước")).toString();

        else if (ms > MINUTE) return (text.append(ms / MINUTE).append(" phút trước")).toString();

        else return (text.append(ms / SECOND).append(" giây trước")).toString();

    }

    public static String getTime(int hour, int minute) {
        String s = "";

        if (hour < 10) {
            s += "0" + hour;
        } else {
            s += hour;
        }

        s += ":";

        if (minute < 10) {
            s += "0" + minute;
        } else {
            s += minute;
        }

        return s;
    }

    public static String convertTimeToDate(long time) {

        try {

            return DateFormat.format("dd/MM/yyyy", new Date(time)).toString();

        } catch (Exception ex) {
            return "" + ex;
        }

    }


    public static String formatDuration(int duration) {

        return DateFormat.format("mm:ss", new Date(duration)).toString();
    }

    public static long formatStringtoLong(String time) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date parseDate = null;
        try {
            parseDate = f.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = parseDate.getTime();

        return milliseconds;

    }
}
