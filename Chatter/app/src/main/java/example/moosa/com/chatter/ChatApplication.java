package example.moosa.com.chatter;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import Model.Message;

/*
 * Created by Moosa on 7/7/2015.
 */
public class ChatApplication extends Application {
    public static final String APP_KEY_ID="GVrzcM8gJ2Nsu0VgEIVISRCdyeW5myZOljG2bhr4";
    public static final String APP_CLIENT_ID="VyQPzcGW58efdgf9yYYzerQcV3exnkf7pJrGA0nI";


    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Message.class);
        Parse.initialize(this,APP_KEY_ID ,APP_CLIENT_ID );

    }
}
