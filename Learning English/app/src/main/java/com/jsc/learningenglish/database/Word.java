package com.jsc.learningenglish.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.orm.SugarRecord;

import java.io.File;

/**
 * Created by ADMIN on 8/14/2017.
 */
public class Word extends SugarRecord {

    private String word;
    private String fullPhrase;
    private String pathImg;
    private String meaning;
    private boolean active;

    public Word() {

    }

    public Word(String word, String fullPhrase, String pathImg, String meaning, boolean active) {
        this.word = word;
        this.fullPhrase = fullPhrase;
        this.pathImg = pathImg;
        this.meaning = meaning;
        this.active = active;
    }

    public String getWord() {
        return word;
    }

    public String getFullPhrase() {
        return fullPhrase;
    }

    public String getPathImg() {
        return pathImg;
    }

    public File getFilePathImg() {
        return new File(this.pathImg);
    }

    public Bitmap getBimapImage() {
        Bitmap myBitmap = null;
        File filePath = this.getFilePathImg();
        if(filePath.exists()){
            myBitmap = BitmapFactory.decodeFile(filePath.getAbsolutePath());
        }

        return myBitmap;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setFullPhrase(String fullPhrase) {
        this.fullPhrase = fullPhrase;
    }

    public void setPathImg(String pathImg) {
        this.pathImg = pathImg;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

}
