package com.svmc.android.locationsupportteam.ui.notification.invited;

import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseViewAuth;

import java.util.ArrayList;

/**
 * Create by TungTS on 5/2/2019
 */

public class MVPInvitedNotification {

    public interface IViewInvitedNotification extends BaseViewAuth<IPresenterInvitedNotification> {

        void onLoadNotifyDone(ArrayList<RoomLocationNotification> notifications);

        void onErr(int errCode);

        void showBottomSheet(int pos);

        /**
         * Call when remove notify success
         * @param pos
         */
        void onRemoveSuccess(int pos);

        /**
         * Call when join room
         * @param pos
         */
        void onJoinRoomSuccess(int pos);
    }

    public interface IPresenterInvitedNotification extends BasePresenter {

        void handleClickNotify(int position);

        /**
         * get list noty when start app
         */
        void getListNotify();

        /**
         * join room
         * @param roomId
         */
        void joinRoom(int pos, RoomLocationNotification roomId);

        /***
         * remove invitation
         * @param notification
         */
        void removeInvitation(int pos, RoomLocationNotification notification);
    }

    public interface IModelInvitedNotification {

        void getListNotify(FinishedListener<BaseResultResponse<ArrayList<RoomLocationNotification>>> finishedListener);

        /**
         * join room
         * @param roomId
         */
        void joinRoom(String roomId, FinishedListener<BaseResultResponse<String>> finishedListener);

        /***
         * remove Invitation
         * @param roomId
         */
        void removeInvitation(final String roomId, final FinishedListener<BaseResultResponse<String>> finishedListener);
    }

}
