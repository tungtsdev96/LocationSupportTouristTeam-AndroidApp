package com.svmc.android.locationsupportteam.listeners;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by TungTS on 6/3/2019
 */

public class ShakeEventListener implements SensorEventListener {

    private final String TAG = "ShakeEventListener";
    private int count = 1;
    private boolean init;
    private Sensor mAccelerometer;
    private SensorManager mSensorManager;
    private float x1, x2, x3;
    private static final float ERROR = (float) 7.0;

    private ShakeCallBack onShakeCallBack;

    public ShakeEventListener() {
    }

    public void innit(Context context) {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void setOnShakeCallBack(ShakeCallBack onShakeCallBack) {
        this.onShakeCallBack = onShakeCallBack;
    }

    public void onResume() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause() {
        mSensorManager.unregisterListener(this);
        onShakeCallBack = null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x,y,z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        if (!init) {
            x1 = x;
            x2 = y;
            x3 = z;
            init = true;
        } else {

            float diffX = Math.abs(x1 - x);
            float diffY = Math.abs(x2 - y);
            float diffZ = Math.abs(x3 - z);

            //Handling ACCELEROMETER Noise
            if (diffX < ERROR) {

                diffX = (float) 0.0;
            }
            if (diffY < ERROR) {
                diffY = (float) 0.0;
            }
            if (diffZ < ERROR) {
                diffZ = (float) 0.0;
            }

            x1 = x;
            x2 = y;
            x3 = z;

            //Horizontal Shake Detected!
            if (diffX > diffY) {
                count = count + 1;
                if (count >= 3) {
                    count = 0;
                    if (onShakeCallBack != null) {
                        onShakeCallBack.onShake();
                    }
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public interface ShakeCallBack {
        void onShake();
    }

}
