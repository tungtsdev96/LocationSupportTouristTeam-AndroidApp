package com.svmc.android.locationsupportteam.ui.showdirection;

import com.google.android.gms.maps.model.LatLng;
import com.svmc.android.locationsupportteam.entity.googlemap.LocationPoint;
import com.svmc.android.locationsupportteam.entity.googlemap.direction.Bound;
import com.svmc.android.locationsupportteam.entity.googlemap.direction.ResultPathGoogle;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;

import java.util.List;

/**
 * Create by TungTS on 5/11/2019
 */

public class MVPShowDirection {

    public interface IViewShowDirection {

        void chooseDriving();
        void chooseBicycling();
        void chooseWalking();

        void showDirection(ResultPathGoogle resultPathGoogle);
        void drawPath(List<LatLng> listDriving);

        void onNoResult();

        void moveCameraTo(Bound bound);

        void moveCameraTo(LocationPoint point);

        void onErr();

        void setLoading();

        void onFindDone();

        void showInforDes();
    }

    public interface IPresenterShowDirection {

        void getPath(String origin, String destination, String mode);

        void onStop();

    }

    public interface IModelShowDirection {

        void getPath(String origin, String destination, String mode, FinishedListener<ResultPathGoogle> finishedListener);

        void onStop();

    }

}
