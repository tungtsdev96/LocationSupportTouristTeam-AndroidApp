package com.svmc.android.locationsupportteam.ui.intro;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.home.MainActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.ui.common.activity.InternetErrActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;

/**
 * Created by TungTS on 4/23/2019
 */

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        onViewCreated();
    }

    public void onViewCreated() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (CommonUtils.checkInternetConnection(this) && user != null) {
            modelSplash = new ModelSplash();
            getRoomLocationByUser();
        } else {
            changeActivity();
        }
    }

    private void changeActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }, 1000);
    }

    private ModelSplash modelSplash;
    private void getRoomLocationByUser() {
        modelSplash.getRoomLocation(new FinishedListener<BaseResultResponse<String>>() {
            @Override
            public void onFinished(BaseResultResponse<String> result) {
                AppPreferencens.getInstance().setRoomId(result.getResult());
                changeActivity();
            }

            @Override
            public void onFailure(Throwable t) {
                Intent i = new Intent(SplashActivity.this, InternetErrActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

}
