package com.svmc.android.locationsupportteam.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.svmc.android.locationsupportteam.R;

/**
 * Created by TungTS on 5/6/2019
 */

public class GlideUtils {

    public static void loadImageByDrawable(Context context, ImageView imageView, int drawableImage){
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_place_hoder)
                .error(R.drawable.ic_place_hoder);

        Glide.with(context)
                .load(drawableImage)
                .apply(options)
                .into(imageView);
    }

    public static void loadImageByPath(Context context, ImageView imageView, String path){
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.ic_place_hoder)
                .error(R.drawable.ic_place_hoder);

        Glide.with(context)
                .load(path)
                .apply(options)
                .into(imageView);
    }

    public static void loadImageWrapContent(Context context, ImageView imageView, String url, int heigth){
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.ic_place_hoder)
                .error(R.drawable.ic_place_hoder)
                .dontTransform()
                .override(Target.SIZE_ORIGINAL, heigth);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

}
