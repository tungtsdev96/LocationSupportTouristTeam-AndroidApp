package com.svmc.android.locationsupportteam.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

/**
 * Created by TungTS on 5/13/2019
 */

public class VibratorUtils {

    private Vibrator vibrator;
    long[] mVibratePattern = new long[]{0, 400, 800, 600, 800, 800, 800, 1000};
    long[] mVibrateOneShotPattern = new long[]{400, 800, 400};

    public VibratorUtils(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void turnOnOneShot() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("ActionRoomService", "vibrate 26");
            VibrationEffect effect = VibrationEffect.createWaveform(mVibrateOneShotPattern, -1);
            vibrator.vibrate(effect);
        } else {
            //deprecated in API 26
            Log.d("ActionRoomService", "vibrate < 26");
            vibrator.vibrate(mVibrateOneShotPattern, -1);
        }
    }

    public void turnOnVibrator(boolean isRepeat) {

        int repeat = isRepeat ? 0 : -1;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("ActionRoomService", "vibrate 26");
            VibrationEffect effect = VibrationEffect.createWaveform(mVibratePattern, repeat);
            vibrator.vibrate(effect);
        } else {
            //deprecated in API 26
            Log.d("ActionRoomService", "vibrate < 26");
            vibrator.vibrate(mVibratePattern, repeat);
        }
    }

    public void stopVibrator() {
        if(vibrator != null && vibrator.hasVibrator()){
            vibrator.cancel();
        }
    }

}
