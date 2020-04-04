package com.svmc.android.locationsupportteam.ui.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.utils.CommonUtils;


/**
 * Created by TUNGTS on 3/30/2019
 */

public class CustomRatingView extends LinearLayout {

    private int type;
    private double score;
    private int defaultSize;
    private Context context;

    public CustomRatingView(Context context) {
        super(context);
    }

    public CustomRatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRatingView);
        this.score = typedArray.getFloat(R.styleable.CustomRatingView_score, 0);
        this.type = typedArray.getInt(R.styleable.CustomRatingView_type, 0);
        this.defaultSize = CommonUtils.dpToPx(12);
        typedArray.recycle();
        innitView();
    }

    public CustomRatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomRatingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScore(double score) {
        this.score = score;
        removeAllViews();
        invalidate();
        innitView();
    }

    private void innitView() {

//        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setLayoutParams(layoutParams);
        this.setOrientation(HORIZONTAL);

        int numberStarGreen = (int) this.score;
        // list star green dark
        for (int i = 0; i < numberStarGreen; i++) {
            ImageView img = new ImageView(context);
            img.setLayoutParams(new LayoutParams(this.defaultSize, this.defaultSize));
            img.setImageResource(
                    this.type == 0 ? R.drawable.ic_star_green_dark : R.drawable.ic_star_orange
            );
            this.addView(img);
        }

        if (score - numberStarGreen < 0.4) {
            for (int i = 0; i < 5 - numberStarGreen; i++) {
                ImageView img = new ImageView(context);
                img.setLayoutParams(new LayoutParams(this.defaultSize, this.defaultSize));
                img.setImageResource(R.drawable.ic_star_gray);
                this.addView(img);
            }
        } else if ((score - numberStarGreen) >= 0.4 && (score - numberStarGreen) <= 0.79){
            ImageView img_half = new ImageView(context);
            img_half.setLayoutParams(new LayoutParams(this.defaultSize, this.defaultSize));
            img_half.setImageResource(
                    this.type == 0 ? R.drawable.ic_star_half_green_dark: R.drawable.ic_star_half_orange
            );
            this.addView(img_half);

            for (int i = 0; i < 4 - numberStarGreen; i++) {
                ImageView img = new ImageView(context);
                img.setLayoutParams(new LayoutParams(this.defaultSize, this.defaultSize));
                img.setImageResource(R.drawable.ic_star_gray);
                this.addView(img);
            }

        } else {
            ImageView img_half = new ImageView(context);
            img_half.setLayoutParams(new LayoutParams(this.defaultSize, this.defaultSize));
            img_half.setImageResource(
                    this.type == 0 ? R.drawable.ic_star_green_dark : R.drawable.ic_star_orange
            );
            this.addView(img_half);

            for (int i = 0; i < 4 - numberStarGreen; i++) {
                ImageView img = new ImageView(context);
                img.setLayoutParams(new LayoutParams(this.defaultSize, this.defaultSize));
                img.setImageResource(R.drawable.ic_star_gray);
                this.addView(img);
            }
        }

    }

}
