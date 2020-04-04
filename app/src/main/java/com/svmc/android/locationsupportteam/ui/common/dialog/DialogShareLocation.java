package com.svmc.android.locationsupportteam.ui.common.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.svmc.android.locationsupportteam.BuildConfig;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.utils.Constans;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Create by TungTS on 6/8/2019
 */

public class DialogShareLocation extends DialogFragment implements View.OnClickListener {

    private ImageView imgCapture;
    private RadioButton rdCurrentLocation;
    private RadioButton rdSelectedPoint;
    private EditText edtMessage;

    public static DialogShareLocation newInstance(DialogShareLocationCallback onDialogShareLocationCallback) {
        DialogShareLocation dialogShareLocation = new DialogShareLocation();
        dialogShareLocation.onDialogShareLocationCallback = onDialogShareLocationCallback;
        return dialogShareLocation;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Chia sẻ vị trí");
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.dialog_share_location, null);

        imgCapture = dialog.findViewById(R.id.img_camera);
        rdCurrentLocation = dialog.findViewById(R.id.rd_current_location);
        rdSelectedPoint = dialog.findViewById(R.id.rd_point_selected);
        edtMessage = dialog.findViewById(R.id.edt_message);

        builder.setView(dialog)
                .setPositiveButton("Chia sẻ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sharePoint();
                    }
                });

        builder.setNegativeButton("Trở về", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        addEvents();
        return  builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void addEvents() {
        imgCapture.setOnClickListener(this);
        rdCurrentLocation.setOnClickListener(this);
        rdSelectedPoint.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_camera:
                showDialogChooseAddImage();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constans.RequestCode.RC_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    imgCapture.setImageURI(imageUri);
                    fileImage = new File(imageUri.toString());
                }
                break;
            case Constans.RequestCode.RC_IMAGE_CAPTURE:
                Uri imageUri = Uri.parse("file:" + fileImage.getAbsolutePath());
                if (imageUri != null) {
                    imgCapture.setImageURI(imageUri);
                }
                break;
        }
    }

    private void sharePoint() {
        boolean isCurrentLocation = rdCurrentLocation.isChecked();
        if (onDialogShareLocationCallback != null) {
            onDialogShareLocationCallback.onShare(
                    fileImage.getAbsolutePath(),
                    isCurrentLocation,
                    edtMessage.getText().toString());
        }
    }

    private DialogShareLocationCallback onDialogShareLocationCallback;

    public interface DialogShareLocationCallback {
        void onShare(String fileImage, boolean isCurrentLocation, String message);
    }

}
