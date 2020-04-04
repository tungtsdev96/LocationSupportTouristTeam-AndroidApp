package com.svmc.android.locationsupportteam.ui.auth;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.svmc.android.locationsupportteam.entity.DeviceUser;
import com.svmc.android.locationsupportteam.entity.User;
import com.svmc.android.locationsupportteam.entity.googlemap.place.detailplace.Place;
import com.svmc.android.locationsupportteam.listeners.FinishedListener;
import com.svmc.android.locationsupportteam.network.BaseResultResponse;

import java.util.ArrayList;

/**
 * Created by TUNGTS on 3/19/2019
 */

public class MVPSignIn {

    public interface IViewSignIn {

        void showProgressDialog(String message);

        void hideProgressDialog();

        void closeActivity();

        void onSignUpSuccess();

        void onSignUpFail();

        void onSignInSuccess();

        void onSignInFail();

        void ErrValidate();

    }

    public interface IPresentSignIn{

        void close();

        void signUpWithEmailPassWord(String email, String password);

        void signInWithEmailPassWord(String email, String password);

        boolean validate(String email, String password);

        void signInWithFacebook();

        // sign with google
        void configureGoogleSignIn();

        void startInWithGoogle();

        void fireBaseAuth(GoogleSignInAccount account);

        // register device
        void addDeviceToDatabase(String playerId, String pushToken);

        /***
         * save to prefs
         * @param userId
         */
        void getUserInfor(String userId);

        /**
         * update user is online
         * @param uid
         */
        void updateOnline(String uid);

        /***
         * save placed
         * @param uid
         */
        void syncPlaceSaved(String uid);
    }

    public interface IModelSignIn {

        void addDeviceToDatabase(DeviceUser deviceUser, FinishedListener<BaseResultResponse<DeviceUser>> finishedListener);

        void getInforUserById(String useId, FinishedListener<User> listener);

        void getPlaceSaved(String uid, FinishedListener<BaseResultResponse<ArrayList<Place>>> listener);
    }

}
