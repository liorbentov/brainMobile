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

import com.example.lior.brainfinalproject.Global.QuestionScore;
import com.example.lior.brainfinalproject.Global.TestApplication;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Section7 extends Activity {

    AlertDialog emptyFieldsDialog = null;
    private double AnswerScore;
    private double Hour;
    private double Minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section7);

        final EditText hour = (EditText)findViewById(R.id.txtQue7Hour);
        final EditText minutes = (EditText)findViewById(R.id.txtQue7Minutes);

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

        this.AnswerScore = 3.0;
        this.Hour = 17.0;
        this.Minutes = (double)30;

        final Stopwatch sw = Stopwatch.createStarted();

        final Button btnQue7 = (Button) findViewById(R.id.btnQue7);
        btnQue7.setOnClickListener(new View.OnClickListener() {
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
                    Intent tryc = new Intent(Section7.this, Section8.class);
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
            // If the hour is not in the AM-PM correct mode
            if (((hours + 12) % 24 == this.Hour) && (minutes == this.Minutes))
            {
                this.AnswerScore -= 1;
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
                    // If the hour is not in the AM-PM correct mode
                    if (((hours + 12) % 24 == this.Minutes) && (minutes == this.Hour))
                    {
                        this.AnswerScore -= 1.5;
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

                            // If the places are wrong and there is deviation in one place and the AM-PM mode is wrong
                            if ((((hours + 12) % 24 == this.Minutes) && (minutes >= this.Hour - 1) && (minutes <= this.Hour + 1)) ||
                                    (((minutes == this.Hour) && ((hours + 12) % 24 >= this.Minutes - 1) && ((hours + 12) % 24 <= this.Minutes + 1))))
                            {
                                this.AnswerScore -= 2;
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
                                    // If the places are wrong and there is deviation in two places and the AM-PM mode is wrong
                                    if ((minutes >= this.Hour - 1) && (minutes <= this.Hour + 1) &&
                                            ((hours + 12) % 24 >= this.Minutes - 1) && ((hours + 12) % 24 <= this.Minutes + 1))
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
                                            // If the hour is exact and AM-PM mode is wrong
                                            if ((hours + 12) % 24 == this.Hour)
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
                                                    // If hour with deviation of 1 and AM-PM mode is wrong
                                                    if (((hours + 12) % 24 >= this.Hour - 1) && ((hours + 12) % 24 <= this.Hour + 1))
                                                    {
                                                        // If minutes are exact
                                                        if (minutes == this.Minutes)
                                                        {
                                                            this.AnswerScore -= 1.5;
                                                        }
                                                        else
                                                        {
                                                            // If there is deviation in the minutes
                                                            if ((minutes >= this.Minutes - 1) && (minutes <= this.Minutes + 1))
                                                            {
                                                                this.AnswerScore -= 2;
                                                            }
                                                            else
                                                            {
                                                                this.AnswerScore -= 2.5;
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        // If minutes are exact
                                                        if (minutes == this.Minutes)
                                                        {
                                                            this.AnswerScore -= 2;
                                                        }
                                                        else
                                                        {
                                                            // If there is deviation in the minutes
                                                            if ((minutes >= this.Minutes - 1) && (minutes <= this.Minutes + 1))
                                                            {
                                                                this.AnswerScore -= 2.5;
                                                            }
                                                            else
                                                            {
                                                                this.AnswerScore -= 3;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
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
