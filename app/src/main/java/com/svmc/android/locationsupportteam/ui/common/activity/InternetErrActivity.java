package com.svmc.android.locationsupportteam.ui.common.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.ui.home.MainActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;

/**
 * Created by TUNGTS on 5/23/2019
 */

public class InternetErrActivity extends BaseActivity {

    private TextView btnTryAgain;

    @Override
    public int getContentView() {
        return R.layout.activity_internet_err;
    }

    @Override
    public void onViewCreated(View view) {
        btnTryAgain = findViewById(R.id.btn_try_again);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryAgain();
            }
        });
    }

    private void tryAgain() {
        if (CommonUtils.checkInternetConnection(this)) {
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
