package com.jsc.learningenglish.feature;

import com.jsc.learningenglish.database.Word;

import java.util.List;
import java.util.Random;

/**
 * Created by ADMIN on 8/18/2017.
 */
public class ListWordManager {
    private List<Word> listWordManager;
    private Random randomGenerator;

    public ListWordManager(List<Word> listWordManager) {
        this.listWordManager = listWordManager;
        randomGenerator = new Random();
    }

    public Word getRandomWord() {
        int index = randomGenerator.nextInt(this.listWordManager.size());
        return this.listWordManager.get(index);
    }

    public Word getWord(int index) {
        if (index > -1) {
            return this.listWordManager.get(index);
        }
        return null;
    }

    public int getSize() {
        return this.listWordManager.size();
    }
}
