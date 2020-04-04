package com.svmc.android.locationsupportteam.ui.notification.room.alert;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Create by TungTS on 6/8/2019
 */

public class BottomSheetAlertNotify extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView btnSolve;
    private ImageView btnCall;
    private EditText edtPhone;

    private RadioButton rd113;
    private RadioButton rd114;
    private RadioButton rd115;

    private BottomSheetCallBack onBottomSheetCallBack;

    public static BottomSheetAlertNotify newInstance(BottomSheetCallBack onBottomSheetCallBack) {
        BottomSheetAlertNotify fragment = new BottomSheetAlertNotify();
        fragment.onBottomSheetCallBack = onBottomSheetCallBack;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_notification_alert, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        innitView(view);
        addEvents();
    }

    private void innitView(View view) {
        btnSolve = view.findViewById(R.id.btn_solve);
        btnCall = view.findViewById(R.id.btn_call);
        edtPhone = view.findViewById(R.id.edt_phone);
        rd113 = view.findViewById(R.id.rd_113);
        rd114 = view.findViewById(R.id.rd_114);
        rd115 = view.findViewById(R.id.rd_115);
    }

    private void addEvents() {
        btnSolve.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        rd113.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtPhone.setText(
                        isChecked ? "113" : edtPhone.getText().toString()
                );
            }
        });

        rd114.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtPhone.setText(
                        isChecked ? "114" : edtPhone.getText().toString()
                );
            }
        });

        rd115.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                edtPhone.setText(
                        isChecked ? "115" : edtPhone.getText().toString()
                );
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_solve:
                if (onBottomSheetCallBack != null) {
                    onBottomSheetCallBack.onClickSolve();
                    dismiss();
                }
                break;
            case R.id.btn_call:
                if (edtPhone.getText() != null) {
                    callTo();
                } else {
                    Toast.makeText(getContext(), "Nhập sđt trước", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void callTo() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(this, Constans.RequestCode.RC_CALL_PHONE, Manifest.permission.CALL_PHONE, true);
        } else {
            String dial = "tel:" + edtPhone.getText().toString();
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    public void requestPermission(Fragment fragment, int requestId,
                                  String permission, boolean finishActivity) {
        fragment.requestPermissions(new String[]{permission}, requestId);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (isPermissionGranted(permissions, grantResults, Manifest.permission.CALL_PHONE)) {
            callTo();
        }
    }

    public boolean isPermissionGranted(String[] grantPermissions, int[] grantResults,
                                       String permission) {
        for (int i = 0; i < grantPermissions.length; i++) {
            if (permission.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    public interface BottomSheetCallBack {

        void onClickSolve();

    }


}
