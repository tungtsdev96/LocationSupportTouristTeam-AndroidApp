package com.svmc.android.locationsupportteam.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 3/19/2019
 */

public class User {

    @SerializedName("user_id")
    private String userId;

    @SerializedName("email")
    private String email;

    @SerializedName("username")
    private String username;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("phone")
    private String phoneNumber;

    @SerializedName("created_time")
    private long createdTime;

    @SerializedName("status")
    private boolean status;

    @SerializedName("url_image")
    private String urlImage;

    @SerializedName("description")
    private String description;

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static User fake() {
        User user = new User();
        user.setUserId("aaa");
        return user;
    }

    public static List<User> fakeList() {
        List<User> users = new ArrayList<>();

        User user = new User();
        user.setUserId("aaa");
        users.add(user);
        user.setUserId("bbb");
        users.add(user);
        user.setUserId("ccc");
        users.add(user);
        user.setUserId("ddd");
        users.add(user);
        user.setUserId("eee");
        users.add(user);
        user.setUserId("bbb");
        users.add(user);
        return users;
    }
}
