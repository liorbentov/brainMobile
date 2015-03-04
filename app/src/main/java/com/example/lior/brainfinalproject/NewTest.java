package com.example.lior.brainfinalproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.lior.brainfinalproject.Global.Mistake;
import com.example.lior.brainfinalproject.Global.Test;
import com.example.lior.brainfinalproject.Global.TestApplication;


public class NewTest extends Activity {
//    final Button btnNewTest = (Button) findViewById(R.id.button);

    AlertDialog userIDEmptyDialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_test);
        try {
            InitializeTestDictionary();
        }
        catch(Exception e) {

        }

        final EditText userID = (EditText) findViewById(R.id.userID);
        final Button btnNewTest = (Button) findViewById(R.id.button);
        final AlertDialog.Builder userIDEmptyDialogBuilder;

        userIDEmptyDialogBuilder = new AlertDialog.Builder(this);
        userIDEmptyDialogBuilder.setMessage("אנא רשום את תעודת הזהות")
                .setCancelable(false)
                .setPositiveButton("בסדר",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int id) {
                                dialogInterface.cancel();
                            }
                        });
        userIDEmptyDialog = userIDEmptyDialogBuilder.create();

        btnNewTest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                // Check if the userID is full
                if (userID.getText().length() == 0){
                    userIDEmptyDialog.show();
                }
                else {
                    Test t = new Test(userID.getText().toString(), 0.0, 0.0, new Date());
                    ((TestApplication) getApplication()).setTest(t);
                    Intent tryc = new Intent(NewTest.this, Section1.class);
                    startActivity(tryc);
                    finish();
                }
            }
        });
    }


    private void InitializeTestDictionary() throws IOException
    {
        AssetManager assetManager = getAssets();
        InputStream input = null;
        BufferedReader reader = null;

        try {
            input = assetManager.open("nouns.csv");

            reader = new BufferedReader(new InputStreamReader(input));
            String line;
            while ((line = reader.readLine()) != null){

                // Gets the correct noun
                String[] currLine = line.split(",");
                List<Mistake> mistakes = new ArrayList<Mistake>();

                // Load all of it's common typos into the dictionary
                for (int lineIndex = 1; lineIndex <= currLine.length - 1; lineIndex += 2)
                {
                    if (!currLine[lineIndex].equals(""))
                    {
                        Mistake mistakeToAdd = new Mistake(currLine[lineIndex], Double.parseDouble(currLine[lineIndex + 1]));
                        mistakes.add(mistakeToAdd);
                    }
                    else
                    {
                        break;
                    }
                }

                TestApplication.getMistakes().put(currLine[0], mistakes);
            }
        }
        catch (Exception e) {
        }
        finally {
            if (input != null){
                input.close();
            }

            if (reader != null) {
                reader.close();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_new_test, menu);
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
