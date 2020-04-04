package com.svmc.android.locationsupportteam.entity.group;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 4/3/2019
 */

public class Group {

    @SerializedName("group_id")
    private String groupId;

    @SerializedName("group_name")
    private String nameGroup;

    @SerializedName("total_member")
    private int numberMember;

    @SerializedName("members")
    private ArrayList<UserInGroup> members;

    @SerializedName("time_created")
    private long timeCreated;

    @SerializedName("icon")
    private String icon;

    @SerializedName("privacy")
    private int privacy = -1;

    @SerializedName("user_created")
    private UserInGroup userCreated;

    @SerializedName("description")
    private String description;

    public Group(){}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getNumberMember() {
        return numberMember;
    }

    public void setNumberMember(int numberMember) {
        this.numberMember = numberMember;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPrivacy() {
        return privacy;
    }

    public void setPrivacy(int privacy) {
        this.privacy = privacy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<UserInGroup> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<UserInGroup> members) {
        this.members = members;
    }

    public UserInGroup getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(UserInGroup userCreated) {
        this.userCreated = userCreated;
    }

    public static Group getDemogroup() {
        Group group = new Group();
        group.groupId = "aaa";
        group.nameGroup = "This is group name";

        UserInGroup user = new UserInGroup();
        user.setDisplayName("tungts");

        ArrayList<UserInGroup> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);
        users.add(user);

        group.setMembers(users);
        group.setUserCreated(user);
        return group;
    }
}
