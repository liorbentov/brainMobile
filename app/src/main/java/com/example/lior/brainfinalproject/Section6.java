package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lior.brainfinalproject.Global.QuestionScore;
import com.example.lior.brainfinalproject.Global.TestApplication;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;


public class Section6 extends Activity {

    private Integer counter = 0;
    private Double result = 5.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section6);

        final Button btnQue6 = (Button)findViewById(R.id.btnQue6);
        final EditText answer = (EditText)findViewById(R.id.newNumber);
        final TextView number = (TextView) findViewById(R.id.number);

        final Stopwatch sw = Stopwatch.createStarted();

        btnQue6.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (answer.getText().length() > 0) {
                    if (counter < 4) {
                        Integer temp = Integer.parseInt(answer.getText().toString());
                        Integer nNumber = Integer.parseInt(number.getText().toString());
                        if (nNumber - temp == 7) {
                            result -= 0;
                        } else {
                            if (Math.abs(nNumber - temp) <= 2) {
                                result -= 0.5;
                            } else {
                                result -= 1;
                            }
                        }

                        number.setText(answer.getText());
                        counter++;

                        if (counter == 4) {
                            btnQue6.setText("החסר והמשך");
                        }

                        answer.clearComposingText();
                        answer.selectAll();
                    } else {
                        sw.stop();
                        ((TestApplication) getApplication()).getTest().saveQuestionScore(
                                new QuestionScore(result, (double) sw.elapsed(TimeUnit.SECONDS)));
                        Intent toResult = new Intent(Section6.this, Section7.class);
                        startActivity(toResult);
                        finish();
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
}
