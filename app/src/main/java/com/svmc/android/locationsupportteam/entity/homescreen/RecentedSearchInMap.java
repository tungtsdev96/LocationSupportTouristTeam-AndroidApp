package com.svmc.android.locationsupportteam.entity.homescreen;

public class RecentedSearchInMap {

    private String placeId;

    private String namePlace;

    private String nameAddress;

    private String formattedAddress;

    public RecentedSearchInMap(String placeId, String namePlace, String nameAddress) {
        this.placeId = placeId;
        this.namePlace = namePlace;
        this.nameAddress = nameAddress;
    }

    public RecentedSearchInMap(String namePlace, String nameAddress) {
        this.namePlace = namePlace;
        this.nameAddress = nameAddress;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getNameAddress() {
        return nameAddress;
    }

    public void setNameAddress(String nameAddress) {
        this.nameAddress = nameAddress;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public static RecentedSearchInMap fake() {
        RecentedSearchInMap recentedSearchInMap = new RecentedSearchInMap("Benh vien binh xuyen", "Vinh Phuc");
        return recentedSearchInMap;
    }
}
