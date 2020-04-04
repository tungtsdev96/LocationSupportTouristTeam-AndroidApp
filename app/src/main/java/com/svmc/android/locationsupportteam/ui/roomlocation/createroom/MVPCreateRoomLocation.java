package com.svmc.android.locationsupportteam.ui.roomlocation.createroom;

import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class MVPCreateRoomLocation {

    public interface IViewCreateRoomLocation extends BaseView<IPresenterCreateRoomLocation> {

        /***
         * bind list recented search to UI
         * @param users
         */
        void bindListRecentSearch(ArrayList<MemberOfRoomLocation> users);

        /***
         * bind list result search
         * @param page
         * @param users
         */
        void bindListResult(int page, ArrayList<MemberOfRoomLocation> users);

        /**
         * remove list
         */
        void removeListSearch();

        void updateUIAddMember(int pos, MemberOfRoomLocation member);

        void updateUIRemoveMember(int pos);

        void onCreateErr(int errCode);

        void onCreateSuccess(RoomLocation roomLocation);
    }

    public interface IPresenterCreateRoomLocation extends BasePresenter {

        void addUserToCache(MemberOfRoomLocation user);

        /**
         * show list recented search
         */
        void showListRecentSearch();

        /**
         * show list result search
         * @param page
         * @param query
         */
        void showListResultSearch(int page, String query);

        /**
         * check user exits in room
         * @param userId
         * @return
         */
        boolean checkExits(String userId);

        void setNameRoom(String name);

        void addMember(MemberOfRoomLocation user, int pos);

        void removeMember(int pos);

        /**
         * created room
         */
        void createRoomLocaion();

    }

    public interface IModelCreateRoomLocation {

        void addUserToCache(User user);

        void addUserToCache(MemberOfRoomLocation user);

        List<RecentedSearchUserCache> getListSearchRecent();

        void searchByUsernameOrEmail(int page, String query, FinishedListener<BaseResultResponse<ArrayList<User>>> finishedListener);

//        void saveRecentedSearchToLocal(ArrayList<User> users);

        void createRoomLocation(FinishedListener<BaseResultResponse<RoomLocation>> finishedListener, RoomLocation roomLocation);
    }

}
