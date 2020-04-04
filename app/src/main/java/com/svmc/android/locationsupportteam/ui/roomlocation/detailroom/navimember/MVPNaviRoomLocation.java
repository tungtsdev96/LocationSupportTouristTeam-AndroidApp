package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.navimember;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseViewAuth;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/21/2019
 */

public class MVPNaviRoomLocation {

    public interface IViewNaviRoomLocation extends BaseViewAuth<IPresenterNaviRoomLocation> {

        void showListMember(ArrayList<MemberOfRoomLocation> memberOfRoomLocations);

    }

    public interface IPresenterNaviRoomLocation extends BasePresenter {

        void setRoomLocation(RoomLocation roomLocation);

        void showListMember();

        void changeCameraTo(String userId);

    }

    public interface IModelNaviRoomLocation {

        void getListMemberOfRoom(String roomId, FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener);

    }

}
