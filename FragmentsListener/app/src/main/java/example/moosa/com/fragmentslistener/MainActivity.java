package example.moosa.com.fragmentslistener;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements FirstFragment.OnFragmentInteractionListener,
        SecondFragment.OnFragmentInteractionListener {
    int i = 0;
FirstFragment first;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        first= (FirstFragment) getFragmentManager().findFragmentById(R.id.linearLayoutInMainXml);
        View v=first.getView();
        tv= (TextView) v.findViewById(R.id.textView);

    }
private void changeValueinTextBox(){
   tv.setText("Value of i is: "+i);
}
    @Override
    public void onFragmentInteraction() {
        getFragmentManager().beginTransaction().add(R.id.secondLinearLayout, SecondFragment.newInstance("asd", "asd")).commit();
        Toast.makeText(this, "Fragment Added", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        if (uri.toString() == "inc") {
            i++;
            changeValueinTextBox();
            Toast.makeText(this, "increment" + i, Toast.LENGTH_SHORT).show();

        } else {
            i--;
            changeValueinTextBox();
            Toast.makeText(this, "Decrement" + i, Toast.LENGTH_SHORT).show();

        }
    }
}
