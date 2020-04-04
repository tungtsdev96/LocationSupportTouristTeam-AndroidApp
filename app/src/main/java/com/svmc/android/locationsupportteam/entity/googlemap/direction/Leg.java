package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.DisplayInfor;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class Leg {

    @SerializedName("distance")
    private DisplayInfor distance;

    @SerializedName("duration")
    private DisplayInfor duration;

    @SerializedName("end_address")
    private String endAddress;

    @SerializedName("end_location")
    private LocationPoint endLocation;

    @SerializedName("start_address")
    private String startAddress;

    @SerializedName("start_location")
    private LocationPoint startLocation;

    @SerializedName("steps")
    private List<Step> steps;

    private List<LocationPoint> allLatLng;

    public List<LocationPoint> getAllLatLon() {
        List<LocationPoint> result = new ArrayList<>();
        for (int i=0;i<steps.size();i++){
            Step step= steps.get(i);
            result.addAll(step.getPolyline().getPointsLatLon());
        }
        return result;
    }


    public void setAllLatLon(List<LocationPoint> allLatLng) {
        this.allLatLng = allLatLng;
    }

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

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public LocationPoint getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LocationPoint endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public LocationPoint getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LocationPoint startLocation) {
        this.startLocation = startLocation;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}
