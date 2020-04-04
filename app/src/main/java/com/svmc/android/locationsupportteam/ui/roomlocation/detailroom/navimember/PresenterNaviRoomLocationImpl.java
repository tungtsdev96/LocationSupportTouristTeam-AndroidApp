package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember;

import com.google.firebase.auth.FirebaseAuthException;
import com.svmc.android.locationsupportteam.entity.event.EventPostFocusCameraMapTo;
import com.svmc.android.locationsupportteam.entity.event.EventPostListUserLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class PresenterNaviRoomLocationImpl implements MVPNaviRoomLocation.IPresenterNaviRoomLocation {

    private MVPNaviRoomLocation.IViewNaviRoomLocation viewNaviRoomLocation;
    private MVPNaviRoomLocation.IModelNaviRoomLocation modelNaviRoomLocation;

    private RoomLocation roomLocation;

    private boolean isForceStart = false;



    public PresenterNaviRoomLocationImpl(MVPNaviRoomLocation.IViewNaviRoomLocation viewNaviRoomLocation) {
        this.viewNaviRoomLocation = viewNaviRoomLocation;
        this.viewNaviRoomLocation.setPresenter(this);
        this.modelNaviRoomLocation = new ModelNaviRoomImpl();
    }

    @Override
    public void start() {
        showListMember();
    }

    @Override
    public void setRoomLocation(RoomLocation roomLocation) {
        this.roomLocation = roomLocation;
    }

    @Override
    public void showListMember() {
        modelNaviRoomLocation.getListMemberOfRoom(roomLocation.getRoomId(), new FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<MemberOfRoomLocation>> result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    ArrayList<MemberOfRoomLocation> members = result.getResult();
                    Collections.sort(members, new Comparator<MemberOfRoomLocation>() {
                        @Override
                        public int compare(MemberOfRoomLocation m1, MemberOfRoomLocation m2) {
                            if (m1.getStatus() < m2.getStatus()) return 1;
                            else if (m1.getStatus() == m2.getStatus()) {
                                if (m1.getUserLocation() != null && m2.getUserLocation() != null) {
                                    if (m2.getUserLocation().isOnline() == m1.getUserLocation().isOnline()) {
                                        return m1.getUserLocation().getLastTimeOnline() < m2.getUserLocation().getLastTimeOnline() ? 1 : -1;
                                    } else if (m2.getUserLocation().isOnline() && !m1.getUserLocation().isOnline()) {
                                        return 1;
                                    } else return -1;
                                }
                            }
                            return -1;
                        }
                    });

                    if (members.size() > 0) {
                        EventBus.getDefault().post(new EventPostListUserLocation(members));
                    }

                    viewNaviRoomLocation.showListMember(members);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof FirebaseAuthException) {
                    viewNaviRoomLocation.onInvalidToken();
                }
            }
        });
    }

    @Override
    public void changeCameraTo(String userId) {
        EventBus.getDefault().post(new EventPostFocusCameraMapTo(userId));
    }

}
