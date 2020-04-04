package com.svmc.android.locationsupportteam.ui.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.svmc.android.locationsupportteam.listeners.IdTokenCallBack;

/**
 * Create by TUNGTS on 4/18/2019
 */

public abstract class BaseModel {

    protected void getIdToken(final IdTokenCallBack callBack) {
        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mUser == null) callBack.onFailure(new Exception("NotSignIn"));
        mUser.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.d("IdToken", idToken);
                            callBack.onComplete(idToken);
                        } else {
                            // Handle error -> task.getException();
                            callBack.onFailure(task.getException());
                        }
                    }
                });
    }

}
