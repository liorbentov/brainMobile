package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lior.brainfinalproject.Global.TestApplication;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Section3 extends Activity {

    private Integer nNounsShowed;
    private List<String> lstAvailableNouns;
    private static List<String> lstShowedNouns;
    private Random nounsRandom;

    private TextView tvNoun;
    public Timer t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section3);

        tvNoun = (TextView)findViewById(R.id.nounToRemember);

        nNounsShowed = 0;
        lstAvailableNouns = new ArrayList<String>();
        for (String curr : TestApplication.getMistakes().keySet()) {
            lstAvailableNouns.add(curr);
        }

        lstShowedNouns = new ArrayList<String>();
        nounsRandom = new Random();

        t = new Timer();
        t.schedule(new showNoun(), 0, 500);
    }

    class showNoun extends TimerTask {

        @Override
        public void run() {
            Section3.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (getNounsNumberShowed() == 3) {
                        t.cancel();
                        Intent tryc = new Intent(Section3.this, Section4.class);
                        startActivity(tryc);
                        finish();
                    } else {
                        tvNoun.setText(getNextNoun());
                    }
                }
            });
        }
    }

    public String getNextNoun()
    {
        int nextNoun = nounsRandom.nextInt(lstAvailableNouns.size());
        String currNoun = lstAvailableNouns.get(nextNoun);
        lstShowedNouns.add(currNoun);
        lstAvailableNouns.remove(currNoun);
        nNounsShowed++;
        return (currNoun);
    }

    public Integer getNounsNumberShowed()
    {
        return nNounsShowed;
    }

    public static List<String> getNounsShowed()
    {
        return lstShowedNouns;
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
