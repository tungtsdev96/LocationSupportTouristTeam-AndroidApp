package com.svmc.android.locationsupportteam.entity.roomloation;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class RoomLocation {

    @SerializedName("room_id")
    private String roomId;

    @SerializedName("name")
    private String nameRoom;

    @SerializedName("members")
    private List<MemberOfRoomLocation> memberOfRoomLocations;

    @SerializedName("total_member")
    private int totalMember;

    public RoomLocation(){}

    public RoomLocation(String roomId) {
        this.roomId = roomId;
    }

    public RoomLocation(String roomId, String nameRoom) {
        this.roomId = roomId;
        this.nameRoom = nameRoom;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public List<MemberOfRoomLocation> getMemberOfRoomLocations() {
        return memberOfRoomLocations;
    }

    public void setMemberOfRoomLocations(List<MemberOfRoomLocation> memberOfRoomLocations) {
        this.memberOfRoomLocations = memberOfRoomLocations;
    }

    public int getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(int totalMember) {
        this.totalMember = totalMember;
    }

    public static RoomLocation fake(){
        RoomLocation roomLocation = new RoomLocation("aaa");
        roomLocation.setNameRoom("tungts");
        roomLocation.setMemberOfRoomLocations(MemberOfRoomLocation.fakeList());
        return roomLocation;
    }
}
