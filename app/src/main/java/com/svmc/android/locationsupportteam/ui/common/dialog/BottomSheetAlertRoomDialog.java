package com.svmc.android.locationsupportteam.ui.common.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.svmc.android.locationsupportteam.BuildConfig;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.adapter.ItemTextAdapter;
import com.svmc.android.locationsupportteam.entity.roomloation.RoomLocationNotification;
import com.svmc.android.locationsupportteam.listeners.RecycleViewClickListener;
import com.svmc.android.locationsupportteam.listeners.ShakeEventListener;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.VibratorUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by TungTS on 5/12/2019
 */

public class BottomSheetAlertRoomDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    private ImageView imgClose;
    private TextView tvCountTime;
    private EditText edtInputMessage;
    private ImageView imgSend;

    private RecyclerView rcvMessage;
    private ItemTextAdapter itemTextAdapter;
    private List<String> messages;

    private TextView tvImage;
    private ImageView imgCapture;
    private ImageView imgDeleteImg;

    private Handler mHandler = new Handler();
    int defaultTime = 10;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            defaultTime--;
            tvCountTime.setText(String.valueOf(defaultTime));

            if (defaultTime == 0) {
                createMessageAlert();
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    private ShakeEventListener listener;

    public BottomSheetAlertRoomDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // the content
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        // creating the fullscreen dialog
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(root);
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet_message_alert_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener = new ShakeEventListener();
        listener.innit(getActivity().getApplicationContext());
        listener.onResume();

        listener.setOnShakeCallBack(new ShakeEventListener.ShakeCallBack() {
            @Override
            public void onShake() {
                createMessageAlert();
                listener.onPause();
            }
        });

        setCancelable(false);
        innitView(view);
        addEvents();
    }

    private void innitView(View view) {
        imgClose = view.findViewById(R.id.img_close);
        tvCountTime = view.findViewById(R.id.tv_count_time);
        edtInputMessage = view.findViewById(R.id.edt_input_message);
        imgSend = view.findViewById(R.id.img_send);

        tvImage = view.findViewById(R.id.tv_image);
        imgCapture = view.findViewById(R.id.img);
        imgDeleteImg = view.findViewById(R.id.img_delete);

        innitRcv(view);
    }

    private void innitRcv(View view) {
        rcvMessage = view.findViewById(R.id.rcv_message_off);
        itemTextAdapter = new ItemTextAdapter();
        messages = new ArrayList<>();
        messages.add(getString(R.string.dangerous));
        messages.add("Bị hỏng xe !!");
        messages.add("Bị lạc đường !!");
        messages.add("Bị thủng xăm !!");
        messages.add("Xe bị hết xăng !!");
        itemTextAdapter.setItems(messages);
        rcvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvMessage.setAdapter(itemTextAdapter);
    }

    private void addEvents() {
        imgClose.setOnClickListener(this);
        imgSend.setOnClickListener(this);

        tvImage.setOnClickListener(this);
        imgDeleteImg.setOnClickListener(this);

        itemTextAdapter.setOnRecycleViewClickListener(new RecycleViewClickListener() {
            @Override
            public void onClick(int position) {
                priority = position;
                edtInputMessage.setText(messages.get(position));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constans.RequestCode.RC_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    imgCapture.setImageURI(imageUri);
                    imgDeleteImg.setVisibility(View.VISIBLE);
                    imgCapture.setVisibility(View.VISIBLE);
                    fileImage = new File(imageUri.toString());
                }
                break;
            case Constans.RequestCode.RC_IMAGE_CAPTURE:
                Uri imageUri = Uri.parse("file:" + fileImage.getAbsolutePath());
                if (imageUri != null) {
                    imgCapture.setImageURI(imageUri);
                    imgDeleteImg.setVisibility(View.VISIBLE);
                    imgCapture.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                Toast.makeText(getContext(), "Đã hủy gửi cảnh báo đến nhóm", Toast.LENGTH_SHORT).show();
                this.dismiss();
                break;
            case R.id.img_send:
                createMessageAlert();
                break;
            case R.id.tv_image:
                showDialogChooseAddImage();
                break;
            case R.id.img_delete:
                imgDeleteImg.setVisibility(View.GONE);
                imgCapture.setImageDrawable(null);
                imgCapture.setVisibility(View.GONE);
                break;
        }
    }

    private void showDialogChooseAddImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.choose_image, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        // The 'which' argument contains the index position
                        // of the selected item
                        if (position == 0) {
                            takeCameraImage();
                        } else {
                            openPickerImageFromGarelly();
                        }
                    }
                });
        builder.create().show();
    }

    private void openPickerImageFromGarelly() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, Constans.RequestCode.RC_GALLERY_IMAGE);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    File fileImage;
    private void takeCameraImage() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        fileImage = getOutputMediaFile();
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                BuildConfig.APPLICATION_ID + ".provider", fileImage);
                        cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, Constans.RequestCode.RC_IMAGE_CAPTURE);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "VTrack");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    }

    private void createMessageAlert() {
        if (onBottomSheetCallBack != null) {
            String message = edtInputMessage.getText().toString();
            if (priority == 0 || message == null) {
                onBottomSheetCallBack.onCreatedMessage(
                        getContext().getResources().getString(R.string.dangerous),
                        RoomLocationNotification.STATUS_SOS,
                        fileImage != null ? fileImage.getAbsolutePath() : null
                );
            } else {
                onBottomSheetCallBack.onCreatedMessage(
                        message,
                        RoomLocationNotification.STATUS_ALERT,
                        fileImage != null ? fileImage.getAbsolutePath() : null
                );
            }
            new VibratorUtils(getContext()).turnOnOneShot();
            this.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable, 1000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
        listener.onPause();
    }

    private int priority = 0;
    private BottomSheetCallBack onBottomSheetCallBack;

    public void setOnBottomSheetCallBack(BottomSheetCallBack onBottomSheetCallBack) {
        this.onBottomSheetCallBack = onBottomSheetCallBack;
    }

    public interface BottomSheetCallBack {

        void onCreatedMessage(String message, String alertType, String image);

    }
}
