package com.svmc.android.locationsupportteam.entity.googlemap.direction;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class MyPolyLine {

    /**
     * points : q~ttG|i}_M_AjDs@vCm@vBy@tCADEPw@rB]`ASf@Ud@q@zA{@pCg@|A[jA
     */

    @SerializedName("points")
    private String points;

    private List<LocationPoint> pointsLatLon;

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public List<LocationPoint> getPointsLatLon() {
        return decodePolyLine(points);
    }

    public void setPointsLatLon(List<LocationPoint> pointsLatLon) {
        this.pointsLatLon = pointsLatLon;
    }

    public List<LocationPoint> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LocationPoint> decoded = new ArrayList<LocationPoint>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LocationPoint(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }
}
