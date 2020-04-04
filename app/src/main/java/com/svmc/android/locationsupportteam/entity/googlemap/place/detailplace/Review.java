package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class Review {

    @SerializedName("author_name")
    private String authorName;

    @SerializedName("author_url")
    private String authorUrl;

    @SerializedName("language")
    private String language;

    @SerializedName("profile_photo_url")
    private String profilePhotoUrl;

    @SerializedName("rating")
    private double rating;

    @SerializedName("relative_time_description")
    private String relativeTimeDescription;

    @SerializedName("text")
    private String contentReview;

    @SerializedName("time")
    private long time;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getRelativeTimeDescription() {
        return relativeTimeDescription;
    }

    public void setRelativeTimeDescription(String relativeTimeDescription) {
        this.relativeTimeDescription = relativeTimeDescription;
    }

    public String getContentReview() {
        return contentReview;
    }

    public void setContentReview(String contentReview) {
        this.contentReview = contentReview;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
