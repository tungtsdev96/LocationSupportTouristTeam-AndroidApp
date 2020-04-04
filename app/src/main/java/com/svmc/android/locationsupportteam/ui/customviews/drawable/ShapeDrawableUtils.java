package com.svmc.android.locationsupportteam.ui.customviews.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorRes;

public class ShapeDrawableUtils {

    public static StateListDrawable getStateListDrawable(float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight, int solidColor, int colorPressed) {

        StateListDrawable drawable = new StateListDrawable();
        int[] state = new int[]{android.R.attr.state_pressed, android.R.attr.state_focused};
        drawable.addState(state, createRectDrawable(radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight, colorPressed));

        drawable.addState(new int[]{}, createRectDrawable(radiusTopLeft, radiusTopRight, radiusBottomLeft, radiusBottomRight, solidColor));

        return drawable;
    }

    public static Drawable createRectDrawable(float radiusTopLeft, float radiusTopRight, float radiusBottomLeft, float radiusBottomRight, int pressedColor) {
        RoundRectShape shape = new RoundRectShape(
                new float[]{
                        radiusTopLeft,
                        radiusTopLeft,
                        radiusTopRight,
                        radiusTopRight,
                        radiusBottomLeft,
                        radiusBottomLeft,
                        radiusBottomRight,
                        radiusBottomRight
                },
                null,
                null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(pressedColor);
        return shapeDrawable;
    }

    public static Drawable createCircleDrawable(int color) {
        OvalShape shape = new OvalShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }

}
