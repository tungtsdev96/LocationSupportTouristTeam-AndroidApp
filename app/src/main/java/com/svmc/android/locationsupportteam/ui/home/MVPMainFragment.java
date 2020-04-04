package com.svmc.android.locationsupportteam.ui.home;

import android.view.KeyEvent;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseViewAuth;

import java.util.ArrayList;

/**
 * Created by TungTS on 4/23/2019
 */

public class MVPMainFragment {

    public interface IViewMain extends BaseViewAuth<IPresenterMain> {

        /**
         * when click hardware button on start phone
         * @param keyCode
         * @param event
         * @return
         */
        boolean onKeyDown(int keyCode, KeyEvent event);

        void ennaleLocationProvider();

        void startTrackingLocation();

        void showNotificationSOS();

        /**
         * get location first
         */
        void onFirstStartApp();
    }

    public interface IPresenterMain extends BasePresenter {

        void handleRoomLocation(String roomIdJoined);

        void getRoomLocationByUser();
    }

    public interface IModelMain {

        void getRoomLocation(FinishedListener<BaseResultResponse<String>> finishedListener);

    }

}
