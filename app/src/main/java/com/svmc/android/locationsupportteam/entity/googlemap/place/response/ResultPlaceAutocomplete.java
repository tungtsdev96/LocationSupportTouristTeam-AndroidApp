package com.svmc.android.locationsupportteam.entity.googlemap.place.response;

import com.google.gson.annotations.SerializedName;
import com.svmc.android.locationsupportteam.entity.googlemap.BaseResponseGoogle;
import com.svmc.android.locationsupportteam.entity.googlemap.place.autocomplete.Prediction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUNGTS on 4/16/2019
 */

public class ResultPlaceAutocomplete extends BaseResponseGoogle {

    @SerializedName("predictions")
    private List<Prediction> predictions;

    public List<Prediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Prediction> predictions) {
        this.predictions = predictions;
    }

    public static ResultPlaceAutocomplete fake() {
        ResultPlaceAutocomplete resultPlaceAutocomplete = new ResultPlaceAutocomplete();
        List<Prediction> predictions = new ArrayList<>();
        double x = Math.random();
        for (int i = 0; i < x * 10; i++) {
            predictions.add(Prediction.fake());
        }
        resultPlaceAutocomplete.setPredictions(predictions);
        return resultPlaceAutocomplete;
    }
}
