package com.svmc.android.locationsupportteam.ui.auth;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.svmc.android.locationsupportteam.data.local.PlaceSaveController;
import com.svmc.android.locationsupportteam.data.prefs.AppPreferencens;
import com.svmc.android.locationsupportteam.entity.DeviceUser;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.firebase.auth.AuthCallback;
import com.svmc.android.locationsupportteam.firebase.auth.EmailPasswordAuth;
import com.svmc.android.locationsupportteam.firebase.auth.GoogleAuth;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.ApiFactory;
import com.svmc.android.locationsupportteam.network.BaseCallBack;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

import okhttp3.ResponseBody;

/**
 * Created by TUNGTS on 3/19/2019
 */

public class PresenterSignImpl implements MVPSignIn.IPresentSignIn {

    final String TAG = "PresenterSignImpl";

    private MVPSignIn.IViewSignIn viewSignIn;
    private MVPSignIn.IModelSignIn modelSignIn;

    public PresenterSignImpl(MVPSignIn.IViewSignIn viewSignIn) {
        this.viewSignIn = viewSignIn;
        this.modelSignIn = new ModelSignInImpl();
    }

    @Override
    public void close() {
        viewSignIn.closeActivity();
    }

    @Override
    public void signUpWithEmailPassWord(String email, String password) {
        if (validate(email, password)) {
            viewSignIn.showProgressDialog("");
            EmailPasswordAuth.getInstance().innitData((Activity) viewSignIn, email, password);
            EmailPasswordAuth.getInstance().signUp(new AuthCallback() {
                @Override
                public void onComplete(boolean isSuccess) {
                    if (isSuccess) viewSignIn.onSignUpSuccess();
                    else viewSignIn.onSignUpFail();
                    viewSignIn.hideProgressDialog();
                }
            });
            return;
        }
        viewSignIn.onSignUpFail();
    }

    @Override
    public void signInWithEmailPassWord(String email, String password) {
        if (validate(email, password)) {
            viewSignIn.showProgressDialog("");
            EmailPasswordAuth.getInstance().innitData((Activity) viewSignIn, email, password);
            EmailPasswordAuth.getInstance().fireBaseAuth(new AuthCallback() {
                @Override
                public void onComplete(boolean isSuccess) {
                    if (isSuccess) viewSignIn.onSignInSuccess();
                    else viewSignIn.onSignInFail();
                    viewSignIn.hideProgressDialog();
                }
            });
            return;
        }
        viewSignIn.ErrValidate();
    }

    @Override
    public boolean validate(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return false;
        return true;
    }

    @Override
    public void signInWithFacebook() {

    }

    // start sign in with gg
    @Override
    public void configureGoogleSignIn() {
        GoogleAuth.getInstance().innitGoogleAuth((Activity) viewSignIn);
        GoogleAuth.getInstance().configureGoogleSignIn();
    }

    @Override
    public void startInWithGoogle() {
        GoogleAuth.getInstance().signIn();
    }

    @Override
    public void fireBaseAuth(GoogleSignInAccount account) {
        viewSignIn.showProgressDialog("");
        GoogleAuth.getInstance().setAcct(account);
        GoogleAuth.getInstance().fireBaseAuth(new AuthCallback() {
            @Override
            public void onComplete(boolean isSuccess) {
                if (isSuccess) viewSignIn.onSignInSuccess();
                else viewSignIn.onSignInFail();
                viewSignIn.hideProgressDialog();
            }
        });
    }

    @Override
    public void addDeviceToDatabase(String playerId, String pushToken) {
        DeviceUser deviceUser = new DeviceUser();
        deviceUser.setPlayerId(playerId);
        deviceUser.setPushToken(pushToken);
        deviceUser.setStatus(DeviceUser.ENABLE);

        modelSignIn.addDeviceToDatabase(deviceUser, new FinishedListener<BaseResultResponse<DeviceUser>>() {
            @Override
            public void onFinished(BaseResultResponse<DeviceUser> result) {
                Log.d(TAG, new Gson().toJson(result));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d(TAG, t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getUserInfor(String userId) {
        modelSignIn.getInforUserById(userId, new FinishedListener<User>() {
            @Override
            public void onFinished(User result) {
                AppPreferencens.getInstance().setCurrentUser(result);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void updateOnline(String uid) {
        ApiFactory.getApiUser().updateOnOffLine(uid, true).enqueue(new BaseCallBack<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void syncPlaceSaved(String uid) {
        modelSignIn.getPlaceSaved(uid, new FinishedListener<BaseResultResponse<ArrayList<Place>>>() {

            @Override
            public void onFinished(BaseResultResponse<ArrayList<Place>> result) {
                if (result.getStatus() != 201 || result.getResult() == null || result.getResult().size() == 0) return;

                for (Place place: result.getResult()) {
                    new PlaceSaveController().addPlace(place);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

}
