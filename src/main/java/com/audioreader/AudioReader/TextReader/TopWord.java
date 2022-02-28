package com.audioreader.AudioReader.TextReader;

public class TopWord {
    private String word;
    private int count;

    public TopWord(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getWord() {
        return word;
    }
}
