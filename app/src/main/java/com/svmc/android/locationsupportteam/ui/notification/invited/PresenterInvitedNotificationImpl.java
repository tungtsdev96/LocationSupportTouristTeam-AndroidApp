package com.svmc.android.locationsupportteam.ui.notification.invited;

import com.google.firebase.auth.FirebaseAuthException;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Create by TungTS on 5/2/2019
 */

public class PresenterInvitedNotificationImpl implements MVPInvitedNotification.IPresenterInvitedNotification {

    private MVPInvitedNotification.IViewInvitedNotification viewNotification;
    private MVPInvitedNotification.IModelInvitedNotification modelNotification;

    public PresenterInvitedNotificationImpl(MVPInvitedNotification.IViewInvitedNotification viewNotification) {
        this.viewNotification = viewNotification;
        this.viewNotification.setPresenter(this);
        this.modelNotification = new ModelInvitedNotificationImpl();
    }

    @Override
    public void start() {
        getListNotify();
    }


    @Override
    public void handleClickNotify(int position) {
        viewNotification.showBottomSheet(position);
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
                        if (roomId == null || (roomId != notification.getRoom().getRoomId() && notification.getType().equals(RoomLocationNotification.STATUS_INVITED))) {
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
    public void joinRoom(final int pos, final RoomLocationNotification notification) {
        modelNotification.joinRoom(notification.getRoom().getRoomId(), new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                AppPreferencens.getInstance().setRoomId(notification.getRoom().getRoomId());
                viewNotification.onJoinRoomSuccess(pos);
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
    public void removeInvitation(final int pos, RoomLocationNotification notification) {
        modelNotification.removeInvitation(notification.getRoom().getRoomId(), new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                viewNotification.onRemoveSuccess(pos);
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
}
