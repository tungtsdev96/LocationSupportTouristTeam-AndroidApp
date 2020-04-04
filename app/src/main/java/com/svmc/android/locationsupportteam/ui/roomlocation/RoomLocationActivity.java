package com.svmc.android.locationsupportteam.ui.roomlocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.roomlocation.createroom.CreateRoomLocationFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.createroom.MVPCreateRoomLocation;
import com.svmc.android.locationsupportteam.ui.roomlocation.createroom.PresenterCreateRoomLocationImpl;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.DetailRoomLocationFragment;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.MVPDetailRoomLocation;
import com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.PresenterDetailRoomLocationImpl;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class RoomLocationActivity extends BaseActivity {

    public static final int CREATE_ROOM = 0;
    public static final int DETAIL_ROOM = 1;

    public static Intent innitIntent(Context activity, int indexCurrentFragment, RoomLocation room, String type, LocationPoint point, String userSender, String urlImage) {
        Gson mGson = new Gson();
        Intent intent = new Intent(activity, RoomLocationActivity.class);
        intent.putExtra(Constans.KeyBundle.CURRENT_FRAG_ON_ROOM_LOCATION, indexCurrentFragment);

        if (indexCurrentFragment == DETAIL_ROOM) {
            intent.putExtra(Constans.KeyBundle.ROOM_LOCATION, mGson.toJson(room));
            Bundle b = new Bundle();
            if (type.equals(RoomLocationNotification.STATUS_SHARE_PLACE)) {
                b.putString(Constans.KeyBundle.ROOM_LOCATION_SHARE_POINT, mGson.toJson(point));
            } else {
                b.putString(Constans.KeyBundle.ROOM_ALERT_DATA, mGson.toJson(point));
            }
            b.putString(Constans.KeyBundle.ROOM_USER_SENDER, userSender);

            if (urlImage != null) b.putString(Constans.KeyBundle.ROOM_NOTIFY_URL_IMAGE, userSender);
            intent.putExtras(b);
        }
        return intent;
    }

    private int indexCurrentFragment = -1;

    private RoomLocation roomLocation;

    @Override
    public int getContentView() {
        return R.layout.activity_room_location;
    }

    @Override
    public void onViewCreated(View view) {
        innitData();
        if (indexCurrentFragment == -1) finish();
        innitFragment(indexCurrentFragment);
    }

    private void innitData() {
        indexCurrentFragment = getIntent().getIntExtra(Constans.KeyBundle.CURRENT_FRAG_ON_ROOM_LOCATION,-1);
        String jsonRoomLocation = getIntent().getStringExtra(Constans.KeyBundle.ROOM_LOCATION);
        this.roomLocation = new Gson().fromJson(jsonRoomLocation, RoomLocation.class);
    }

    private void innitFragment(int indexCurrentFragment) {
        switch (indexCurrentFragment){
            case CREATE_ROOM:
                CreateRoomLocationFragment createRoomLocationFragment = CreateRoomLocationFragment.newInstance();
                MVPCreateRoomLocation.IPresenterCreateRoomLocation presenterCreateRoomLocation = new PresenterCreateRoomLocationImpl(createRoomLocationFragment);
                replaceFragment(R.id.content_room_location, createRoomLocationFragment, false);
                break;
            case DETAIL_ROOM:
                Bundle b = getIntent().getExtras();
                DetailRoomLocationFragment detailRoomLocationFragment = DetailRoomLocationFragment.newInstance(roomLocation);
                if (b != null) detailRoomLocationFragment.setArguments(b);
                MVPDetailRoomLocation.IPresenterDetailRoomLocation presenterDetailRoomLocation = new PresenterDetailRoomLocationImpl(detailRoomLocationFragment);
                replaceFragment(R.id.content_room_location, detailRoomLocationFragment, false);
                break;
        }
    }

    public RoomLocation getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(RoomLocation roomLocation) {
        this.roomLocation = roomLocation;
    }

    /**
     * when create room done
     * @param roomLocation
     */
    public void innitShowOnMapFragment(RoomLocation roomLocation) {
        setRoomLocation(roomLocation);
        innitFragment(DETAIL_ROOM);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment = getCurrentFragment(R.id.content_room_location);
        if (fragment instanceof DetailRoomLocationFragment) {
            boolean tmp = ((DetailRoomLocationFragment) fragment).onKeyDown(keyCode, event);
            if (tmp) return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
