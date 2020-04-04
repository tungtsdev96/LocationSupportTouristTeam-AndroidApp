package com.svmc.android.locationsupportteam.ui.notification.room;

import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseViewAuth;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Create by TungTS on 5/2/2019
 */

public class MVPRoomNotification {

    public interface IViewRoomNotification extends BaseViewAuth<IPresenterRoomNotification> {

        void onLoadNotifyDone(ArrayList<RoomLocationNotification> notifications);

        void onRemoveDone(int pos);

        void onErr(int errCode);

    }

    public interface IPresenterRoomNotification extends BasePresenter {

        /**
         * get list noty when start app
         */
        void getListNotify();

        void removeNotify(int pos, String notifyId);

        void resolveAlert(String senderId);
    }

    public interface IModelShareNotification {

        void getListNotify(FinishedListener<BaseResultResponse<ArrayList<RoomLocationNotification>>> finishedListener);

        void removeNotify(String notifyId, FinishedListener<BaseResultResponse<JSONObject>> finishedListener);

        void resolveAlert(String senderId, FinishedListener<BaseResultResponse<String>> finishedListener);
    }

}
