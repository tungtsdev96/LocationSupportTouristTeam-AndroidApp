package com.svmc.android.locationsupportteam.network.api;

import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TUNGTS on 3/16/2019
 */

public interface ApiGoogle {

    /********** Place API ************/

    // Search
    @GET("/maps/api/place/findplacefromtext/json?inputtype=textquery")
    Call<ResultPlace> findPlaceRequest(@Query("input") String queryText,
                                       @Query("key") String key);

    @GET("/maps/api/place/nearbysearch/json")
    Call<ResultPlace> nearbySearch(@Query("location") String location,
                                   @Query("radius") int radius,
                                   @Query("rankby") String rankBy,
                                   @Query("keyword") String keyWord,
                                   @Query("type") String type,
                                   @Query("opennow") String openNow,
                                   @Query("minprice") int minPrice,
                                   @Query("maxprice") int maxPrice,
                                   @Query("pagetoken") String pageToken,
                                   @Query("key") String key);


    @GET("/maps/api/place/textsearch/json")
    Call<ResultPlace> textSearch(@Query("query") String queryText,
                                 @Query("location") String location,
                                 @Query("radius") int radius,
                                 @Query("type") String type,
                                 @Query("opennow") String openNow,
                                 @Query("minprice") int minPrice,
                                 @Query("maxprice") int maxPrice,
                                 @Query("pagetoken") String pageToken,
                                 @Query("key") String key);

    // Detail
    @GET("/maps/api/place/details/json?language=vi&fields=address_component,formatted_address,formatted_phone_number,geometry,icon,international_phone_number,name,permanently_closed,opening_hours,photo,place_id,type,user_ratings_total,vicinity,website,price_level,rating,review")
    Call<ResultPlace> getDetailPlaceById(@Query("placeid") String placeId,
                                         @Query("key") String key);

    @GET("/maps/api/place/details/json?language=vi&fields=address_component,formatted_address,place_id,type,user_ratings_total,geometry,rating,photo,name")
    Call<ResultPlace> getSimpleInforPlace(@Query("placeid") String placeId,
                                             @Query("key") String key);

    // Photos /maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=YOUR_API_KEY


    // Place AutoComplete
    @GET("/maps/api/place/autocomplete/json")
    Call<ResultPlaceAutocomplete> getResultAutoCompletePlace(@Query("input") String queryText,
                                                             @Query("location") String location,
                                                             @Query("key") String key);

    /********** DistanMatrixAPI **********/
    /***
     * From placeID -> PlaceId  :
     * From location -> PlaceId
     * /maps/api/distancematrix/json?origins=20.9955085,105.846057&destinations=place_id:ChIJQ6BVP0YdNTERMxronuGXeQo&key=AIzaSyA0JRDEYIqGUdqnyRCNl0gWX4godQO37XA
     * /maps/api/distancematrix/json?origins=place_id:ChIJlclXM5WrNTERDqL5tGu_ugE&destinations=place_id:ChIJQ6BVP0YdNTERMxronuGXeQo&key=AIzaSyA0JRDEYIqGUdqnyRCNl0gWX4godQO37XA
     */
    @GET("/maps/api/distancematrix/json?language=vi")
    Call<ResultDistance> getDistanceFromLocation(@Query("origins") String origins,
                                                 @Query("destinations") String destination,
                                                 @Query("key") String key);

}
