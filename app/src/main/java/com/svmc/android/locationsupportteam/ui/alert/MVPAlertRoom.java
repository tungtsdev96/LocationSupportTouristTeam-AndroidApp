package com.svmc.android.locationsupportteam.ui.alert;

/**
 * Created by TungTS on 5/13/2019
 */

public class MVPAlertRoom {

    public interface IViewAlertRoom {

        void startVibrate(boolean isRepeat);
        void stopVibrate();

        void finishActivity();

        void showUIMap();

        void callTo();
    }

    public interface IPresenterAlertRoom {

        void callToSender();

        void showUIMap();

    }

}
