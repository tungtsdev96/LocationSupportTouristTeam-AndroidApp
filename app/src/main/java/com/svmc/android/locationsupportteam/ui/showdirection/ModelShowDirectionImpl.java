package com.svmc.android.locationsupportteam.ui.showdirection;

import com.svmc.android.locationsupportteam.data.remote.RecommendDataRemote;
import com.svmc.android.locationsupportteam.entity.googlemap.direction.ResultPathGoogle;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;

/**
 * Create by TungTS on 5/11/2019
 */

public class ModelShowDirectionImpl implements MVPShowDirection.IModelShowDirection {

    private RecommendDataRemote recommendDataRemote;

    public ModelShowDirectionImpl() {
        recommendDataRemote = new RecommendDataRemote();
    }

    @Override
    public void getPath(String origin, String destination, String mode, FinishedListener<ResultPathGoogle> finishedListener) {
        recommendDataRemote.getPath(origin, destination, mode, finishedListener);
    }

    @Override
    public void onStop() {
        recommendDataRemote.cancelCallPath();
    }

}
