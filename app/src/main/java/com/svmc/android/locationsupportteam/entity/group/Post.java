package com.svmc.android.locationsupportteam.entity.group;

import android.location.Location;

import java.util.List;

/**
 * Created by TUNGTS on 4/4/2019
 */

public class Post {

    private String postId;

    private String content;

    private int cntLike;

    private int cntCmt;

    private List cmt;

    private Location location;

    private String userIdCreated;

    private String groupId;

    public static Post getDemoPost() {
        Post post = new Post();
        post.groupId = "Aaaa";
        return post;
    }

}
