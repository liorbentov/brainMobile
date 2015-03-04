package com.example.lior.brainfinalproject.Global;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.media.Image;
import android.provider.MediaStore;

import com.google.common.io.Files;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.io.*;
import java.util.List;

/**
 * Created by Lior on 21/02/2015.
 */
public class Test {

    // Const Members
    final public String CSV_SEPEARATOR = ",";
    final private String DICTIONARY_FILE_NAME = "nouns.csv";
    final private String NOUN_PIC_SUFFIX = ".png";

    private String m_strID;
    private Double m_dScore;
    private Double m_dTime;
    private Date m_dtDate;
    private LinkedList<QuestionScore> m_lQuestions;

    public String getUserID() {
        return m_strID;
    }

    public Double getScore() {
        return m_dScore;
    }

    public Double getTime() {
        return m_dTime;
    }

    public Date getDate() {
        return m_dtDate;
    }

    public Test(){
        this("", 0.0, 0.0, new Date());
    }

    public Test(String user, Double score, Double time, Date date){
        this.m_strID = user;
        this.m_dScore = score;
        this.m_dTime = time;
        this.m_dtDate = date;
        this.m_lQuestions = new LinkedList<QuestionScore>();
    }

    public void saveQuestionScore(QuestionScore qs){
        this.m_lQuestions.add(qs);
        this.m_dScore = this.m_dScore + qs.getScore();
        this.m_dTime = this.m_dTime + qs.getTime();
    }

    private void InitializeTestDictionary() throws IOException
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("nouns.csv"));
            String line;
            while ((line = reader.readLine()) != null){

                // Gets the correct noun
                String[] currLine = line.split(CSV_SEPEARATOR);
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
            reader.close();
        }
        catch (Exception e) {
        }
    }
}
