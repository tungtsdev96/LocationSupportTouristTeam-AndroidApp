package com.svmc.android.locationsupportteam.firebase.auth;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.svmc.android.locationsupportteam.firebase.FirebaseUtils;

/**
 * Created by TUNGTS on 3/17/2019
 */

public class EmailPasswordAuth extends BaseAuthenticated<AuthCallback> {

    private String email;
    private String password;
    private Activity activity;

    private EmailPasswordAuth() {}

    private static EmailPasswordAuth instance;
    public static EmailPasswordAuth getInstance() {
        if (instance == null) {
            instance = new EmailPasswordAuth();
        }
        return instance;
    }

    public void innitData(Activity activity, String email, String password) {
        this.activity = activity;
        this.email = email;
        this.password = password;
    }

    public void signUp(final AuthCallback callback) {
        FirebaseUtils.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(this.getClass().getName(), "createUserWithEmail:success");
                            //FirebaseUser user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
                            callback.onComplete(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(this.getClass().getName(), "createUserWithEmail:failure", task.getException());
                            callback.onComplete(false);
                        }
                    }
                });
    }

    @Override
    public void fireBaseAuth(final AuthCallback callback) {
        FirebaseUtils.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(this.getClass().getName(), "signInWithEmail:success");
                            FirebaseUser user = FirebaseUtils.getFirebaseAuth().getCurrentUser();
                            callback.onComplete(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(this.getClass().getName(), "signInWithEmail:failure", task.getException());
                            callback.onComplete(false);
                        }
                    }
                });
    }

    @Override
    public void signOut() {
        FirebaseUtils.getFirebaseAuth().signOut();
    }

}
