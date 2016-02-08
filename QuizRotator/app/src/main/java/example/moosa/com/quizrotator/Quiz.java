package example.moosa.com.quizrotator;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Quiz extends ActionBarActivity {

    private Button nextButton;
    private Button trueButton;
    private Button falseButton;
    private TextView questionView;
    private TrueFalse[] questionBank = {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true),
    };
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        questionView = (TextView) findViewById(R.id.textView2);
        trueButton = (Button) findViewById(R.id.truebutton);
        falseButton = (Button) findViewById(R.id.falsebutton);
        nextButton = (Button) findViewById(R.id.nextbutton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = (index + 1) % questionBank.length;
                updateQuestion();

            }
        });
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

    }

    private void checkAnswer(boolean ans) {
        boolean answerIs = questionBank[index].ismTrueQuestion();
        String answer;
        if (answerIs == ans) {
            answer = "Answer is True";
        } else {
            answer = "Answer is False";

        }
        Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();

    }

    private void updateQuestion() {
        int question = questionBank[index].getmQuestion();
        questionView.setText(question);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("state",index);
    }
}
