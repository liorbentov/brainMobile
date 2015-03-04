package com.example.lior.brainfinalproject.Global;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by Lior on 21/02/2015.
 */
public class TestApplication extends Application {
        private Test test;
        private static Hashtable<String, List<Mistake>> testMistakesDictionary;
        private static Hashtable<String, Drawable> nounsImages;
        private static Hashtable<String, String> nounsDictionary;

        public void setTest(Test toSet) {
            this.test = toSet;
        }

        public Test getTest(){
            return this.test;
        }

        public static Hashtable<String, List<Mistake>> getMistakes() {
            if (testMistakesDictionary == null) {
                testMistakesDictionary = new Hashtable<String, List<Mistake>>();
            }

            return testMistakesDictionary;
        }

        public static Hashtable<String, Drawable> getPictures() {
            if (nounsImages == null) {
                nounsImages = new Hashtable<String, Drawable>();
            }

            return nounsImages;
        }

        public static Hashtable<String, String> getDictionary() {
            if (nounsDictionary == null){
                nounsDictionary = new Hashtable<String, String>();

                nounsDictionary.put("closet", "ארון");
                nounsDictionary.put("chair", "כיסא");
                nounsDictionary.put("computer", "מחשב");
                nounsDictionary.put("couch", "ספה");
                nounsDictionary.put("garbage", "פח");
                nounsDictionary.put("hammer", "פטיש");
                nounsDictionary.put("ruller", "סרגל");
                nounsDictionary.put("sharpener", "מחדד");
                nounsDictionary.put("table", "שולחן");
                nounsDictionary.put("tv", "טלויזיה");
            }

            return nounsDictionary;
        }
}
