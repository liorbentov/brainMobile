package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.example.lior.brainfinalproject.Global.Test;
import com.example.lior.brainfinalproject.Global.TestApplication;


public class Results extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        TextView tvScore = (TextView) findViewById(R.id.score);
        TextView tvTime = (TextView) findViewById(R.id.time);
        TextView tvTester = (TextView) findViewById(R.id.tester);

        Test test = ((TestApplication) getApplication()).getTest();

        tvTester.setText(test.getUserID());
        tvScore.setText(test.getScore().toString());
        tvTime.setText(test.getTime().toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
