package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lior.brainfinalproject.Clock.AnalogClockView;
import com.example.lior.brainfinalproject.Global.QuestionScore;
import com.example.lior.brainfinalproject.Global.TestApplication;
import com.google.common.base.Stopwatch;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Section8 extends Activity {

    private AnalogClockView mClockView;

    AlertDialog emptyFieldsDialog = null;
    private int AnswerScore;
    private int Hour;
    private int Minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section8);

        final EditText hour = (EditText)findViewById(R.id.txtQue8Hour);
        final EditText minutes = (EditText)findViewById(R.id.txtQue8Minutes);

        final AlertDialog.Builder emptyFieldsDialogBuilder;

        emptyFieldsDialogBuilder = new AlertDialog.Builder(this);
        emptyFieldsDialogBuilder.setMessage("אנא מלא את כל השדות")
                .setCancelable(false)
                .setPositiveButton("הבנתי",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int id) {
                                dialogInterface.cancel();
                            }
                        });
        emptyFieldsDialog = emptyFieldsDialogBuilder.create();

        mClockView = (AnalogClockView)findViewById(R.id.clock);

        Hour = new Random().nextInt(12);
        Minutes = new Random().nextInt(60);

        mClockView.setTime(Hour, Minutes);
        mClockView.start();

        final Stopwatch sw = Stopwatch.createStarted();
        final Button btnQue8 = (Button) findViewById(R.id.btnQue8);
        btnQue8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Check if all is full
                if (hour.getText().length() == 0 || minutes.getText().length() == 0) {
                    emptyFieldsDialog.show();
                } else {

                    sw.stop();
                    ((TestApplication) getApplication()).getTest().saveQuestionScore(
                            new QuestionScore(checkAnswer(
                                    Integer.parseInt(minutes.getText().toString()),
                                    Integer.parseInt(hour.getText().toString())), (double) sw.elapsed(TimeUnit.SECONDS)));
                    Intent tryc = new Intent(Section8.this, Results.class);
                    startActivity(tryc);
                    finish();
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

    public double checkAnswer(int minutes, int hours)
    {
        // If hour and minutes are in place and exact
        if ((hours == this.Hour) && (minutes == this.Minutes))
        {
            this.AnswerScore -= 0;
        }
        else
        {
            // If the data is exact but the places are wrong
            if ((hours == this.Minutes) && (minutes == this.Hour))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                // If the places are wrong and there is deviation in one place
                if (((hours == this.Minutes) && (minutes >= this.Hour - 1) && (minutes <= this.Hour + 1)) ||
                        (((minutes == this.Hour) && (hours >= this.Minutes - 1) && (hours <= this.Minutes + 1))))
                {
                    this.AnswerScore -= 1;
                }
                else
                {
                    // If the places are wrong and there is deviation in two places
                    if ((minutes >= this.Hour - 1) && (minutes <= this.Hour + 1) &&
                            (hours >= this.Minutes - 1) && (hours <= this.Minutes + 1))
                    {
                        this.AnswerScore -= 1.5;
                    }
                    else
                    {
                        // If the hour is exact
                        if (hours == this.Hour)
                        {
                            // If there is deviation in the minutes
                            if ((minutes >= this.Minutes - 1) && (minutes <= this.Minutes + 1))
                            {
                                this.AnswerScore -= 0.5;
                            }
                            else
                            {
                                this.AnswerScore -= 1;
                            }
                        }
                        else
                        {
                            // If hour with deviation of 1
                            if ((hours >= this.Hour - 1) && (hours <= this.Hour + 1))
                            {
                                // If minutes are exact
                                if (minutes == this.Minutes)
                                {
                                    this.AnswerScore -= 0.5;
                                }
                                else
                                {
                                    // If there is deviation in the minutes
                                    if ((minutes >= this.Minutes - 1) && (minutes <= this.Minutes + 1))
                                    {
                                        this.AnswerScore -= 1;
                                    }
                                    else
                                    {
                                        this.AnswerScore -= 1.5;
                                    }
                                }
                            }
                            else
                            {
                                // If minutes are exact
                                if (minutes == this.Minutes)
                                {
                                    this.AnswerScore -= 1;
                                }
                                else
                                {
                                    // If there is deviation in the minutes
                                    if ((minutes >= this.Minutes - 1) && (minutes <= this.Minutes + 1))
                                    {
                                        this.AnswerScore -= 1.5;
                                    }
                                    else
                                    {
                                        this.AnswerScore -= 2;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return this.AnswerScore;
    }
}
