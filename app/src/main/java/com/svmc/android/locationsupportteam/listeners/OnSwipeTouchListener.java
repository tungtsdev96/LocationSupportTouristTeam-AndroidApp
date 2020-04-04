package com.svmc.android.locationsupportteam.listeners;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by TUNGTS on 4/12/2019
 */

public class OnSwipeTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    private SwipeTouchCallBack onSwipeTouchCallBack;

    private View view;

    public OnSwipeTouchListener (Context ctx, SwipeTouchCallBack onSwipeTouchCallBack){
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        this.onSwipeTouchCallBack = onSwipeTouchCallBack;
        Log.d("swipe", "constructor");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.view = v;
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            Log.d("swipe", "onDown");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                Log.d("swipe", "onFing " + diffX + " " + diffY);
                Log.d("swipe_velocity", "onFing " + velocityX + " " + velocityY);

                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            if (onSwipeTouchCallBack != null) {
                                onSwipeTouchCallBack.onSwipeRight(view);
                            }
                        } else {
                            if (onSwipeTouchCallBack != null) {
                                onSwipeTouchCallBack.onSwipeLeft(view);
                            }
                        }
                        result = true;
                    }
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        if (onSwipeTouchCallBack != null) {
                            onSwipeTouchCallBack.onSwipeBottom(view);
                        }
                    } else {
                        if (onSwipeTouchCallBack != null) {
                            onSwipeTouchCallBack.onSwipeTop(view);
                        }
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            return result;
        }

    }

    public interface SwipeTouchCallBack {

        void onSwipeLeft(View V);

        void onSwipeTop(View V);

        void onSwipeRight(View V);

        void onSwipeBottom(View V);
    }
}
