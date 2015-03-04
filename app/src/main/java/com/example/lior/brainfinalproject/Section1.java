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


public class Section1 extends Activity {

    private Spinner spDay;
    private Spinner spMonth;
    private Spinner spYear;
    private Spinner spSeason;

    private Integer m_nDay = 22;
    private Integer m_nMonth = 2;
    private Integer m_nYear = 2015;
    private Integer m_nSeason = 1;
    private double AnswerScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section1);

        spDay = (Spinner) findViewById(R.id.spDays);
        spMonth = (Spinner) findViewById(R.id.spMonths);
        spYear = (Spinner) findViewById(R.id.spYears);
        spSeason = (Spinner) findViewById(R.id.spSeasons);

        this.AnswerScore = 4.0;

        final Stopwatch sw = Stopwatch.createStarted();

        final Button btnQue1 = (Button) findViewById(R.id.btnQue1);
        btnQue1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sw.stop();
                ((TestApplication)getApplication()).getTest().saveQuestionScore(
                        new QuestionScore(
                            checkAnswer(Integer.parseInt(spDay.getSelectedItem().toString()),
                                    spMonth.getSelectedItemPosition(),
                                    Integer.parseInt(spYear.getSelectedItem().toString()),
                                    spSeason.getSelectedItemPosition())
                                , (double)sw.elapsed(TimeUnit.SECONDS)));
                Intent tryc = new Intent(Section1.this, Section2.class);
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

    public double checkAnswer(int nDayAnswer, int nMonthAnswer, int nYearAnswer, int nSeasonAnswer)
    {
        // Day
        if (nDayAnswer != this.m_nDay)
        {
            if ((nDayAnswer == this.m_nDay - 1) || (nDayAnswer == this.m_nDay + 1))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                this.AnswerScore -= 1;
            }
        }

        // Month
        if (nMonthAnswer != (this.m_nMonth))
        {
            if ((nMonthAnswer == this.m_nMonth - 1) || (nMonthAnswer == this.m_nMonth + 1))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                this.AnswerScore -= 1;
            }
        }

        // Check Year
        if (nYearAnswer != this.m_nYear)
        {
            if ((nYearAnswer == this.m_nYear - 1) || (nYearAnswer == this.m_nYear + 1))
            {
                this.AnswerScore -= 0.5;
            }
            else
            {
                this.AnswerScore -= 1;
            }
        }

        // Check Season
        if (nSeasonAnswer != (this.m_nSeason))
        {
            if ((nSeasonAnswer == this.m_nSeason - 1) || (nSeasonAnswer == this.m_nSeason + 1))
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
