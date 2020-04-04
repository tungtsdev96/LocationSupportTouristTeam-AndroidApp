package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.DisplayInfor;

/**
 * Create by TungTS on 5/11/2019
 */

public class Step {

    @SerializedName("distance")
    private DisplayInfor distance;

    @SerializedName("duration")
    private DisplayInfor duration;

    @SerializedName("end_location")
    private LocationPoint endLocation;

    @SerializedName("html_instructions")
    private String htmlInstructions;

    @SerializedName("polyline")
    private MyPolyLine polyline;

    @SerializedName("start_location")
    private LocationPoint startLocation;

    @SerializedName("travel_mode")
    private String travelMode;

    public DisplayInfor getDistance() {
        return distance;
    }

    public void setDistance(DisplayInfor distance) {
        this.distance = distance;
    }

    public DisplayInfor getDuration() {
        return duration;
    }

    public void setDuration(DisplayInfor duration) {
        this.duration = duration;
    }

    public LocationPoint getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LocationPoint endLocation) {
        this.endLocation = endLocation;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    public MyPolyLine getPolyline() {
        return polyline;
    }

    public void setPolyline(MyPolyLine polyline) {
        this.polyline = polyline;
    }

    public LocationPoint getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationPoint startLocation) {
        this.startLocation = startLocation;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
}
