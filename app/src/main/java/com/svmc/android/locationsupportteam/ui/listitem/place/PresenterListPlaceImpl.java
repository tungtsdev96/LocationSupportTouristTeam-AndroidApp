package com.svmc.android.locationsupportteam.ui.listitem.place;

import android.util.Log;

import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.entity.googlemap.place.response.ResultPlace;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.ui.common.dialog.FilterDialog;
import com.svmc.android.locationsupportteam.utils.ErrCode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PresenterListPlaceImpl implements MVPListPlace.IPresenterListPlace {

    private boolean isFirstLoad = true;
    private MVPListPlace.IViewListPlace viewListPlace;
    private MVPListPlace.IModelListPlace modelListPlace;

    public PresenterListPlaceImpl(MVPListPlace.IViewListPlace iViewListPlace) {
        this.viewListPlace = iViewListPlace;
        this.viewListPlace.setPresenter(this);
        modelListPlace = new ModelListPlaceImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void setFirstLoad() {
        isFirstLoad = true;
    }

    @Override
    public void showListPlace(String location, final String pageToken, int type) {
        modelListPlace.loadPlaceBytype(location, type, pageToken, new FinishedListener<ResultPlace>() {
            @Override
            public void onFinished(ResultPlace result) {
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {
                    viewListPlace.showUIListPlace(result, isFirstLoad);
                    isFirstLoad = false;
                }
            }

            @Override
            public void onFailure(Throwable t) {
                viewListPlace.onLoadDataErr();
            }
        });
    }

    @Override
    public void showListPlace(String queryCity, String pagetoken) {
        modelListPlace.loadPlaceQueryCity(queryCity, pagetoken, new FinishedListener<ResultPlace>() {
            @Override
            public void onFinished(ResultPlace result) {
                if (result.getStatus().equals(ErrCode.GGMapCode.OK)) {
                    viewListPlace.showUIListPlace(result, isFirstLoad);
                    isFirstLoad = false;
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void filterBy(List coppysItems, boolean isOpenNow, int filterType) {
        List items = new ArrayList();

        if (isOpenNow) {
            for (Object o : coppysItems) {
                Place p = (Place) o;
                if (p.getOpeningHours() != null && p.getOpeningHours().isOpenNow()) {
                    items.add(p);
                }
            }
        } else items.addAll(coppysItems);

        switch (filterType) {
            case FilterDialog.PRICE_ASCENDING:
                Collections.sort(items, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Place p1 = (Place) o1;
                        Place p2 = (Place) o2;
                        if (p1.getPriceLevel() > p2.getPriceLevel()) {
                            return 1;
                        } else return 0;
                    }
                });
                break;
            case FilterDialog.PRICE_DESCENDING:
                Collections.sort(items, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Place p1 = (Place) o1;
                        Place p2 = (Place) o2;
                        if (p1.getPriceLevel() < p2.getPriceLevel()) {
                            return 1;
                        } else return 0;
                    }
                });
                break;
            case FilterDialog.RATING_ASCENDING:
                Collections.sort(items, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Place p1 = (Place) o1;
                        Place p2 = (Place) o2;
                        if (p1.getRating() > p2.getRating()) {
                            return 1;
                        } else return -1;
                    }
                });
                break;
            case FilterDialog.RATING_DESCENDING:
                Collections.sort(items, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Place p1 = (Place) o1;
                        Place p2 = (Place) o2;
                        if (p1.getRating() < p2.getRating()) {
                            return 1;
                        } else return -1;
                    }
                });
                break;
        }

        viewListPlace.showListPlaceWhenFilter(items);
    }
}
