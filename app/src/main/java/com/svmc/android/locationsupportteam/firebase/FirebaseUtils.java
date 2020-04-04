package com.svmc.android.locationsupportteam.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by TUNGTS on 3/17/2019
 */

public class FirebaseUtils {

    public static FirebaseAuth getFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static FirebaseDatabase getFirebaseDatabase(){
        return FirebaseDatabase.getInstance();
    }

}
