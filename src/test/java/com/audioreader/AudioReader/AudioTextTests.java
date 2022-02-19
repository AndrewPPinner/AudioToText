package com.audioreader.AudioReader;

import org.testng.annotations.Test;

import java.util.Map;

public class AudioTextTests {


    @Test
    public void countAllWords_does_its_job() {


        AudioText audioText = new AudioText("docxFiles/02_18_2022.docx");
        Map<String, Integer> actual = audioText.countAllWords();
        System.out.println(actual.size() + " Total unique words");
        for (Map.Entry<String, Integer> words : actual.entrySet()) {
            System.out.println(words.getKey() + "," + words.getValue());
        }
    }

    @Test
    public void printTopWords_sorts_hashmap_by_value_and_prints_top_words() {

        int numberOfWordsToPrint = 50;
        AudioText audioText = new AudioText("docxFiles/02_18_2022.docx");
        Map<String, Integer> countedWordMap = audioText.countAllWords();

        audioText.printTopWords(countedWordMap,numberOfWordsToPrint);

    }


}
