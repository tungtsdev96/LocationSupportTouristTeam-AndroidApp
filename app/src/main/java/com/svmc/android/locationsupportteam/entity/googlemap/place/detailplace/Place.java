package com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tungts on 3/28/2019.
 */

public class Place {

    @SerializedName("address_components")
    private List<AddressComponent> addressComponents;

    @SerializedName("formatted_address")
    private String formattedAddress;

    @SerializedName("formatted_phone_number")
    private String formattedPhoneNumber;

    @SerializedName("geometry")
    private Geometry geometry;

    @SerializedName("icon")
    private String icon;

    @SerializedName("international_phone_number")
    private String internationalPhoneNumber;

    @SerializedName("name")
    private String name;

    @SerializedName("opening_hours")
    private OpeningHours openingHours;

    @SerializedName("photos")
    private List<Photo> photos;

    @SerializedName("place_id")
    private String placeId;

    @SerializedName("price_level")
    private int priceLevel;

    @SerializedName("rating")
    private double rating;

    @SerializedName("reviews")
    private List<Review> reviews;

    @SerializedName("types")
    private List<String> types;

    @SerializedName("user_ratings_total")
    private int userRatingTotal;

    @SerializedName("vicinity")
    private String vicinity;

    @SerializedName("website")
    private String website;

    public Place() {}

    public List<AddressComponent> getAddressComponents() {
        return addressComponents;
    }

    public void setAddressComponents(List<AddressComponent> addressComponents) {
        this.addressComponents = addressComponents;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public int getUserRatingTotal() {
        return userRatingTotal;
    }

    public void setUserRatingTotal(int userRatingTotal) {
        this.userRatingTotal = userRatingTotal;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getPriceLevel() {
        return priceLevel;
    }

    public void setPriceLevel(int priceLevel) {
        this.priceLevel = priceLevel;
    }

    public static Place fake() {
        Place place = new Place();
        place.setPlaceId("aaaa");
        place.setName("aaaa");
        return place;
    }

    /**
     * get name "administrative_area_level_1"
     * @return
     */
    public String getNameAdministrativeArea() {
        if (addressComponents == null) return "";
        for (AddressComponent address : addressComponents) {
            for (String s : address.getTypes()) {
                if (s.equals("administrative_area_level_1")) return address.getLongName();
            }
        }
        return "";
    }
}
