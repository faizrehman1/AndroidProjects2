package example.moosa.com.chatter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import Utils.ProgressGenerator;


public class CreateAccountActivity extends ActionBarActivity implements ProgressGenerator.OnCompleteListener {
    private EditText emailAddress;
    private EditText userName;
    private EditText password;
    private ProgressGenerator progressGenerator;
    private ActionProcessButton createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        progressGenerator = new ProgressGenerator(this);
        createAccountButton = (ActionProcessButton) findViewById(R.id.usernameCreateAccountId);
        emailAddress = (EditText) findViewById(R.id.userEmail);
        userName = (EditText) findViewById(R.id.userNameAccountId);
        password = (EditText) findViewById(R.id.usernamePasswordId);
        createAccountButton.setMode(ActionProcessButton.Mode.PROGRESS);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        final String email = emailAddress.getText().toString();
        final String username = userName.getText().toString();
        final String pass = password.getText().toString();
        if (email.isEmpty() || username.isEmpty() || pass.isEmpty()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(CreateAccountActivity.this);
            dialog.setTitle("Empty Fields");
            dialog.setMessage("Please Complete The Form");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        } else {
            ParseUser user = new ParseUser();
            user.setUsername(username);
            user.setPassword(pass);
            user.setEmail(email);
            user.put("city", "Karachi");
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        progressGenerator.start(createAccountButton);
                        createAccountButton.setEnabled(false);
                        emailAddress.setEnabled(false);
                        password.setEnabled(false);
                        userName.setEnabled(false);

                        logUserIn(username, pass);
                    }
                }
            });
        }

    }

    private void logUserIn(String uname, String passwrd) {
        if (!uname.equals("") || !passwrd.equals("")) {
            ParseUser.logInInBackground(uname, passwrd, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (e == null) {
                        Log.v("User Logged in", parseUser.getUsername());
                    } else {

                    }
                }
            });
        }
    }


    @Override
    public void onComplete() {
        startActivity(new Intent(CreateAccountActivity.this, ChatActivity.class));


    }
}
