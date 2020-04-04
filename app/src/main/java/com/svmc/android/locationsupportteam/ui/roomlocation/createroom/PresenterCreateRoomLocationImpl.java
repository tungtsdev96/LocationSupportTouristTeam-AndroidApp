package com.svmc.android.locationsupportteam.ui.roomlocation.createroom;

import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class PresenterCreateRoomLocationImpl implements MVPCreateRoomLocation.IPresenterCreateRoomLocation {

    private RoomLocation roomLocation;

    private MVPCreateRoomLocation.IViewCreateRoomLocation viewCreateRoomLocation;
    private MVPCreateRoomLocation.IModelCreateRoomLocation modelCreateRoomLocation;

    public PresenterCreateRoomLocationImpl(MVPCreateRoomLocation.IViewCreateRoomLocation viewCreateRoomLocation) {
        this.viewCreateRoomLocation = viewCreateRoomLocation;
        this.viewCreateRoomLocation.setPresenter(this);
        this.modelCreateRoomLocation = new ModelCreateRoomLocationImpl();
        roomLocation = new RoomLocation();
    }

    @Override
    public void start() {

        // innit Room
        FirebaseUser user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        if (roomLocation.getMemberOfRoomLocations() == null && user != null) {
            List<MemberOfRoomLocation> memberOfRoomLocations = new ArrayList<>();
            MemberOfRoomLocation memberOfRoomLocation = new MemberOfRoomLocation();
            memberOfRoomLocation.setUserId(user.getUid());
            memberOfRoomLocation.setDisplayName(user.getDisplayName());
            memberOfRoomLocation.setStatus(MemberOfRoomLocation.MEMBER_JOINED);
            memberOfRoomLocations.add(memberOfRoomLocation);
            roomLocation.setMemberOfRoomLocations(memberOfRoomLocations);

//            showListRecentSearch();
        }
    }

    @Override
    public void addUserToCache(MemberOfRoomLocation user) {
        User u = new User();
        u.setUserId(user.getUserId());
        u.setDisplayName(user.getDisplayName());
        u.setEmail(user.getEmail());
        modelCreateRoomLocation.addUserToCache(u);
    }

    @Override
    public void showListRecentSearch() {
        ArrayList<MemberOfRoomLocation> users = new ArrayList<>();
        for (RecentedSearchUserCache recentedSearchUserCache : modelCreateRoomLocation.getListSearchRecent()) {
            if (!checkExits(recentedSearchUserCache.getUserId())) {
                MemberOfRoomLocation u = new MemberOfRoomLocation();
                u.setUserId(recentedSearchUserCache.getUserId());
                u.setEmail(recentedSearchUserCache.getEmail());
                u.setDisplayName(recentedSearchUserCache.getDisplayName());
                u.setStatus(MemberOfRoomLocation.MEMBER_LEFT);
                users.add(u);
            }
        }
        viewCreateRoomLocation.bindListRecentSearch(users);
    }

    @Override
    public void showListResultSearch(int page, String query) {
        modelCreateRoomLocation.searchByUsernameOrEmail(page, query, new FinishedListener<BaseResultResponse<ArrayList<User>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<User>> result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    if (result.getResult().size() > 0) {
                        ArrayList<MemberOfRoomLocation> members = new ArrayList<>();
                        for (User u : result.getResult()) {
                            if (!checkExits(u.getUserId())) {
                                MemberOfRoomLocation member = new MemberOfRoomLocation();
                                member.setUserId(u.getUserId());
                                member.setEmail(u.getEmail());
                                member.setDisplayName(u.getDisplayName());
                                member.setStatus(MemberOfRoomLocation.MEMBER_LEFT);
                                members.add(member);
                            }
                        }
                        viewCreateRoomLocation.bindListResult(0, members);
						return;
				   }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewCreateRoomLocation.onCreateErr(ErrCode.CommonCode.ERR_SERVER);
            }
        });
    }

    @Override
    public boolean checkExits(String userId){
        for (MemberOfRoomLocation m : roomLocation.getMemberOfRoomLocations()) {
            if (userId.equals(m.getUserId())) return true;
        }
        return false;
    }

    @Override
    public void setNameRoom(String name) {
        roomLocation.setNameRoom(name);
    }

    @Override
    public void addMember(MemberOfRoomLocation user, int pos) {

        // add user to cache
        modelCreateRoomLocation.addUserToCache(user);
        roomLocation.getMemberOfRoomLocations().add(user);

        user.setStatus(MemberOfRoomLocation.MEMBER_INVITED);
        viewCreateRoomLocation.updateUIAddMember(pos, user);
    }

    @Override
    public void removeMember(int pos) {
        roomLocation.getMemberOfRoomLocations().remove(pos);
        viewCreateRoomLocation.updateUIRemoveMember(pos);
    }

    @Override
    public void createRoomLocaion() {
        if (roomLocation.getNameRoom() == null) {
            viewCreateRoomLocation.onCreateErr(ErrCode.RoomLocationCode.ERR_REQUIRE_NAME);
            return;
        }

        if (roomLocation.getMemberOfRoomLocations().size() <= 1) {
            viewCreateRoomLocation.onCreateErr(ErrCode.RoomLocationCode.ERR_REQUIRE_MEMBER);
            return;
        }

        modelCreateRoomLocation.createRoomLocation(new FinishedListener<BaseResultResponse<RoomLocation>>() {
            @Override
            public void onFinished(BaseResultResponse<RoomLocation> result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    AppPreferencens.getInstance().setRoomId(result.getResult().getRoomId());
                    viewCreateRoomLocation.onCreateSuccess(result.getResult());
                } else {
                    viewCreateRoomLocation.onCreateErr(ErrCode.CommonCode.ERR_SERVER);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewCreateRoomLocation.onCreateErr(ErrCode.CommonCode.BAD_REQUEST);
            }
        }, roomLocation);
    }

}
