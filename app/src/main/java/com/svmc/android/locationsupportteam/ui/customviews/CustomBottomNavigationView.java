package com.svmc.android.locationsupportteam.ui.customviews;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;


/**
 * Created by TUNGTS on 4/12/2019
 */

public class CustomBottomNavigationView extends BottomNavigationView {

    private Context context;

    private VelocityTracker mVelocityTracker = null;

    private float downX;
    private float downY;

    public CustomBottomNavigationView(Context context) {
        super(context);
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int index = ev.getActionIndex();
        int action = ev.getActionMasked();
        int pointerId = ev.getPointerId(index);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();

                Log.d("bottom_touch", "onInterceptTouchEvent ACTION_DOWN " + ev.getX() + " " + ev.getY());
                if (mVelocityTracker == null) {
                    // Retrieve a new VelocityTracker object to watch the
                    // velocity of a motion.
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    // Reset the velocity tracker back to its initial state.
                    mVelocityTracker.clear();
                }
                // Add a user's movement to the tracker.
                mVelocityTracker.addMovement(ev);
                return false;
            case MotionEvent.ACTION_CANCEL:
                Log.d("bottom_touch", "onInterceptTouchEvent ACTION_CANCEL " + ev.getX() + " " + ev.getY());
            case MotionEvent.ACTION_UP:
                Log.d("bottom_touch", "onInterceptTouchEvent ACTION_UP " + ev.getX() + " " + ev.getY());
            case MotionEvent.ACTION_BUTTON_RELEASE:
                Log.d("bottom_touch", "onInterceptTouchEvent ACTION_BUTTON_RELEASE " + ev.getX() + " " + ev.getY());
            case MotionEvent.ACTION_HOVER_EXIT:
                Log.d("bottom_touch", "onInterceptTouchEvent ACTION_HOVER_EXIT " + ev.getX() + " " + ev.getY());
                mVelocityTracker.recycle();
                return super.onInterceptTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                Log.d("bottom_touch", "onInterceptTouchEvent ACTION_MOVE " + ev.getX() + " " + ev.getY());
                mVelocityTracker.addMovement(ev);
                // When you want to determine the velocity, call
                // computeCurrentVelocity(). Then call getXVelocity()
                // and getYVelocity() to retrieve the velocity for each pointer ID.
                mVelocityTracker.computeCurrentVelocity(1000);
                // Log velocity of pixels per second
                // Best practice to use VelocityTrackerCompat where possible.

                float velocityX = mVelocityTracker.getXVelocity(pointerId);
                float velocityY = mVelocityTracker.getYVelocity(pointerId);
                float diffX = ev.getX() - downX;
                float diffY = ev.getY() - downY;

                Log.d("bottom_touch", "X velocity: " + velocityX + " X diff: " + diffX);
                Log.d("bottom_touch", "Y velocity: " + velocityY + " Y diff: " + diffY);

                if (Math.abs(velocityX) > 100 && Math.abs(velocityY) > 100) {
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (diffX > 0) {
                            if (onMovementCallBack != null) {
                                onMovementCallBack.onMoveRight(this, velocityX, velocityY);
                            }
                        } else {
                            if (onMovementCallBack != null) {
                                onMovementCallBack.onMoveLeft(this, velocityX, velocityY);
                            }
                        }
                    } else {
                        if (diffY > 0) {
                            if (onMovementCallBack != null) {
                                onMovementCallBack.onMoveBottom(this, velocityX, velocityY);
                            }
                        } else {
                            if (onMovementCallBack != null) {
                                onMovementCallBack.onMoveTop(this, velocityX, velocityY);
                            }
                        }
                    }
                    return true;
                }
                return super.onInterceptTouchEvent(ev);
            default:
                Log.d("bottom_touch", "onInterceptTouchEvent DEFAULT");
                return super.onInterceptTouchEvent(ev);
        }
    }


    private MovementCallBack onMovementCallBack;

    public MovementCallBack getOnMovementCallBack() {
        return onMovementCallBack;
    }

    public void setOnMovementCallBack(MovementCallBack onMovementCallBack) {
        this.onMovementCallBack = onMovementCallBack;
    }

    public interface MovementCallBack {

        void onMoveLeft(View view, float velocityX, float velocityY);

        void onMoveRight(View view, float velocityX, float velocityY);

        void onMoveTop(View view, float velocityX, float velocityY);

        void onMoveBottom(View view, float velocityX, float velocityY);

    }

}
