package example.moosa.com.firebaseexampleme;

import com.firebase.client.Firebase;

/*
 * Created by Moosa on 5/19/2015.
 */
public class FireChatApplication extends android.app.Application {
    @Override
    public void onCreate() {
        Firebase.setAndroidContext(this);
        super.onCreate();
    }
}
