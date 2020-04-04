package com.svmc.android.locationsupportteam.ui.customviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by TUNGTS on 3/29/2019
 */

public class SquareRelativeLayout extends LinearLayout {

    public SquareRelativeLayout(@NonNull Context context) {
        super(context);
    }

    public SquareRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
