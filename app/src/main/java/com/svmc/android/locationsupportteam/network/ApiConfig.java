package com.svmc.android.locationsupportteam.network;

/**
 * Created by TUNGTS on 3/16/2019
 */

public final class ApiConfig {

    public interface ConfigUrl {
        String URL_NODEJS = "http://192.168.1.157:3000";
//        String URL_NODEJS = "http://192.168.15.131:3000";
        String URL_GOOGLEAPI = "https://maps.googleapis.com";
        String URL_ONE_SIGNAL = "https://onesignal.com";
    }

    public interface ConfigKeyGoogleMap {
        String KEY_GOOGLE_MAP = "AIzaSyBIXZ1VbDifxu7w8QjL0tFMuKyc-Jem52o";
    }

    public interface ConfigTime {
        int CONNECT_TIMEOUT = 15;
        int WRITE_TIMEOUT = 15;
        int READ_TIMEOUT = 15;
    }

}
