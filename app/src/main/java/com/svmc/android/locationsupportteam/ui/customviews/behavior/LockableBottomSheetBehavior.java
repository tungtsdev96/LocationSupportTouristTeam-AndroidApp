package com.svmc.android.locationsupportteam.ui.customviews.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TUNGTS on 4/11/2019
 */

public class LockableBottomSheetBehavior<V extends View> extends BottomSheetBehavior<V> {

    private boolean mLocked = false;

    public LockableBottomSheetBehavior() {}

    public LockableBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLocked(boolean locked) {
        mLocked = locked;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        boolean handled = false;

        if (!mLocked) {
            handled = super.onInterceptTouchEvent(parent, child, event);
        }

        return handled;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        boolean handled = false;

        if (!mLocked) {
            handled = super.onTouchEvent(parent, child, event);
        }

        return handled;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V child, View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY);
    }

}
