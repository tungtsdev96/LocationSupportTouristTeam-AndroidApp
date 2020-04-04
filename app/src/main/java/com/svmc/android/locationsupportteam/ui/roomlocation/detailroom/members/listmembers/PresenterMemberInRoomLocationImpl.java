package com.svmc.android.locationsupportteam.ui.roomlocation.detailroom.members.listmembers;

import com.svmc.android.locationsupportteam.entity.roomloation.MemberOfRoomLocation;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by TUNGTS on 4/8/2019
 */

public class PresenterMemberInRoomLocationImpl implements MVPMemberInRoomLocation.IPresenterMemeberInRoomLocation {

    private MVPMemberInRoomLocation.IViewMemberInRoomLocation viewMemberInGroup;
    private MVPMemberInRoomLocation.IModelMemberInRoomLocation modelMemberInGroup;

    public PresenterMemberInRoomLocationImpl(MVPMemberInRoomLocation.IViewMemberInRoomLocation viewMemberInGroup) {
        this.viewMemberInGroup = viewMemberInGroup;
        this.viewMemberInGroup.setPresenter(this);
        modelMemberInGroup = new ModelMemberInGroupImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void showAllMemberInGroup(String roomId) {

        modelMemberInGroup.getAllMemberInRoom(roomId, new FinishedListener<BaseResultResponse<ArrayList<MemberOfRoomLocation>>>() {
            @Override
            public void onFinished(BaseResultResponse<ArrayList<MemberOfRoomLocation>> result) {
                ArrayList<MemberOfRoomLocation> members = result.getResult();
                Collections.sort(members, new Comparator<MemberOfRoomLocation>() {
                    @Override
                    public int compare(MemberOfRoomLocation m1, MemberOfRoomLocation m2) {
                        if (m1.getStatus() > m2.getStatus()) return 1;
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
                viewMemberInGroup.showAllMemberInRoom(members);
            }

            @Override
            public void onFailure(Throwable t) {
                viewMemberInGroup.onLoadDataFail(ErrCode.CommonCode.ERR_SERVER);
            }
        });

    }

    @Override
    public void removeMember(final int pos, final MemberOfRoomLocation member) {
        modelMemberInGroup.removeMember(member.getRoomId(), member.getUserId(), new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                viewMemberInGroup.onRemoveMemberSuccess(pos, member);
            }

            @Override
            public void onFailure(Throwable t) {
                viewMemberInGroup.onRemoveMemberFail(ErrCode.CommonCode.ERR_SERVER);
            }
        });
    }

    @Override
    public void removeInviation(final int pos, final MemberOfRoomLocation member, String roomId) {
        modelMemberInGroup.removeInvitation(member.getUserId(), roomId, new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                viewMemberInGroup.onRemoveMemberSuccess(pos, member);
            }

            @Override
            public void onFailure(Throwable t) {
                viewMemberInGroup.onRemoveMemberFail(ErrCode.CommonCode.ERR_SERVER);
            }
        });
    }
}
