package com.svmc.android.locationsupportteam.ui.roomlocation.startdialog;

import com.google.firebase.auth.FirebaseAuthException;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class PresenterDialogRoomImpl implements MVPDialogRoom.IPresenterDialogRoom {

    private MVPDialogRoom.IViewDialogRoom viewDialogRoom;
    private MVPDialogRoom.IModelDialogRoom modelDialogRoom;

    public PresenterDialogRoomImpl(MVPDialogRoom.IViewDialogRoom viewDialogRoom) {
        this.viewDialogRoom = viewDialogRoom;
        this.viewDialogRoom.setPresenter(this);
        this.modelDialogRoom = new ModelDialogRoomImpl();
    }


    @Override
    public void start() {

    }


    @Override
    public void getInforRoom(String roomId) {
        modelDialogRoom.getInforRoom(roomId, new FinishedListener<BaseResultResponse<RoomLocation>>() {
            @Override
            public void onFinished(BaseResultResponse<RoomLocation> result) {
                viewDialogRoom.innitView(result.getResult());
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof FirebaseAuthException) {
                    viewDialogRoom.onInvalidToken();
                }
            }
        });
    }

    @Override
    public void leaveRoom(String roomId) {
        modelDialogRoom.leaveRoom(roomId, new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                AppPreferencens.getInstance().setRoomId(null);
                viewDialogRoom.onLeaveRoom();
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof FirebaseAuthException) {
                    viewDialogRoom.onInvalidToken();
                    return;
                }
            }
        });
    }

    @Override
    public void openUICreateRoom() {
        viewDialogRoom.showUICreateRoom();
    }

    @Override
    public void openUILocationOnMap() {
        viewDialogRoom.showUILocationOnMap();
    }
}
