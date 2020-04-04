package com.svmc.android.locationsupportteam.network.api;

import com.svmc.android.locationsupportteam.entity.googlemap.direction.ResultPathGoogle;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by TungTS on 5/9/2019
 */

public interface ApiRecommend {

    /***
     * recommend place around location
     */
    @GET("/api/recommend-place")
    Call<BaseResultResponse<ArrayList<HomeScreenModel>>> getListRecommendPlace(@Query("location") String location);

    /**
     * near by search
     */
    @GET("/api/near-by-search")
    Call<ResultPlace> getListNearBySearch(@Query("location") String location,
                                          @Query("type") int type,
                                          @Query("pagetoken") String pageToken);

    /**
     * near by search city
     */
    @GET("/api/near-by-search/city")
    Call<ResultPlace> getListPlaceInQueryCity(@Query("query") String query,
                                              @Query("pagetoken") String pagetoken);

    /**
     * get direction
     */
    @GET("/api/get-direction")
    Call<ResultPathGoogle> getPath(@Query("origin") String origin,
                                   @Query("destination") String destination,
                                   @Query("travel_mode") String mode);

    /**
     * new feed
     */
    @GET("/api/newfeed/recommend")
    Call<BaseResultResponse<ArrayList<HomeScreenModel>>> getRecommendNewFeed(@Query("placetypes") String listPlaceTypeIds,
                                                                             @Query("location") String location,
                                                                             @Query("queryCity") String queryCity);
}
