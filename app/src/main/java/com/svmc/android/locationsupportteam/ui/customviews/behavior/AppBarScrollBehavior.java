package com.svmc.android.locationsupportteam.ui.customviews.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by TUNGTS on 4/9/2019
 */

public abstract class AppBarScrollBehavior<T extends View> extends CoordinatorLayout.Behavior<T> {

    public AppBarScrollBehavior() {
    }

    public AppBarScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull T child, @NonNull View dependency) {
        float offset = 1.0f;
        animateBaseOnAppbarMovement(offset, parent, child, dependency);
        return true;
    }

    abstract void animateBaseOnAppbarMovement(float offset, CoordinatorLayout parent, T child, View dependency);

}
