package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.lior.brainfinalproject.Global.QuestionScore;
import com.example.lior.brainfinalproject.Global.TestApplication;
import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;


public class Section2 extends Activity {

    private Spinner spCountry;
    private Spinner spCity;
    private Spinner spFloor;

    private Integer m_nCountry = 12;
    private Integer m_nCity = 17;
    private Integer m_nFloor = 1;
    private double AnswerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section2);

        spCountry = (Spinner) findViewById(R.id.spCountry);
        spCity = (Spinner) findViewById(R.id.spCity);
        spFloor = (Spinner) findViewById(R.id.spFloor);

        this.AnswerScore = 3.0;

        final Stopwatch sw = Stopwatch.createStarted();

        final Button btnQue2 = (Button) findViewById(R.id.btnQue2);
        btnQue2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sw.stop();
                ((TestApplication)getApplication()).getTest().saveQuestionScore(
                        new QuestionScore(
                                checkAnswer(spCountry.getSelectedItemPosition(),
                                        spCity.getSelectedItemPosition(),
                                        spFloor.getSelectedItemPosition()),
                                (double)sw.elapsed(TimeUnit.SECONDS)));
                Intent tryc = new Intent(Section2.this, Section3.class);
                startActivity(tryc);
                finish();
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

    public double checkAnswer(int nCountryAnswer, int nCityAnswer, int nFloorAnswer)
    {
        // City
        if (nCityAnswer != this.m_nCity)
        {
            if ((nCityAnswer == this.m_nCity - 1) || (nCityAnswer == this.m_nCity + 1))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                this.AnswerScore -= 1;
            }
        }

        // Country
        if (nCountryAnswer != (this.m_nCountry))
        {
            if ((nCountryAnswer == this.m_nCountry - 1) || (nCountryAnswer == this.m_nCountry + 1))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                this.AnswerScore -= 1;
            }
        }

        // Check Floor
        if (nFloorAnswer != this.m_nFloor)
        {
            if ((nFloorAnswer == this.m_nFloor - 1) || (nFloorAnswer == this.m_nFloor + 1))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                this.AnswerScore -= 1;
            }
        }

        return (this.AnswerScore);
    }
}
