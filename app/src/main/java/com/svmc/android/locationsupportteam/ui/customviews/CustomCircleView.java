package com.svmc.android.locationsupportteam.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.svmc.android.locationsupportteam.R;

public class CustomCircleView extends FrameLayout {

    private int backGroundColor;

    private int widthBorder;

    private int colorBorder;

    public CustomCircleView(Context context) {
        super(context);
        innitView(context);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomCircleView);
        this.backGroundColor = typedArray.getColor(R.styleable.CustomCircleView_backgroundColor, Color.WHITE);
        this.widthBorder = typedArray.getDimensionPixelOffset(R.styleable.CustomCircleView_widthBorder, 0);
        this.colorBorder = typedArray.getColor(R.styleable.CustomCircleView_borderColor, Color.WHITE);
        typedArray.recycle();
        innitView(context);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        innitView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        innitView(context);
    }

    private void innitView(Context context) {
        this.setBackgroundDrawable(createCircleDrawable());
    }

    public Drawable createCircleDrawable() {
        OvalShape shape = new OvalShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(backGroundColor);
        return shapeDrawable;
    }

}
