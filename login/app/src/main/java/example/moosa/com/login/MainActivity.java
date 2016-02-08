package example.moosa.com.login;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    EditText user;
    EditText pass;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (EditText) findViewById(R.id.editText);
        pass = (EditText) findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equalsIgnoreCase("hello") && pass.getText().toString().equalsIgnoreCase("hello")) {
                    Toast.makeText(MainActivity.this, "login", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this, "login error", Toast.LENGTH_LONG).show();


                }
            }
        });

    }

}
