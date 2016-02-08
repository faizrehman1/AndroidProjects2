package com.moosa.firebase;

import com.firebase.client.Firebase;

/**
 * Created by Moosa on 5/19/2015.
 */
public class FireApplication extends android.app.Application {
    @Override
    public void onCreate() {

        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
