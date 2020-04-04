package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember;

import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/8/2019
 */

public class PresenterInviteMemberImpl implements MVPInviteMember.IPresenterInviteMember {

    private MVPInviteMember.IViewInviteMember viewMemberInGroup;
    private MVPInviteMember.IModelInviteMember modelInviteMember;

    public PresenterInviteMemberImpl(MVPInviteMember.IViewInviteMember viewMemberInGroup) {
        this.viewMemberInGroup = viewMemberInGroup;
        this.viewMemberInGroup.setPresenter(this);
        modelInviteMember = new ModelInviteMemberInvitedImpl();
    }

    @Override
    public void start() {
//        showListRecentSearch();
    }

    @Override
    public void addUserToCache(User user) {
        modelInviteMember.addUserToCache(user);
    }

    @Override
    public void showListRecentSearch() {
        ArrayList<User> users = new ArrayList<>();
        for (RecentedSearchUserCache recentedSearchUserCache : modelInviteMember.getListSearchRecent()) {
            User u = new User();
            u.setUserId(recentedSearchUserCache.getUserId());
            u.setEmail(recentedSearchUserCache.getEmail());
            u.setUsername(recentedSearchUserCache.getUsername());
            u.setDisplayName(recentedSearchUserCache.getDisplayName());
            users.add(u);
        }
    }

    @Override
    public void showListResultSearch(int page, String query, String roomId) {
        modelInviteMember.searchByUsernameOrEmail(page, query, roomId, new FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<MemberOfRoomLocation>> result) {
                if (result.getStatus() == ErrCode.CommonCode.RESULT_OK) {
                    if (result.getResult().size() > 0) {
                        viewMemberInGroup.bindListResult(0, result.getResult());
                        return;
                    }
                    viewMemberInGroup.bindListResult(0, new ArrayList<MemberOfRoomLocation>());
                }

            }

            @Override
            public void onFailure(Throwable t) {
                viewMemberInGroup.bindListResult(0, new ArrayList<MemberOfRoomLocation>());
            }
        });
    }

    @Override
    public void inviteMemberToRoom(final MemberOfRoomLocation member, String roomId, final int pos) {
        member.setStatus(MemberOfRoomLocation.MEMBER_INVITED);

        // GOTO api invited
        modelInviteMember.inviteMemberToRoom(member, roomId, new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                viewMemberInGroup.addMemberToListWaiting(pos, member);
            }

            @Override
            public void onFailure(Throwable t) {
                viewMemberInGroup.onErr(ErrCode.CommonCode.ERR_SERVER);
            }
        });
    }

    @Override
    public void removeInvitation(final MemberOfRoomLocation member, String roomId, final int pos) {
        member.setStatus(MemberOfRoomLocation.MEMBER_LEFT);
        modelInviteMember.removeInvitation(member.getUserId(), roomId, new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                viewMemberInGroup.removeMemberToListWaiting(pos, member);
            }

            @Override
            public void onFailure(Throwable t) {
                viewMemberInGroup.onErr(ErrCode.CommonCode.ERR_SERVER);
            }
        });
    }

}
