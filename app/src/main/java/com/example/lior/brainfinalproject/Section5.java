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

import com.example.lior.brainfinalproject.Global.Mistake;
import com.example.lior.brainfinalproject.Global.QuestionScore;
import com.example.lior.brainfinalproject.Global.TestApplication;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Section5 extends Activity {

    public EditText etNoun1;
    public EditText etNoun2;
    public EditText etNoun3;

    AlertDialog emptyFieldsDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section5);
        final Stopwatch sw = Stopwatch.createStarted();

        etNoun1 = (EditText)findViewById(R.id.nounMemory1);
        etNoun2 = (EditText)findViewById(R.id.nounMemory2);
        etNoun3 = (EditText)findViewById(R.id.nounMemory3);


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

        final Button btnQue5 = (Button) findViewById(R.id.btnQue5);
        btnQue5.setOnClickListener(new View.OnClickListener(){

            Double answerScore = 3.0;
            public void onClick(View v) {

                // Check if all is full
                if (etNoun1.getText().length() == 0 || etNoun2.getText().length() == 0 ||
                        etNoun3.getText().length() == 0) {
                        emptyFieldsDialog.show();
                } else {

                    List<String> answersToCheck = new ArrayList<String>();
                    answersToCheck.add(etNoun1.getText().toString());
                    answersToCheck.add(etNoun2.getText().toString());
                    answersToCheck.add(etNoun3.getText().toString());

                    // If all is full
                    for (int currAnswerIndex = 0; currAnswerIndex < answersToCheck.size(); currAnswerIndex++) {
                        Boolean bIsAnswerFound = false;
                        String currAnswer = answersToCheck.get(currAnswerIndex);

                        for (int correctAnswerIndex = 0;
                             correctAnswerIndex < 3;
                             correctAnswerIndex++) {
                            String correctAnswer = Section3.getNounsShowed().get(correctAnswerIndex);

                            if (!correctAnswer.equals(currAnswer)) {
                                for (Mistake currMistake : TestApplication.getMistakes().get(correctAnswer))
                                {
                                    if (currMistake.input.equals(currAnswer)) {
                                        answerScore -= currMistake.toSubtract;
                                        bIsAnswerFound = true;
                                        break;
                                    }
                                }
                            } else {
                                bIsAnswerFound = true;
                                break;
                            }
                        }

                        if (!bIsAnswerFound) {
                            answerScore -= 1;
                        }
                    }

                    sw.stop();
                    ((TestApplication) getApplication()).getTest().saveQuestionScore(
                            new QuestionScore(answerScore, (double) sw.elapsed(TimeUnit.SECONDS)));
                    Intent tryc = new Intent(Section5.this, Section6.class);
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
}
