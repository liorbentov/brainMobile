package com.example.lior.brainfinalproject.Global;

/**
 * Created by Lior on 21/02/2015.
 */
public class QuestionScore {
    public Double getScore() {
        return m_dScore;
    }

    public void setScore(Double m_dScore) {
        this.m_dScore = m_dScore;
    }

    public Double getTime() {
        return m_dTime;
    }

    public void setTime(Double m_dTime) {
        this.m_dTime = m_dTime;
    }

    private Double m_dScore;
    private Double m_dTime;

    public QuestionScore(){
        this.m_dScore = 0.0;
        this.m_dTime = 0.0;
    }

    public QuestionScore(Double score, Double time) {
        this.m_dScore = score;
        this.m_dTime = time;
    }
}
