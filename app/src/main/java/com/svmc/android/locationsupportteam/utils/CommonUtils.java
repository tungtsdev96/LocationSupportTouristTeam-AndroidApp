package com.svmc.android.locationsupportteam.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.ColorRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.PlaceType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by TUNGTS on 3/3/2019
 */

public class CommonUtils {

    /***
     * Check internet android
     * @param context
     * @return
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        Log.d(CommonUtils.class.getSimpleName(), (networkInfo != null && networkInfo.isConnected()) + "");
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static int getDeviceWith(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static String getCurrentDate(String fomart) {
        return "17/11/96";
    }

    public static float getMarkerColor(int pos) {
        switch (pos) {
            case 1:
                return BitmapDescriptorFactory.HUE_ORANGE;
            case 2:
                return BitmapDescriptorFactory.HUE_GREEN;
            case 3:
                return BitmapDescriptorFactory.HUE_CYAN;
            case 4:
                return BitmapDescriptorFactory.HUE_MAGENTA;
            case 5:
                return BitmapDescriptorFactory.HUE_BLUE;
            case 6:
                return BitmapDescriptorFactory.HUE_AZURE;
            case 7:
                return BitmapDescriptorFactory.HUE_YELLOW;
            case 8:
                return BitmapDescriptorFactory.HUE_ROSE;
            case 9:
                return BitmapDescriptorFactory.HUE_VIOLET;
            default:
                return BitmapDescriptorFactory.HUE_RED;
        }
    }

    public static String randomUrlImg(int pos) {
        switch (pos % 10) {
            case 0:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F1.jpg?alt=media&token=ce607fe0-c2ca-4217-8bd3-e18709b0d27f";
            case 1:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F10.jpg?alt=media&token=db3257cc-8c99-40cf-8d2d-73e106f64376";
            case 2:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F2.jpg?alt=media&token=59b43707-16d3-4452-a2ae-5123fcc52780";
            case 3:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F3.jpg?alt=media&token=f933f4cf-a60c-4010-a600-fedb40b1e30d";
            case 4:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F4.jpg?alt=media&token=e15f7a57-bf44-492b-896b-cadf3b2a4753";
            case 5:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F5.jpg?alt=media&token=b65ceb59-674f-43fd-983c-7935d2471f9b";
            case 6:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F6.jpg?alt=media&token=c3d3e713-f551-4636-bbc4-247d0b8ab20b";
            case 7:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F7.jpg?alt=media&token=af2fb0a2-cf0c-4d2d-b9b2-189aa3682004";
            case 8:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F8.jpg?alt=media&token=fc8bed2b-d2f7-43d9-b482-d3955570a138";
            case 9:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F9.jpg?alt=media&token=a8d56a58-8bf5-4e69-8d5b-aa8501310ab0";
            default:
                return "https://firebasestorage.googleapis.com/v0/b/locationsupporttouristteam.appspot.com/o/avatars%2F9.jpg?alt=media&token=a8d56a58-8bf5-4e69-8d5b-aa8501310ab0";
        }
    }

    public static int getDrawableResourceFromString(Context context, String name) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resourceId;
    }

    public static Bitmap createImageRounded(Context context, int width, int height, String name)
    {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paintCicle = new Paint();
        Paint paintText = new Paint();

        Rect rect = new Rect(0, 0, width, height);
        RectF rectF = new RectF(rect);
        float density = context.getResources().getDisplayMetrics().density;
        float roundPx = 100*density;

        paintCicle.setColor(Color.LTGRAY);
        paintCicle.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        // Set Border For Circle
        paintCicle.setStyle(Paint.Style.STROKE);
        paintCicle.setStrokeWidth(4.0f);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paintCicle);

        paintText.setColor(Color.RED);
        paintText.setTextSize(30);

        canvas.drawText(name, 10, 10, paintText);

        return output;
    }

    public static Bitmap createImage(int width, int height, int color, String name) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//
//        Paint paint = new Paint();
//        Paint circlePaint = new Paint();
//
//        paint.setColor(Color.WHITE);
//        paint.setTextSize(18f);
//        paint.setAntiAlias(true);
//        paint.setTextAlign(Paint.Align.CENTER);
//
//        Rect bounds = new Rect();
//        paint.getTextBounds(text, 0, text.length(), bounds);
//
//        circlePaint.setColor(Color.RED);
//        circlePaint.setAntiAlias(true);
//
//        canvas.drawCircle(-3, 15 - (bounds.height() / 2), bounds.width() + 5, circlePaint);
//
//        canvas.drawText(name, -3, 15, paint);
        return bitmap;
    }

}
