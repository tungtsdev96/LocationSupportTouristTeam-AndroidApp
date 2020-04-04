package com.svmc.android.locationsupportteam.ui.showdirection;

import com.svmc.android.locationsupportteam.entity.googlemap.direction.ResultPathGoogle;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.utils.ErrCode;

/**
 * Create by TungTS on 5/11/2019
 */

public class PresenterShowDirectionImpl implements MVPShowDirection.IPresenterShowDirection {

    private MVPShowDirection.IViewShowDirection viewShowDirection;
    private MVPShowDirection.IModelShowDirection modelShowDirection;

    public PresenterShowDirectionImpl(MVPShowDirection.IViewShowDirection viewShowDirection) {
        this.viewShowDirection = viewShowDirection;
        modelShowDirection = new ModelShowDirectionImpl();
    }

    @Override
    public void getPath(String origin, String destination, String mode) {
        viewShowDirection.setLoading();
        modelShowDirection.getPath(origin, destination, mode, new FinishedListener<ResultPathGoogle>() {
            @Override
            public void onFinished(ResultPathGoogle result) {
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {
                    viewShowDirection.showDirection(result);
                    viewShowDirection.showInforDes();
                } else {
                    viewShowDirection.onNoResult();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewShowDirection.onErr();
            }
        });
    }

    @Override
    public void onStop() {
        modelShowDirection.onStop();
    }

}
