package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.lior.brainfinalproject.Global.Mistake;
import com.example.lior.brainfinalproject.Global.QuestionScore;
import com.example.lior.brainfinalproject.Global.TestApplication;
import com.google.common.base.Stopwatch;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Section4 extends Activity {

    private Integer nNounsShowed;
    private List<String> lstAvailableNouns;
    private List<String> lstShowedNouns;
    private Random nounsRandom;

    AlertDialog emptyFieldsDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section4);

        AssetManager assetManager = getAssets();
        String[] files = null;

        try {
            files = assetManager.list("pictures");
        } catch (IOException e){
            e.printStackTrace();
        }

        try {
            for (String file : files) {
                InputStream ims = getClass().getResourceAsStream("/assets/pictures/" + file);
                TestApplication.getPictures().put(
                        TestApplication.getDictionary().get(
                                file.substring(0, file.lastIndexOf("."))), Drawable.createFromStream(ims, null));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        nNounsShowed = 0;
        lstAvailableNouns = new ArrayList<String>();
        for (String curr : TestApplication.getPictures().keySet()) {
            lstAvailableNouns.add(curr);
        }
        lstShowedNouns = new ArrayList<String>();
        nounsRandom = new Random();

        // Set images
        ImageView iv1 = (ImageView)findViewById(R.id.nounPic1);
        iv1.setImageDrawable(getNextNounBitmap(getNextNoun()));
        ImageView iv2 = (ImageView)findViewById(R.id.nounPic2);
        iv2.setImageDrawable(getNextNounBitmap(getNextNoun()));
        ImageView iv3 = (ImageView)findViewById(R.id.nounPic3);
        iv3.setImageDrawable(getNextNounBitmap(getNextNoun()));

        final EditText etNoun1 = (EditText)findViewById(R.id.nounPicText1);
        final EditText etNoun2 = (EditText)findViewById(R.id.nounPicText2);
        final EditText etNoun3 = (EditText)findViewById(R.id.nounPicText3);

        etNoun1.requestFocus();

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

        final Stopwatch sw = Stopwatch.createStarted();

        final Button btnQue4 = (Button) findViewById(R.id.btnQue4);
        btnQue4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Check if all is full
                if (etNoun1.getText().length() == 0 || etNoun2.getText().length() == 0 ||
                        etNoun3.getText().length() == 0) {
                    emptyFieldsDialog.show();
                } else {

                    sw.stop();
                    List<String> answersToCheck = new ArrayList<String>();
                    answersToCheck.add(etNoun1.getText().toString());
                    answersToCheck.add(etNoun2.getText().toString());
                    answersToCheck.add(etNoun3.getText().toString());
                    ((TestApplication) getApplication()).getTest().saveQuestionScore(
                            new QuestionScore(checkAnswer(answersToCheck), (double) sw.elapsed(TimeUnit.SECONDS)));
                    Intent tryc = new Intent(Section4.this, Section5.class);
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

    public String getNextNoun()
    {
        Integer nextNoun = nounsRandom.nextInt(lstAvailableNouns.size());
        String currNoun = lstAvailableNouns.get(nextNoun);
        lstShowedNouns.add(currNoun);
        lstAvailableNouns.remove(currNoun);
        nNounsShowed++;
        return (currNoun);
    }

    public Drawable getNextNounBitmap(String wantedNounBitmap)
    {
        return (TestApplication.getPictures().get(wantedNounBitmap));
    }

    public int getNounsNumberShowed()
    {
        return nNounsShowed;
    }

    public double checkAnswer(List<String> answersToCheck) {
        Double answerScore = 3.0;
        for (int currAnswerIndex = 0; currAnswerIndex < answersToCheck.size(); currAnswerIndex++)
        {
            String currAnswer = answersToCheck.get(currAnswerIndex);
            String correctAnswer = lstShowedNouns.get(currAnswerIndex);

            if (!correctAnswer.equals(currAnswer))
            {
                Boolean bIsAnswerFound = false;

                for (Mistake currMistake : TestApplication.getMistakes().get(correctAnswer))
                {
                    if (currMistake.input.equals(currAnswer))
                    {
                        answerScore -= currMistake.toSubtract;
                        bIsAnswerFound = true;
                        break;
                    }
                }

                if (!bIsAnswerFound)
                {
                    answerScore -= 1;
                }
            }
        }

        return answerScore;
    }
}
