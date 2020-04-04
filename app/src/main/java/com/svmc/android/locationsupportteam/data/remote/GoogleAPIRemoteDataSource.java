package com.svmc.android.locationsupportteam.data.remote;

import com.google.android.gms.common.api.Result;
import com.svmc.android.locationsupportteam.entity.googlemap.distance.ResultDistance;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlaceAutocomplete;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;

import retrofit2.Call;

/**
 * Created by TungTS on 5/6/2019
 */

public class GoogleAPIRemoteDataSource {


    /////////////////////////// START PLACE API /////////////////////////////////////////////////////////////////////////////////
    /***
     * Search place
     * @param query
     * @param listener
     */
    public void searchPlace(String query, final FinishedListener<ResultPlace> listener) {
        ApiFactory
                .getApiGoogle()
                .findPlaceRequest(query, ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP)
                .enqueue(new BaseCallBack<ResultPlace>() {
                    @Override
                    public void onSuccess(ResultPlace result) {
                        listener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        listener.onFailure(t);
                    }
                });

    }

    /***
     * Place auto caomplete
     * @param query
     * @param location
     * @param finishedListener
     */
    Call<ResultPlaceAutocomplete> placeAutoCompleteCall;
    public void placeAutocomplete(String query, String location, final FinishedListener<ResultPlaceAutocomplete> finishedListener) {
        if (placeAutoCompleteCall != null && placeAutoCompleteCall.isExecuted()) {
            placeAutoCompleteCall.cancel();
        }

        placeAutoCompleteCall = ApiFactory
                .getApiGoogle()
                .getResultAutoCompletePlace(query, location, ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP);
        placeAutoCompleteCall.enqueue(new BaseCallBack<ResultPlaceAutocomplete>() {
            @Override
            public void onSuccess(ResultPlaceAutocomplete result) {
                finishedListener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                finishedListener.onFailure(t);
            }
        });
    }

    /***
     * Nearby search
     * @param location
     * @param radius
     * @param type
     * @param openNow
     * @param minPrice
     * @param maxPrice
     * @param pageToken
     * @param listener
     */
    public void nearBySearchPlace(String location, int radius, String type, boolean openNow, int minPrice, int maxPrice, String pageToken, final FinishedListener<ResultPlace> listener) {
        ApiFactory
                .getApiGoogle()
                .nearbySearch(location, radius, null, null, type, String.valueOf(openNow), minPrice, maxPrice, pageToken, ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP)
                .enqueue(new BaseCallBack<ResultPlace>() {
                    @Override
                    public void onSuccess(ResultPlace result) {
                        listener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        listener.onFailure(t);
                    }
                });
    }

    /**
     * get simple place
     * @param placeId
     * @param listener
     */
    Call<ResultPlace> callSimplePlace;
    public void getSimplePlace(String placeId, final FinishedListener<ResultPlace> listener) {
        callSimplePlace = ApiFactory.getApiGoogle().getSimpleInforPlace(placeId, ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP);

        callSimplePlace.enqueue(new BaseCallBack<ResultPlace>() {
            @Override
            public void onSuccess(ResultPlace result) {
                listener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void cancelCallSimplePlace() {
        if (callSimplePlace != null && callSimplePlace.isExecuted()) {
            callSimplePlace.cancel();
        }
    }

    /**
     * place detail
     * @param placeId
     * @param listener
     */
    Call<ResultPlace> callDetailPlace;
    public void getPlaceDetail(String placeId, final FinishedListener<ResultPlace> listener) {
        callDetailPlace =  ApiFactory
                .getApiGoogle()
                .getDetailPlaceById(placeId, ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP);
        
        callDetailPlace.enqueue(new BaseCallBack<ResultPlace>() {
                    @Override
                    public void onSuccess(ResultPlace result) {
                        listener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        listener.onFailure(t);
                    }
                });
    }
    
    public void cancelCallDetailPlace() {
        if (callDetailPlace != null && callDetailPlace.isExecuted()) {
            callDetailPlace.cancel();
        }
    }
    
    public static String getUrlPhoto(int max, boolean isMaxHeight, String photoReference) {
        ///maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=YOUR_API_KEY
        String url = "https://maps.googleapis.com/maps/api/place/photo?";
        String paramsHeight;
        if (isMaxHeight) {
            paramsHeight = "maxheight=" + max + "&";
        } else {
            paramsHeight = "maxwidth=" + max + "&";
        }

        url += paramsHeight;

        url += "photoreference=" + photoReference + "&" + "key=" + ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP;

        return url;
    }

    /////////////////////////// END PLACE API /////////////////////////////////////////////////////////////////////////////////

    Call<ResultDistance> callDistance;
    public void caculDistance(String origin, String destination, final FinishedListener<ResultDistance> listener) {
        callDistance = ApiFactory
                .getApiGoogle()
                .getDistanceFromLocation(origin, destination, ApiConfig.ConfigKeyGoogleMap.KEY_GOOGLE_MAP);

        callDistance.enqueue(new BaseCallBack<ResultDistance>() {
                    @Override
                    public void onSuccess(ResultDistance result) {
                        listener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        listener.onFailure(t);
                    }
                });
    }

    public void cancelCallDistance() {
        if (callDistance != null && callDistance.isExecuted()) {
            callDistance.cancel();
        }
    }

}
