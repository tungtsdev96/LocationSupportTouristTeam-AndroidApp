package com.svmc.android.locationsupportteam.ui.setting;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.manager.AlarmManagerSync;
import com.svmc.android.locationsupportteam.utils.TimeUtil;

/**
 * Created by TUNGTS on 5/23/2019
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private SwitchCompat switchVibrate;
    
    private LinearLayout llTimeSynchronize;
    private SwitchCompat switchSynchronize;
    private TextView tvTimeSynchronize;

    private SwitchCompat switchNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        innitView();
        addEvents();
    }

    private void innitView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cài đặt");

        switchVibrate = findViewById(R.id.switch_vibrate);
        boolean isVibrate = AppPreferencens.getInstance().checkVibrate();
        switchVibrate.setChecked(isVibrate);

        llTimeSynchronize = findViewById(R.id.ll_time_synchronize);
        switchSynchronize = findViewById(R.id.switch_synchronize);
        tvTimeSynchronize = findViewById(R.id.tv_time);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            switchSynchronize.setChecked(false);
            switchSynchronize.setEnabled(false);
        } else {
            boolean isSync = AppPreferencens.getInstance().checkSynchronize();
            switchSynchronize.setChecked(isSync);
            String timeSync = AppPreferencens.getInstance().getTimeSync();
            tvTimeSynchronize.setText(timeSync);
        }

        switchNotification = findViewById(R.id.switch_notification);
        boolean isReceive = AppPreferencens.getInstance().checkReceiveNotification();
        switchNotification.setChecked(isReceive);
    }

    private void addEvents() {
        switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppPreferencens.getInstance().setReceiveNotification();
            }
        });

        switchVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppPreferencens.getInstance().setVibrate();
            }
        });

        llTimeSynchronize.setOnClickListener(this);

        switchSynchronize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    AlarmManagerSync.getInstance().cancel();
                } else {
                    String[] time = AppPreferencens.getInstance().getTimeSync().split(":");
                    int hour = Integer.parseInt(time[0]);
                    int minute = Integer.parseInt(time[1]);
                    AlarmManagerSync.getInstance().innitTime(hour, minute);
                }

                AppPreferencens.getInstance().setSynchronize();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_time_synchronize:
                openDialogSetTime();
                break;
        }
    }

    /***
     * time to sync
     */
    private void openDialogSetTime() {
        boolean isSync =  switchSynchronize.isChecked();
        if (!isSync) return;

        String[] time = AppPreferencens.getInstance().getTimeSync().split(":");
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                setTimeSync(hourOfDay, minute);
            }
        }, hour, minute, true);
        dialog.show();
    }

    private void setTimeSync(int hourOfDay, int minute) {
        AppPreferencens.getInstance().setTimeSync(TimeUtil.getTime(hourOfDay, minute));
        tvTimeSynchronize.setText(TimeUtil.getTime(hourOfDay, minute));

        AlarmManagerSync.getInstance().innitTime(hourOfDay, minute);
    }
}
