package com.svmc.android.locationsupportteam.firebase.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.svmc.android.locationsupportteam.R;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;
import com.svmc.android.locationsupportteam.utils.Constans;

/**
 * Created by TUNGTS on 3/17/2019
 */

public class GoogleAuth extends BaseAuthenticated<AuthCallback> {

    private Activity activity;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount acct;

    private GoogleAuth() {}

    private static GoogleAuth instance;
    public static GoogleAuth getInstance() {
        if (instance == null) {
            instance = new GoogleAuth();
        }
        return instance;
    }

    public void innitGoogleAuth(Activity activity) {
        this.activity = activity;
    }

    public void configureGoogleSignIn() {
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, Constans.RequestCode.RC_SIGN_IN);
    }

    public void setAcct(GoogleSignInAccount acct) {
        this.acct = acct;
    }

    public GoogleSignInAccount getAcct() {
        return acct;
    }

    @Override
    public void fireBaseAuth(final AuthCallback callback) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        FirebaseUtils.getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(this.getClass().getName(), "signInWithCredential:success");
                            callback.onComplete(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(this.getClass().getName(), "signInWithCredential:failure", task.getException());
                            callback.onComplete(false);

                        }
                    }
                });
    }

    @Override
    public void signOut() {
        // Google revoke access
        if (mGoogleSignInClient != null) {
            // Firebase sign out
            FirebaseUtils.getFirebaseAuth().signOut();

            mGoogleSignInClient.revokeAccess().addOnCompleteListener(activity,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //updateUI(null);
                            Log.d(this.getClass().getName(), "Log Out");
                        }
                    });
        }
    }
}
