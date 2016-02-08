package com.moosa.todolist;

import com.firebase.client.Firebase;

/**
 * Created by Moosa on 6/20/2015.
 */
public class FireToDoApplication extends android.app.Application {
    @Override
    public void onCreate() {
        Firebase.setAndroidContext(this);
        super.onCreate();
    }
}
