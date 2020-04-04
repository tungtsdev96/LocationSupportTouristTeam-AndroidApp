package com.svmc.android.locationsupportteam.ui.auth;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sweetdialog.SweetAlertDialog;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.listeners.SweetAlertDialogListener;
import com.svmc.android.locationsupportteam.ui.base.BaseActivity;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 3/17/2019
 */

public class SignInActivity extends BaseActivity
        implements MVPSignIn.IViewSignIn, View.OnClickListener {

    private static final String TAG = "SignInActivity";

    private NestedScrollView contentSignIn;
    private ImageView btnClose;
    private EditText edtEmail;
    private EditText edtPassword;
    private TextView btnSignInWithEmail;
    private TextView btnSignUp;
    private View signInWithFacebook;
    private View signInWithGoogle;

    private MVPSignIn.IPresentSignIn presenterSignIn;

    private User user = new User();

    @Override
    public int getContentView() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void onViewCreated(View view) {
        boolean isUpdate = getIntent().getBooleanExtra(Constans.KeyBundle.UPDATE_PROFILE, false);
        if (isUpdate) {
            pushFragment(R.id.content_auth, EditProfileFragment.newInstance(true), false);
            return;
        }

        contentSignIn = findViewById(R.id.content_sign_in);
        contentSignIn.setVisibility(View.VISIBLE);
        presenterSignIn = new PresenterSignImpl(this);
        presenterSignIn.configureGoogleSignIn();
        innitView();
        addEvents();
    }

    private void innitView() {
        btnClose = findViewById(R.id.img_close);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        btnSignInWithEmail = findViewById(R.id.tv_sign_in);
        btnSignUp = findViewById(R.id.tv_sign_up);
        signInWithFacebook = findViewById(R.id.v_fb);
        signInWithGoogle = findViewById(R.id.v_google);
    }

    private void addEvents() {
        btnClose.setOnClickListener(this);
        btnSignInWithEmail.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        signInWithFacebook.setOnClickListener(this);
        signInWithGoogle.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getCurrentFragment(R.id.content_auth);
        if (fragment instanceof EditProfileFragment) {
            fragment.onActivityResult(requestCode, resultCode, data);
            return;
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constans.RequestCode.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                presenterSignIn.fireBaseAuth(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.d(TAG, "Google sign in failed", e);
            }
        }

    }

    @Override
    public void onClick(View view) {
        hideKeyboard();
        switch (view.getId()) {
            case R.id.img_close:
                presenterSignIn.close();
                break;
            case R.id.tv_sign_in:
                presenterSignIn.signInWithEmailPassWord(edtEmail.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.tv_sign_up:
                presenterSignIn.signUpWithEmailPassWord(edtEmail.getText().toString(), edtPassword.getText().toString());
                break;
            case R.id.v_fb:
                presenterSignIn.signInWithFacebook();
                break;
            case R.id.v_google:
                presenterSignIn.startInWithGoogle();
                break;
        }
    }

    @Override
    public void showProgressDialog(String message) {
        showProgressGlobalDialog(message);
    }

    @Override
    public void hideProgressDialog() {
        hideProgressGlobalDialog();
    }

    @Override
    public void closeActivity() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onSignUpSuccess() {
        presenterSignIn.addDeviceToDatabase(getPlayerId(), getPushToken());
        FirebaseUser user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        this.user.setUserId(user.getUid());
        this.user.setEmail(user.getEmail());
        pushFragment(R.id.content_auth, EditProfileFragment.newInstance(false), false);
    }

    @Override
    public void onSignUpFail() {
        showErrDialog("Đăng kí không thành công");
    }

    @Override
    public void onSignInSuccess() {
        // update device
        presenterSignIn.addDeviceToDatabase(getPlayerId(), getPushToken());

        // update online
        FirebaseUser user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
        presenterSignIn.updateOnline(user.getUid());

        // save user to prefs
        presenterSignIn.getUserInfor(user.getUid());

        // sync place saved
        presenterSignIn.syncPlaceSaved(user.getUid());

        showSweetAlert("", "Đăng nhập thành công", null, "Xong", SweetAlertDialog.SUCCESS_TYPE, new SweetAlertDialogListener() {
            @Override
            public void onConfirmClicked(SweetAlertDialog dialog) {
                dialog.dismiss();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }

            @Override
            public void onCancelClicked(SweetAlertDialog dialog) {

            }
        });
    }

    @Override
    public void onSignInFail() {
        showErrDialog("Email hoặc Password chưa đúng");
    }

    @Override
    public void ErrValidate() {
        showErrDialog("Hãy điền email hoặc mật khẩu muốn đăng kí");
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
