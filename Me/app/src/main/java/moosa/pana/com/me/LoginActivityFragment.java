package moosa.pana.com.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {
    private LoginButton button;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager callbackManager;
    private Firebase firebase;

    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        button = (LoginButton) view.findViewById(R.id.authButton);
        button.setFragment(this);
        button.setReadPermissions(Arrays.asList("email"));
        firebase = new Firebase("https://mepanacloud.firebaseio.com");
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,
                                                       AccessToken currentAccessToken) {
                updateWithToken();
                // authResultHandler();

            }
        };
      // authResultHandler();
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker.startTracking();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    private void updateWithToken() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            Log.d("Token From Facebook is=", accessToken.getToken());
            firebase.authWithOAuthToken("facebook", accessToken.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(final AuthData authData) {
                    Log.d("Handler", "UserLoggedIn" + authData.getUid());
                    Log.d("onAuthState", authData.getProviderData().toString());
                    final String uuid = authData.getUid();
                    final String displayname = authData.getProviderData().get("displayName").toString();
                    final String imageURL = authData.getProviderData().get("profileImageURL").toString();
                    final String email = authData.getProviderData().get("email").toString() != null ? authData.getProviderData().get("email").toString() : "Email Not Available";
                    ///////////////////SAVE USER DATA////////////////////////
                    Firebase checkUser = firebase.child("users");

                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (!dataSnapshot.hasChild(uuid)) {
                                Map<String, String> userData = new HashMap<>();
                                userData.put("id", uuid);
                                userData.put("name", displayname);
                                userData.put("image", imageURL);
                                userData.put("mail", email);
                                firebase.child("users").child(authData.getUid()).setValue(userData);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    ////////////////////////////////////////////////////////


                    Log.d("User Id - ", uuid + "");
                    Log.d("Display Name - ", displayname + "");
                    Log.d("ImageURL - ", imageURL + "");
                    Log.d("Email - ", email + "");
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", uuid);
                    bundle.putString("name", displayname);
                    bundle.putString("dp", imageURL);
                    bundle.putString("email", email);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra(Vars.bundleTransferToActivity, bundle);
                    getActivity().startActivity(intent);
                    onDestroy();

                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Log.d("Handler", "User Not Logged In" + firebaseError.getMessage());

                }
            });
        } else {
            firebase.unauth();
        }
    }

    private void authResultHandler() {
        firebase.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

                if (authData != null) {
                    accessTokenTracker.stopTracking();
                    Log.d("Handler", "UserLoggedIn" + authData.getUid());
                    Log.d("onAuthState", authData.getProviderData().toString());
                    final String uuid = authData.getUid();
                    final String displayname = authData.getProviderData().get("displayName").toString();
                    final String imageURL = authData.getProviderData().get("profileImageURL").toString();
                    final String email = authData.getProviderData().get("email").toString() != null ? authData.getProviderData().get("email").toString() : "Email Not Available";
                    Log.d("User Id - ", uuid + "");
                    Log.d("Display Name - ", displayname + "");
                    Log.d("ImageURL - ", imageURL + "");
                    Log.d("Email - ", email + "");
                    Bundle bundle = new Bundle();
                    bundle.putString("uid", uuid);
                    bundle.putString("name", displayname);
                    bundle.putString("dp", imageURL);
                    bundle.putString("email", email);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.putExtra(Vars.bundleTransferToActivity, bundle);

                    getActivity().startActivity(intent);
                    onDestroy();
                } else {
                    Log.d("Handler", "User Not Logged In");
                }
            }
        });
    }
}
