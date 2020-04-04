package com.svmc.android.locationsupportteam.ui.notification.room.shareplace;

import com.google.firebase.auth.FirebaseAuthException;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.notification.room.MVPRoomNotification;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Create by TungTS on 5/2/2019
 */

public class PresenterShareNotificationImpl implements MVPRoomNotification.IPresenterRoomNotification {

    private MVPRoomNotification.IViewRoomNotification viewNotification;
    private MVPRoomNotification.IModelShareNotification modelNotification;

    public PresenterShareNotificationImpl(MVPRoomNotification.IViewRoomNotification viewNotification) {
        this.viewNotification = viewNotification;
        this.viewNotification.setPresenter(this);
        this.modelNotification = new ModelShareNotificationImpl();
    }

    @Override
    public void start() {
        getListNotify();
    }


    @Override
    public void getListNotify() {
        modelNotification.getListNotify(new FinishedListener<BaseResultResponse<ArrayList<RoomLocationNotification>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<RoomLocationNotification>> result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    ArrayList<RoomLocationNotification> notifications = new ArrayList<>();

//                    notifications.addAll(result.getResult());

                    String roomId = AppPreferencens.getInstance().getRoomId();
                    for (RoomLocationNotification notification: result.getResult()) {
                        if (roomId != null && roomId.equals(notification.getRoom().getRoomId()) && notification.getType().equals(RoomLocationNotification.STATUS_SHARE_PLACE)) {
                            notifications.add(notification);
                        }
                    }

                    Collections.sort(notifications, new Comparator<RoomLocationNotification>() {
                        @Override
                        public int compare(RoomLocationNotification o1, RoomLocationNotification o2) {
                            if (o1.getCreatedTime() < o2.getCreatedTime()) return 1;
                            return -1;
                        }
                    });
                    viewNotification.onLoadNotifyDone(notifications);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof FirebaseAuthException) {
                    viewNotification.onInvalidToken();
                    return;
                }
                viewNotification.onErr(ErrCode.CommonCode.ERR_SERVER);
            }
        });
    }

    @Override
    public void removeNotify(final int pos, String notifyId) {
        modelNotification.removeNotify(notifyId, new FinishedListener<BaseResultResponse<JSONObject>>() {
            @Override
            public void onFinished(BaseResultResponse<JSONObject> result) {
                viewNotification.onRemoveDone(pos);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void resolveAlert(String senderId) {

    }

}
