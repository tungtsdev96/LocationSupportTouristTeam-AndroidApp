package com.svmc.android.locationsupportteam.data.remote;

import com.svmc.android.locationsupportteam.entity.googlemap.direction.ResultPathGoogle;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.entity.homescreen.HomeScreenModel;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by TungTS on 5/9/2019
 */

public class RecommendDataRemote {

    public void getRecommendInPlace(String location, final FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>> listener) {
        ApiFactory
                .getApiRecommend()
                .getListRecommendPlace(location)
                .enqueue(new BaseCallBack<BaseResultResponse<ArrayList<HomeScreenModel>>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<ArrayList<HomeScreenModel>> result) {
                        listener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        listener.onFailure(t);
                    }
                });
    }

    Call<ResultPlace> callNearBySearch;
    public void getPlaceNearBySearch(String location, int type, String pageToken, final FinishedListener<ResultPlace> listener) {
        callNearBySearch = ApiFactory
                .getApiRecommend()
                .getListNearBySearch(location, type, pageToken);

        callNearBySearch.enqueue(new BaseCallBack<ResultPlace>() {
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

    public void cancelCallNearBySearch() {
        if (callNearBySearch != null && callNearBySearch.isExecuted()) {
            callNearBySearch.cancel();
        }
    }

    Call<ResultPathGoogle> callPathGoogle;
    public void getPath(String origin, String destination, String mode, final FinishedListener<ResultPathGoogle> finishedListener) {
        callPathGoogle = ApiFactory
                .getApiRecommend()
                .getPath(origin, destination, mode);

        callPathGoogle.enqueue(new BaseCallBack<ResultPathGoogle>() {
            @Override
            public void onSuccess(ResultPathGoogle result) {
                finishedListener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                finishedListener.onFailure(t);
            }
        });
    }

    public void cancelCallPath() {
        if (callPathGoogle != null && callPathGoogle.isExecuted()) {
            callPathGoogle.cancel();
        }
    }

    Call<BaseResultResponse<ArrayList<HomeScreenModel>>> callNewFeed;
    public void getNewFeedRecommend(String ids, String location, String queryCity, final FinishedListener<BaseResultResponse<ArrayList<HomeScreenModel>>> listener) {

        callNewFeed = ApiFactory.getApiRecommend().getRecommendNewFeed(ids, location, queryCity);

        callNewFeed.enqueue(new BaseCallBack<BaseResultResponse<ArrayList<HomeScreenModel>>>() {
            @Override
            public void onSuccess(BaseResultResponse<ArrayList<HomeScreenModel>> result) {
                listener.onFinished(result);
            }

            @Override
            public void onFailure(Throwable t) {
                listener.onFailure(t);
            }
        });
    }

    public void cancelCallNewFeed() {
        if (callNewFeed != null && callNewFeed.isExecuted()) {
            callNewFeed.cancel();
        }
    }

    public void getListPlaceInQueryCity(String query, String pagetoken, final FinishedListener<ResultPlace> listener) {
        ApiFactory.getApiRecommend().getListPlaceInQueryCity(query, pagetoken)
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
}
