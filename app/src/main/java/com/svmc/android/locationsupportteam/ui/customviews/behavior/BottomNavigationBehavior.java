package com.svmc.android.locationsupportteam.ui.customviews.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by TUNGTS on 4/9/2019
 */

public class BottomNavigationBehavior extends CoordinatorLayout.Behavior<BottomNavigationView>{

    public BottomNavigationBehavior() {
    }

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
        boolean dependsOn = dependency instanceof FrameLayout;
//        Log.d("tungts_de", dependsOn +"");
        return dependsOn;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        Log.d("tungts_", (axes == ViewCompat.SCROLL_AXIS_VERTICAL) +"");
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
//        Log.d("tungts", dy+"");
        if (dy < 0) {
            showBottomNavigationView(child);
        } else {
            hideBottomNavigationView(child);
        }
    }

    private void hideBottomNavigationView(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.clearAnimation();
        bottomNavigationView.animate().translationY(bottomNavigationView.getHeight());
    }

    private void showBottomNavigationView(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.clearAnimation();
        bottomNavigationView.animate().translationY(0);
    }

}
