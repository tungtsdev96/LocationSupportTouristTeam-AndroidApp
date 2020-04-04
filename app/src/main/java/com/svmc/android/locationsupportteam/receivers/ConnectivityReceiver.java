package com.svmc.android.locationsupportteam.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.utils.CommonUtils;

/**
 * Created by TungTS on 4/23/2019
 */

public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            boolean isInternet = CommonUtils.checkInternetConnection(context);
//            Toast.makeText(context, " " + isInternet, Toast.LENGTH_SHORT).show();
            if (onConnectivityChangeListener != null){
                onConnectivityChangeListener.onConnectivityChange(isInternet);
            }
        }
    }

    private ConnectivityChangeListener onConnectivityChangeListener;
    public void setOnConnectivityReceiver(ConnectivityChangeListener onConnectivityChangeListener){
        this.onConnectivityChangeListener = onConnectivityChangeListener;
    }

    public interface ConnectivityChangeListener {
        void onConnectivityChange(boolean isInternet);
    }
}
