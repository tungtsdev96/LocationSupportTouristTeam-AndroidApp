package com.svmc.android.locationsupportteam.ui.roomlocation.startdialog;

import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseViewAuth;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class MVPDialogRoom {

    public interface IViewDialogRoom extends BaseViewAuth<IPresenterDialogRoom> {

        void showProgress();

        void hideProgress();

        void showContentView();

        void hideContentView();

        void innitView(RoomLocation roomLocation);

        void showUICreateRoom();

        void showUILocationOnMap();

        /***
         * Done leave room
         */
        void onLeaveRoom();

    }

    public interface IPresenterDialogRoom extends BasePresenter {

        void getInforRoom(String roomId);

        /**
         * @param roomId
         */
        void leaveRoom(String roomId);

        /**
         * open UI
         */
        void openUICreateRoom();

        void openUILocationOnMap();

    }

    public interface IModelDialogRoom {

        void leaveRoom(String roomId, FinishedListener<BaseResultResponse<String>> finishedListener);

        void getInforRoom(String roomId, FinishedListener<BaseResultResponse<RoomLocation>> finishedListener);

    }

}
