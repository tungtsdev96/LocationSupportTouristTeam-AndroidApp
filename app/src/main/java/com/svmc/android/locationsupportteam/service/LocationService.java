package com.svmc.android.locationsupportteam.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.ResponseBody;


/**
 * Created by TUNGTS on 4/10/2019
 */

public class LocationService extends Service implements LocationListener, GpsStatus.Listener {

    public final int MAX_OLD_LOCATION = 10 * 1000; // 10 seconds
    public final int MAX_ACCURANCY = 50; // range 10m -> 100m  < recommend 10 - 20m >
    public final int INTERVAL_TIME_TRACKING = 10000; // 10 seconds
    public final int INTERVAL_DISTANCE_TRACKING = 10; // 10 meter

    public static final String LOG_TAG = LocationService.class.getSimpleName();
    public static final String TAG = "Tracking";

    private final LocationServiceBinder binder = new LocationServiceBinder();
    boolean isLocationManagerUpdatingLocation;

    ArrayList<Location> locationList;

    ArrayList<Location> oldLocationList;
    ArrayList<Location> noAccuracyLocationList;
    ArrayList<Location> inaccurateLocationList;
    ArrayList<Location> kalmanNGLocationList;

    boolean isLogging = true;

    float currentSpeed = 0.0f; // meters/second

    KalmanLatLong kalmanFilter;
    long runStartTimeInMillis;

    ArrayList<Integer> batteryLevelArray;
    ArrayList<Float> batteryLevelScaledArray;
    int batteryScale;
    int gpsCount;

    public LocationService() {

    }

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate ");
        isLocationManagerUpdatingLocation = false;
        locationList = new ArrayList<>();
        noAccuracyLocationList = new ArrayList<>();
        oldLocationList = new ArrayList<>();
        inaccurateLocationList = new ArrayList<>();
        kalmanNGLocationList = new ArrayList<>();
        kalmanFilter = new KalmanLatLong(3);

        isLogging = true;

