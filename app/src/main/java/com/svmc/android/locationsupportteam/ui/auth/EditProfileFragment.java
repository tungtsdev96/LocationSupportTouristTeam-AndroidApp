package com.svmc.android.locationsupportteam.ui.auth;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.sweetdialog.SweetAlertDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.event.EventPostUpdateInfor;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.network.ApiConfig;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.ui.base.BaseFragment;
import com.svmc.android.locationsupportteam.ui.customviews.CircleImageView;
import com.svmc.android.locationsupportteam.ui.customviews.CustomInputEditText;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.CommonUtils;
import com.svmc.android.locationsupportteam.utils.Constans;
import com.svmc.android.locationsupportteam.utils.GlideUtils;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static android.app.Activity.RESULT_OK;

/**
 * Create by TUNGTS on 4/18/2019
 */

public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    RequestOptions options = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_place_hoder)
            .error(R.drawable.ic_place_hoder)
            .skipMemoryCache(true);

    private static final int IMAGE_COMPRESSION = 80; // 0-> 100
    private FirebaseUser user;
    private boolean isUpdate;

    private ImageView imgClose;
    private TextView btnSave;
    private Toolbar toolbar;

    private boolean canUpload;
    private CircleImageView imgProfile;
    private Uri uriImage;
    private RelativeLayout llAvatar;
    private TextView btnUpload;

    private CustomInputEditText edtDisplayName;
    private CustomInputEditText edtUsername;
    private CustomInputEditText edtPhone;
    private CustomInputEditText edtDescription;


    public static EditProfileFragment newInstance(boolean isUpdate) {
        EditProfileFragment editProfileFragment = new EditProfileFragment();
        editProfileFragment.setTAG(Constans.TagFragment.EDIT_PROFILE_FRAGMENT);
        editProfileFragment.isUpdate = isUpdate;
        return editProfileFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_edit_profile;
    }

    @Override
    protected void onFragmentCreateView(View view) {

    }

    @Override
    protected void onFragmentCreated(View view) {

    }

    @Override
    protected void innitView(View view) {
        imgClose = view.findViewById(R.id.img_close);
        btnSave = view.findViewById(R.id.tv_save);

        llAvatar = view.findViewById(R.id.ll_avatar);
        imgProfile = view.findViewById(R.id.img_profile);
        btnUpload = view.findViewById(R.id.tv_upload);

        edtDisplayName = view.findViewById(R.id.input_display_name);
        edtUsername = view.findViewById(R.id.input_user_name);
        edtPhone = view.findViewById(R.id.input_phone);
        edtDescription = view.findViewById(R.id.input_description);

        innitData();
    }

    private void innitData() {
        user = FirebaseUtils.getFirebaseAuth().getCurrentUser();

        if (isUpdate) {
            showSweetProgressDialog("Đang tải");
            ApiFactory.getApiUser().getInforUser(user.getUid()).enqueue(new BaseCallBack<User>() {
                @Override
                public void onSuccess(User result) {
                    bindToUI(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    showSweetAlert("Thông báo", "Đã có lỗi xảy ra", null, "OK", SweetAlertDialog.ERROR_TYPE, new SweetAlertDialogListener() {
                        @Override
                        public void onConfirmClicked(SweetAlertDialog dialog) {
                            dialog.dismiss();
                            ((SignInActivity) getActivity()).finish();
                        }

                        @Override
                        public void onCancelClicked(SweetAlertDialog dialog) {

                        }
                    });
                }
            });
            return;
        }

        String[] part = user.getEmail().split("@");
        String tmp = part[0];

//        uriImage = user.getPhotoUrl();
//        if (uriImage != null)

        edtDisplayName.setText(tmp);
        edtUsername.setText(tmp);
        edtPhone.setText("");
    }

    private void bindToUI(User user) {
        hideSweetProgressDialog();
        if (user.getUrlImage() != null) {
            Glide.with(this)
                    .load(ApiConfig.ConfigUrl.URL_NODEJS + user.getUrlImage())
                    .apply(options)
                    .into(imgProfile);
        }

        edtDisplayName.setText(user.getDisplayName() != null ? user.getDisplayName() : "");
        edtUsername.setText(user.getUsername() != null ? user.getUsername() : "");
        edtPhone.setText(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
    }

    @Override
    protected void addEvents() {
        imgClose.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        llAvatar.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_close:
                if (!isUpdate) {
                    saveToDB();
                    break;
                }
                getActivity().finish();
                break;
            case R.id.tv_save:
                saveToDB();
                break;
            case R.id.ll_avatar:
                openPickerImageFromGarelly();
                break;
            case R.id.tv_upload:
//                chooseImageToUpload();
                break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constans.RequestCode.RC_GALLERY_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri imageUri = data.getData();
                    Log.d(getTAG(), String.valueOf(imageUri));
//                    uriImage = imageUri;
                    cropImage(imageUri);
                } else {
                    Log.d(getTAG(), "Cancel Pick image");
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    handleUCropResult(data);
                } else {
//                    setResultCancelled();
                    Log.d(getTAG(), "Crop Canceled");
                }
                break;
            case UCrop.RESULT_ERROR:
                final Throwable cropError = UCrop.getError(data);
                Log.d(getTAG(), "Crop error: " + cropError);
                break;
        }
    }

    private void handleUCropResult(Intent data) {
        if (data == null) {
            Log.d(getTAG(), "Data null");
            return;
        }

        final Uri resultUri = UCrop.getOutput(data);
        Glide.with(this)
                .load(resultUri.toString())
                .apply(options)
                .into(imgProfile);

        this.uriImage = resultUri;
        this.canUpload = true;
        Log.d(getTAG(), "Done Crop " + resultUri);
    }

    /***
     * Crop image by Ucrop
     * @param sourceUri
     */
    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getActivity().getCacheDir(), queryName(getActivity().getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(IMAGE_COMPRESSION);
        options.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        options.withAspectRatio(1, 1);

        options.withMaxResultSize(320, 320);

        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(getActivity());
    }

    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }
    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * save infor user updated
     */
    private void saveToDB() {
        if (validate()) {
            updateInforUser();
        } else {
            showSweetAlert("Thông báo", "Bạn phải điển thông tin trước đã", null, "OK", SweetAlertDialog.ERROR_TYPE, new SweetAlertDialogListener() {
                @Override
                public void onConfirmClicked(SweetAlertDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void onCancelClicked(SweetAlertDialog dialog) {

                }
            });
        }
    }

    private void updateInforUser() {
        User user = new User();
        user.setCreatedTime(this.user.getMetadata().getCreationTimestamp());
        user.setEmail(this.user.getEmail());
        user.setUserId(this.user.getUid());
        user.setStatus(true);
        user.setDescription(
                edtDescription.getText() != null ? edtDescription.getText() : ""
        );
        user.setDisplayName(
                edtDisplayName.getText()
        );
        user.setUsername(
                edtUsername.getText()
        );
//        user.setUrlImage(
//                uriImage != null ? String.valueOf(uriImage) : ""
//        );
        user.setPhoneNumber(
                edtPhone.getText() != null ? edtPhone.getText() : ""
        );

        // save current user
        AppPreferencens.getInstance().setCurrentUser(user);

        // update infor
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(user.getDisplayName())
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(getTAG(), "UserCache profile updated. " + firebaseUser.getDisplayName() + " " + firebaseUser.getPhotoUrl());
                        }
                    }
                });

        MultipartBody.Part part = null;
        if (uriImage != null) {
            File fileImage = new File(uriImage.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), fileImage);
            part = MultipartBody.Part.createFormData("uploaded_file", fileImage.getName(), requestBody);
        }

        ApiFactory.getApiUser().addNewUser(part, user).enqueue(new BaseCallBack<ResponseBody>() {
            @Override
            public void onSuccess(final ResponseBody result) {

                try {
                    JSONObject object = new JSONObject(result.string());
                    User u = new Gson().fromJson(object.optString("data"), User.class);
                    u.setUserId(object.optString("id"));
                    AppPreferencens.getInstance().setCurrentUser(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                showSweetAlert("Thông báo", "Cập nhật thông tin thành công", null, "OK", SweetAlertDialog.SUCCESS_TYPE, new SweetAlertDialogListener() {
                    @Override
                    public void onConfirmClicked(SweetAlertDialog dialog) {
                        dialog.dismiss();
                        ((SignInActivity) getActivity()).finish();
                    }

                    @Override
                    public void onCancelClicked(SweetAlertDialog dialog) {

                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                showSweetAlert("Thông báo", "Đã có lỗi xảy ra vui lòng thử lại", null, "OK", SweetAlertDialog.NORMAL_TYPE, new SweetAlertDialogListener() {
                    @Override
                    public void onConfirmClicked(SweetAlertDialog dialog) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelClicked(SweetAlertDialog dialog) {

                    }
                });
            }
        });
    }

    /**
     * validate display name and username and phone
     * @return
     */
    private boolean validate() {
        if (edtDisplayName.getText() != null && edtUsername.getText() != null && edtPhone.getText() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
