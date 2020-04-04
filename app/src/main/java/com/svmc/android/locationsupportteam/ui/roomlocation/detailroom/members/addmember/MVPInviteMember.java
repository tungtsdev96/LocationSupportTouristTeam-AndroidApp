package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.addmember;

import com.svmc.android.locationsupportteam.data.local.entity.RecentedSearchUserCache;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.ArrayList;
import java.util.List;

public class MVPInviteMember {

    public interface IViewInviteMember extends BaseView<IPresenterInviteMember> {

        void removeMemberToListWaiting(int pos, MemberOfRoomLocation member);

        void addMemberToListWaiting(int pos, MemberOfRoomLocation member);

        void onErr(int errCode);

        void bindListResult(int page, ArrayList<MemberOfRoomLocation> result);

    }

    public interface IPresenterInviteMember extends BasePresenter {

        void addUserToCache(User user);

        void showListRecentSearch();

        void showListResultSearch(int page, String query, String roomId);

        /**
         * inview member to room
         * @param member
         * @param roomId
         * @param pos
         */
        void inviteMemberToRoom(MemberOfRoomLocation member, String roomId, int pos);

        /**
         * remove member to room
         * @param member
         * @param roomId
         * @param pos
         */
        void removeInvitation(MemberOfRoomLocation member, String roomId, int pos);
    }

    public interface IModelInviteMember {

        void addUserToCache(User user);

        List<RecentedSearchUserCache> getListSearchRecent();

        void searchByUsernameOrEmail(int page, String query, String roomId, FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener);

        void inviteMemberToRoom(MemberOfRoomLocation member, String roomId, FinishedListener<BaseResultResponse<String>> finishedListener);

        void removeInvitation(String userId, String roomId, FinishedListener<BaseResultResponse<String>> finishedListener);

    }

}