        batteryLevelArray = new ArrayList<>();
        batteryLevelScaledArray = new ArrayList<>();
//        registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public int onStartCommand(Intent i, int flags, int startId) {
        super.onStartCommand(i, flags, startId);
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind ");
        return binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(LOG_TAG, "onRebind ");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(LOG_TAG, "onUnbind ");
        return true;
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy ");
    }

    //This is where we detect the app is being killed, thus stop service.
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d(LOG_TAG, "onTaskRemoved ");
        this.stopUpdatingLocation();
        stopSelf();
    }

    /***
     * Stop tracking
     */
    public void stopUpdatingLocation(){
        if(this.isLocationManagerUpdatingLocation == true){
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationManager.removeUpdates(this);
            isLocationManagerUpdatingLocation = false;
            updateLastLocationToDatbase();
//            EventBus.getDefault().post(new EventPostWhenStopTracking(true));
        }
        stopSelf();
    }

    /***
     * update last location + user offline
     */
    private void updateLastLocationToDatbase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            ApiFactory.getApiUser().updateOnOffLine(user.getUid(), false).enqueue(new BaseCallBack<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody result) {

                }

                @Override
                public void onFailure(Throwable t) {

                }
            });
        }
    }

    /**
     * Binder class
     * @author Takamitsu Mizutori
     *
     */
    public class LocationServiceBinder extends Binder {
        public LocationService getService() {
            return LocationService.this;
        }
    }

    /* LocationListener implemenation */
    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            notifyLocationProviderStatusUpdated(false);
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            notifyLocationProviderStatusUpdated(true);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            if (status == LocationProvider.OUT_OF_SERVICE) {
                notifyLocationProviderStatusUpdated(false);
            } else {
                notifyLocationProviderStatusUpdated(true);
            }
        }
    }

    private void notifyLocationProviderStatusUpdated(boolean isLocationProviderAvailable) {
        //Broadcast location provider status change here
        if (isLocationProviderAvailable) {
            Log.d(LOG_TAG, "isLocationProviderAvailable");
            return;
        }
        Log.d(LOG_TAG, "isLocationProvider Not Available");
    }

    /* GpsStatus.Listener implementation */
    public void onGpsStatusChanged(int event) {

    }

    public void startLogging(){
        isLogging = true;
    }

    public void stopLogging(){
        if (locationList.size() > 1 && batteryLevelArray.size() > 1){
            long currentTimeInMillis = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
            long elapsedTimeInSeconds = (currentTimeInMillis - runStartTimeInMillis) / 1000;
            float totalDistanceInMeters = 0;
            for(int i = 0; i < locationList.size() - 1; i++){
                totalDistanceInMeters +=  locationList.get(i).distanceTo(locationList.get(i + 1));
            }
            int batteryLevelStart = batteryLevelArray.get(0).intValue();
            int batteryLevelEnd = batteryLevelArray.get(batteryLevelArray.size() - 1).intValue();

            float batteryLevelScaledStart = batteryLevelScaledArray.get(0).floatValue();
            float batteryLevelScaledEnd = batteryLevelScaledArray.get(batteryLevelScaledArray.size() - 1).floatValue();

//            saveLog(elapsedTimeInSeconds, totalDistanceInMeters, gpsCount, batteryLevelStart, batteryLevelEnd, batteryLevelScaledStart, batteryLevelScaledEnd);
        }
//        isLogging = false;
    }

    /***
     * start update location
     */
    public void startUpdatingLocation() {
        if(this.isLocationManagerUpdatingLocation == false){
            isLocationManagerUpdatingLocation = true;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                runStartTimeInMillis = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
            } else {
                runStartTimeInMillis = System.currentTimeMillis();
            }

            locationList.clear();

            oldLocationList.clear();
            noAccuracyLocationList.clear();
            inaccurateLocationList.clear();
            kalmanNGLocationList.clear();

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            //Exception thrown when GPS or Network provider were not available on the user's device.
            try {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE); //setAccuracyは内部では、https://stackoverflow.com/a/17874592/1709287の用にHorizontalAccuracyの設定に変換されている。
                criteria.setPowerRequirement(Criteria.POWER_HIGH);
                criteria.setAltitudeRequired(false);
                criteria.setSpeedRequired(true);
                criteria.setCostAllowed(true);
                criteria.setBearingRequired(false);

                //API level 9 and up
                criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
                criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
                //criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
                //criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);

                Integer gpsFreqInMillis = INTERVAL_TIME_TRACKING;
                Integer gpsFreqInDistance = INTERVAL_DISTANCE_TRACKING;  // in meters

                locationManager.addGpsStatusListener(this);

                locationManager.requestLocationUpdates(gpsFreqInMillis, gpsFreqInDistance, criteria, this, null);

                /* Battery Consumption Measurement */
                gpsCount = 0;
                batteryLevelArray.clear();
                batteryLevelScaledArray.clear();

                /* Tracking online user */
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
//                    UserLocation userLocation = new UserLocation(user.getUid(), true, (new Date()).getTime());
                    Log.d(LOG_TAG, "tracking online");
                    trackingOnline(user);
                }

            } catch (IllegalArgumentException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            } catch (SecurityException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            } catch (RuntimeException e) {
                Log.e(LOG_TAG, e.getLocalizedMessage());
            }
        }
    }

    /**
     * tracking online
     * @param user
     */
    private void trackingOnline(FirebaseUser user) {
        ApiFactory.getApiUser().updateOnOffLine(user.getUid(), true).enqueue(new BaseCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public Location getCurrentLocation() {
        if (locationList != null && !locationList.isEmpty()) {
            return locationList.get(locationList.size() - 1);
        }
        return null;
    }

    @Override
    public void onLocationChanged(final Location newLocation) {
        Log.d(LOG_TAG, "(" + newLocation.getLatitude() + "," + newLocation.getLongitude() + ") ");

        if (!isLogging) return;

        gpsCount++;

        boolean isOk = filterAndAddLocation(newLocation);

        if (isOk) {
            Log.d(LOG_TAG, "(" + newLocation.getLatitude() + "," + newLocation.getLongitude() + ") " + newLocation.getAccuracy() + " "  + isOk);
            Intent intent = new Intent("LocationUpdated");
            intent.putExtra("location", newLocation);
            LocalBroadcastManager.getInstance(this.getApplication()).sendBroadcast(intent);

            boolean isTurnOnLocation = AppPreferencens.getInstance().checkTurnOnLocation();
            if (isTurnOnLocation) trackingToDb(newLocation);
        }
    }

    /***
     * update location user on database
     * @param newLocation
     */
    private void trackingToDb(Location newLocation) {
        LocationDataLocal locationDataLocal = new LocationDataLocal();
        locationDataLocal.addLocation(newLocation);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            HashMap<String, Object> childUpdate = new HashMap<>();
            childUpdate.put("userId", user.getUid());
            childUpdate.put("lat", newLocation.getLatitude());
            childUpdate.put("lng", newLocation.getLongitude());
            childUpdate.put("time", (new Date()).getTime());
            FirebaseUtils.getFirebaseDatabase().getReference().child("location_user").child(user.getUid()).updateChildren(childUpdate);
        }
    }

    private long getLocationAge(Location newLocation){
        long locationAge;
        if(android.os.Build.VERSION.SDK_INT >= 17) {
            long currentTimeInMilli = (long)(SystemClock.elapsedRealtimeNanos() / 1000000);
            long locationTimeInMilli = (long)(newLocation.getElapsedRealtimeNanos() / 1000000);
            locationAge = currentTimeInMilli - locationTimeInMilli;
        }else{
            locationAge = System.currentTimeMillis() - newLocation.getTime();
        }
        return locationAge;
    }

    private boolean filterAndAddLocation(Location location){

        if (locationList.isEmpty()) {
            locationList.add(location);
            return true;
        }

        long age = getLocationAge(location);

        if(age > MAX_OLD_LOCATION){
            Log.d(LOG_TAG, "Location is old");
            oldLocationList.add(location);
            return false;
        }

        if(location.getAccuracy() <= 0){
            Log.d(LOG_TAG, "Latitidue and longitude values are invalid.");
            noAccuracyLocationList.add(location);
            return false;
        }

        //setAccuracy(newLocation.getAccuracy());
        float horizontalAccuracy = location.getAccuracy();
        if(horizontalAccuracy > MAX_ACCURANCY){ //100 meter filter
            Log.d(LOG_TAG, "Accuracy is too low. " + horizontalAccuracy);
            inaccurateLocationList.add(location);
            return false;
        }


        /* Kalman Filter */
        float Qvalue;

        long locationTimeInMillis = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            locationTimeInMillis = (long)(location.getElapsedRealtimeNanos() / 1000000);
        } else {
            locationTimeInMillis = System.currentTimeMillis();
        }

        long elapsedTimeInMillis = locationTimeInMillis - runStartTimeInMillis;

        if(currentSpeed == 0.0f){
            Qvalue = 3.0f; //3 meters per second
        }else{
            Qvalue = currentSpeed; // meters per second
        }

        kalmanFilter.Process(location.getLatitude(), location.getLongitude(), location.getAccuracy(), elapsedTimeInMillis, Qvalue);
        double predictedLat = kalmanFilter.get_lat();
        double predictedLng = kalmanFilter.get_lng();

        Location predictedLocation = new Location("");//provider name is unecessary
        predictedLocation.setLatitude(predictedLat);//your coords of course
        predictedLocation.setLongitude(predictedLng);
        float predictedDeltaInMeters =  predictedLocation.distanceTo(location);

        if(predictedDeltaInMeters > 60){
            Log.d(LOG_TAG, "Kalman Filter detects mal GPS, we should probably remove this from track");
            kalmanFilter.consecutiveRejectCount += 1;

            if(kalmanFilter.consecutiveRejectCount > 3){
                kalmanFilter = new KalmanLatLong(3); //reset Kalman Filter if it rejects more than 3 times in raw.
            }

            kalmanNGLocationList.add(location);
            return false;
        }else{
            kalmanFilter.consecutiveRejectCount = 0;
        }

        /* Notifiy predicted location to UI */
        Intent intent = new Intent("PredictLocation");
        intent.putExtra("location", predictedLocation);
        LocalBroadcastManager.getInstance(this.getApplication()).sendBroadcast(intent);

        Log.d(LOG_TAG, "Location quality is good enough.");
        currentSpeed = location.getSpeed();
        locationList.add(location);

        return true;
    }

    /***
     * Test battery
     */
    /* Battery Consumption */
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryLevelScaled = batteryLevel / (float)scale;

            batteryLevelArray.add(Integer.valueOf(batteryLevel));
            batteryLevelScaledArray.add(Float.valueOf(batteryLevelScaled));
            batteryScale = scale;
        }
    };

    /* Data Logging */
    public synchronized void saveLog(long timeInSeconds, double distanceInMeters, int gpsCount, int batteryLevelStart, int batteryLevelEnd, float batteryLevelScaledStart, float batteryLevelScaledEnd) {
        Log.d(TAG, "aaaa ");
        SimpleDateFormat fileNameDateTimeFormat = new SimpleDateFormat("yyyy_MMdd_HHmm");
        String filePath = this.getExternalFilesDir(null).getAbsolutePath() + "/"
                + fileNameDateTimeFormat.format(new Date()) + "_battery" + ".csv";

        Log.d(TAG, "saving to " + filePath);

        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, false);
            fileWriter.append("Time,DisplayInfor,GPSCount,BatteryLevelStart,BatteryLevelEnd,BatteryLevelStart(/" + batteryScale + ")," + "BatteryLevelEnd(/" + batteryScale + ")" + "\n");
            String record = "" + timeInSeconds + ',' + distanceInMeters + ',' + gpsCount + ',' + batteryLevelStart + ',' + batteryLevelEnd + ',' + batteryLevelScaledStart + ',' + batteryLevelScaledEnd + '\n';
            fileWriter.append(record);
            Toast.makeText(this, "write done", Toast.LENGTH_SHORT).show();
            isLogging = false;
        } catch (Exception e) {
            Toast.makeText(this, "write err", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "err " + e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                Log.d(TAG, "close");
                try {
                    fileWriter.close();
                } catch (IOException ioe) {
                    Log.d(TAG, "err " + ioe.getLocalizedMessage());
                    ioe.printStackTrace();
                    Toast.makeText(this, "write err 2", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}


