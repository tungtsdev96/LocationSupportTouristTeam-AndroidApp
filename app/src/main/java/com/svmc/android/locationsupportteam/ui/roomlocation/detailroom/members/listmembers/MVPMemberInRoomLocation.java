package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BasePresenter;
import com.svmc.android.locationsupportteam.ui.base.BaseView;

import java.util.ArrayList;

public class MVPMemberInRoomLocation {

    public interface IViewMemberInRoomLocation extends BaseView<IPresenterMemeberInRoomLocation> {

        void showAllMemberInRoom(ArrayList<MemberOfRoomLocation> members);

        void onRemoveMemberSuccess(int pos, MemberOfRoomLocation member);

        void onRemoveMemberFail(int errCode);

        void onLoadDataFail(int errCode);
    }

    public interface IPresenterMemeberInRoomLocation extends BasePresenter {

        void showAllMemberInGroup(String roomId);

        void removeMember(int pos, MemberOfRoomLocation member);

        void removeInviation(int pos, MemberOfRoomLocation member, String roomId);
    }

    public interface IModelMemberInRoomLocation {

        void getAllMemberInRoom(String roomId, FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>> finishedListener);

        void removeMember(String roomId, String memberId, FinishedListener<BaseResultResponse<String>> finishedListener);

        void removeInvitation(String userId, String roomId, FinishedListener<BaseResultResponse<String>> finishedListener);
    }
}
