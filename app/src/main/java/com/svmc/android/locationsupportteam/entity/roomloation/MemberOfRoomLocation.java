package com.svmc.android.locationsupportteam.entity.roomloation;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.UserLocation;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/20/2019
 */

public class MemberOfRoomLocation {

    public final static int MEMBER_LEFT = 0;
    public final static int MEMBER_INVITED = 1;
    public final static int MEMBER_JOINED = 2;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("email")
    private String email;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("status")
    private int status;

    @SerializedName("room_id")
    private String roomId;

    @SerializedName("action")
    private String action;

    @SerializedName("url_image")
    private String urlProfile;

    @SerializedName("phone")
    private String phone;

    @SerializedName("user_location")
    private UserLocation userLocation;

    public MemberOfRoomLocation(){}

    public MemberOfRoomLocation(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }

    public UserLocation getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(UserLocation userLocation) {
        this.userLocation = userLocation;
    }

    public static ArrayList<MemberOfRoomLocation> fakeList() {
        ArrayList<MemberOfRoomLocation> memberOfRoomLocations = new ArrayList<>();
        MemberOfRoomLocation memberOfRoomLocation = new MemberOfRoomLocation("aaaa");

        memberOfRoomLocations.add(memberOfRoomLocation);
        memberOfRoomLocations.add(memberOfRoomLocation);
        memberOfRoomLocations.add(memberOfRoomLocation);
        memberOfRoomLocations.add(memberOfRoomLocation);
        memberOfRoomLocations.add(memberOfRoomLocation);
        memberOfRoomLocations.add(memberOfRoomLocation);

        return memberOfRoomLocations;
    }

}
