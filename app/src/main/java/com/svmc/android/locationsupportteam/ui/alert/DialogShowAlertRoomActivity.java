package com.svmc.android.locationsupportteam.ui.alert;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.roomloation.ParamsPostNotification;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocation;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.ui.roomlocation.RoomLocationActivity;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;
import com.svmc.android.locationsupportteam.utils.VibratorUtils;

/**
 * Created by TungTS on 5/13/2019
 */

public class DialogShowAlertRoomActivity extends Activity implements View.OnClickListener, MVPAlertRoom.IViewAlertRoom {

    public static Intent innitIntent(Context context, String jsonNotification) {
        Intent i = new Intent(context, DialogShowAlertRoomActivity.class);
        i.putExtra(Constans.KeyBundle.ROOM_ALERT_DATA, jsonNotification);
        return i;
    }

    private ParamsPostNotification postNotification;

    private TextView tvContentMessage;
    private TextView tvCall;
    private TextView tvShowMap;
    private ImageView imgAttach;

    private RadioButton rd113;
    private RadioButton rd114;
    private RadioButton rd115;
    private EditText edtPhone;
    private TextView btnCall;

    private VibratorUtils vibratorUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_show_action_room);
        String jsonData = getIntent().getStringExtra(Constans.KeyBundle.ROOM_ALERT_DATA);
        if (jsonData == null) finish();

        vibratorUtils = new VibratorUtils(getApplicationContext());
        postNotification = new Gson().fromJson(jsonData, ParamsPostNotification.class);

        vibratorUtils.turnOnVibrator(
                AppPreferencens.getInstance().checkVibrate()
        );

        innitView();
        addEvents();
        innitData();
    }

    private void innitView() {
        tvContentMessage = findViewById(R.id.tv_content_message);
        tvCall = findViewById(R.id.tv_call);
        tvShowMap = findViewById(R.id.tv_show_map);
        imgAttach = findViewById(R.id.img_attach);

        rd113 = findViewById(R.id.rd_113);
        rd114 = findViewById(R.id.rd_114);
        rd115 = findViewById(R.id.rd_115);
        edtPhone = findViewById(R.id.edt_phone);
        btnCall = findViewById(R.id.btn_call);
    }

    private void addEvents() {
        tvCall.setOnClickListener(this);
        tvShowMap.setOnClickListener(this);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callToEdt();
            }
        });

        rd113.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtPhone.setText("113");
            }
        });

        rd114.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtPhone.setText("114");
            }
        });

        rd115.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtPhone.setText("115");
            }
        });
    }

    private void callToEdt() {
        if (edtPhone.getText() == null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constans.RequestCode.RC_CALL_PHONE);
            } else {
                if (postNotification.getSender().getPhone() != null) {
                    String dial = "tel:" + postNotification.getSender().getPhone();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(this, postNotification.getSender().getNameUser() + " chưa lưu số điện thoại trên hệ thống!!!", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constans.RequestCode.RC_CALL_PHONE);
            } else {
                if (postNotification.getSender().getPhone() != null) {
                    String dial = "tel:" + edtPhone.getText().toString();
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                } else {
                    Toast.makeText(this, postNotification.getSender().getNameUser() + " chưa lưu số điện thoại trên hệ thống!!!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void innitData(){
//        if (postNotification.getUrlImage() != null) {
//            imgAttach.setVisibility(View.VISIBLE);
//
//            GlideUtils
//                    .loadImageWrapContent(
//                            this,
//                            imgAttach,
//                            ApiConfig.ConfigUrl.URL_NODEJS + postNotification.getUrlImage(),
//                            CommonUtils.dpToPx(180));
//        }


        tvContentMessage.setText(
//                Html.fromHtml(
//                        "<html><b>" + postNotification.getSender().getNameUser() + "</b> " + postNotification.getMessage() + "</html>"
//                )

                Html.fromHtml(
                        "<html><b> Hải Linh</b> đang gặp nguy hiểm !!!</html>"
                )
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_call:
                callTo();
                break;
            case R.id.tv_show_map:
                showUIMap();
                break;
        }
    }

    @Override
    public void startVibrate(boolean isRepeat) {
        vibratorUtils.turnOnVibrator(isRepeat);
    }

    @Override
    public void stopVibrate() {
        vibratorUtils.stopVibrator();
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void showUIMap() {
        Intent i = RoomLocationActivity.innitIntent(
                this,
                RoomLocationActivity.DETAIL_ROOM,
                new RoomLocation(postNotification.getRoom().getRoomId(), postNotification.getRoom().getNameRoom()),
                postNotification.getType(),
                postNotification.getPoint(),
                postNotification.getSender().getUserId(),
                postNotification.getUrlImage());
        startActivity(i);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }


    @Override
    public void callTo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constans.RequestCode.RC_CALL_PHONE);
        } else {
            if (postNotification.getSender().getPhone() != null) {
                String dial = "tel:" + postNotification.getSender().getPhone();
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            } else {
                Toast.makeText(this, postNotification.getSender().getNameUser() + " chưa lưu số điện thoại trên hệ thống!!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isPermissionGranted(permissions, grantResults, Manifest.permission.CALL_PHONE)) {
            callTo();
        }
    }

    private boolean isPermissionGranted(String[] permissions, int[] grantResults, String callPhone) {
        for (int i = 0; i < permissions.length; i++) {
            if (Manifest.permission.CALL_PHONE.equals(permissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        vibratorUtils.stopVibrator();
    }
}
