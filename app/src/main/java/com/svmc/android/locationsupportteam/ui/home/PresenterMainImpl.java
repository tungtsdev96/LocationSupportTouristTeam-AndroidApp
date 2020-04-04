package com.svmc.android.locationsupportteam.ui.home;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.data.local.LocationDataLocal;
import com.svmc.android.locationsupportteam.entity.event.EventPostLoadingData;
import com.svmc.android.locationsupportteam.entity.event.EventPostRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by TungTS on 4/23/2019
 */

public class PresenterMainImpl implements MVPMainFragment.IPresenterMain {

    private MVPMainFragment.IViewMain viewMain;
    private MVPMainFragment.IModelMain modelMain;
    private LocationDataLocal locationDataLocal;

    public PresenterMainImpl(MVPMainFragment.IViewMain viewMain) {
        this.viewMain = viewMain;
        this.viewMain.setPresenter(this);
        modelMain = new ModelMainImpl();
        locationDataLocal = new LocationDataLocal();
    }

    @Override
    public void start() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            getRoomLocationByUser();
        }

        if (locationDataLocal.getLastLocationCache() == null) viewMain.onFirstStartApp();
    }

    @Override
    public void handleRoomLocation(String roomIdJoined) {

        if (roomIdJoined != null) {
            EventBus.getDefault().post(new EventPostRoomLocation(roomIdJoined, MemberOfRoomLocation.MEMBER_JOINED));
            AppPreferencens.getInstance().setRoomId(roomIdJoined);
            viewMain.ennaleLocationProvider();
            viewMain.showNotificationSOS();
        }

        EventBus.getDefault().post(new EventPostLoadingData(true));

    }

    @Override
    public void getRoomLocationByUser() {
        FinishedListener<BaseResultResponse<String>> finishedListener =  new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                handleRoomLocation(result.getResult());
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof FirebaseAuthException) {
                    viewMain.onInvalidToken();
                } else {

                }
            }
        };

        modelMain.getRoomLocation(finishedListener);
    }


}
