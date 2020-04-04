package com.svmc.android.locationsupportteam.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.PlaceType;
import com.svmc.android.locationsupportteam.entity.CityProvince;
import com.svmc.android.locationsupportteam.entity.SubPlaceType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by TungTS on 5/14/2019
 */

public class FileUtils {

    public static ArrayList<SubPlaceType> getAllSubPlaceTypes(Context context) {
        InputStream iStream = context.getResources().openRawResource(R.raw.recommend_place_type);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = byteStream.toString();
        if (json != null) {
            Type type = new TypeToken<ArrayList<SubPlaceType>>() {
            }.getType();
            return new Gson().fromJson(json, type);
        }
        return null;
    }

    public static ArrayList<PlaceType> getPlaceTypes(Context context) {
        InputStream iStream = context.getResources().openRawResource(R.raw.placetype);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = byteStream.toString();
        if (json != null) {
            Type type = new TypeToken<ArrayList<PlaceType>>() {
            }.getType();
            return new Gson().fromJson(json, type);
        }
        return null;
    }

    public static ArrayList<CityProvince> getListCityAndProvince(Context context) {
        InputStream iStream = context.getResources().openRawResource(R.raw.vn_city_province);
        ByteArrayOutputStream byteStream = null;
        try {
            byte[] buffer = new byte[iStream.available()];
            iStream.read(buffer);
            byteStream = new ByteArrayOutputStream();
            byteStream.write(buffer);
            byteStream.close();
            iStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = byteStream.toString();
        if (json != null) {
            Type type = new TypeToken<ArrayList<CityProvince>>() {
            }.getType();
            return new Gson().fromJson(json, type);
        }
        return null;
    }

}
