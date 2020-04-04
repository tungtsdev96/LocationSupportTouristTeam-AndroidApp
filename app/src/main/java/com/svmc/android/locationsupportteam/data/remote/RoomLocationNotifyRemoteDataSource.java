package com.svmc.android.locationsupportteam.data.remote;

import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Create by TungTS on 5/3/2019
 */

public class RoomLocationNotifyRemoteDataSource extends BaseRemoteDataSource {

    /***
     * Get all notification
     * @param finishedListener
     */
    public void getAllNotify(final FinishedListener<BaseResultResponse<ArrayList<RoomLocationNotification>>> finishedListener) {
        ApiFactory
                .getApiNotification()
                .getAllNotify(getIdToken())
                .enqueue(new BaseCallBack<BaseResultResponse<ArrayList<RoomLocationNotification>>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<ArrayList<RoomLocationNotification>> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });

    }

    /***
     * remove notification
     * @param notifyId
     * @param finishedListener
     */
    public void removeNotify(String notifyId, final FinishedListener<BaseResultResponse<JSONObject>> finishedListener) {
        ApiFactory
                .getApiNotification()
                .removeNotify(getIdToken(), notifyId)
                .enqueue(new BaseCallBack<BaseResultResponse<JSONObject>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<JSONObject> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }

    public void resolveAlert(String senderId, final FinishedListener<BaseResultResponse<String>> finishedListener) {
        ApiFactory
                .getApiNotification()
                .resolveAlert(senderId)
                .enqueue(new BaseCallBack<BaseResultResponse<String>>() {
                    @Override
                    public void onSuccess(BaseResultResponse<String> result) {
                        finishedListener.onFinished(result);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        finishedListener.onFailure(t);
                    }
                });
    }
}
