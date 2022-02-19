package com.audioreader.AudioReader;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class AudioText {
    private int counter;
    private String fileName;
    private File file;


    public AudioText(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    //moved logic for counting a single word from constructor to separate method

    public void countSingleWord(String wordToCount){
        try (FileInputStream FileInputStream = new FileInputStream(file.getAbsolutePath());) {
            XWPFDocument document = new XWPFDocument(FileInputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                String[] splitParagraph = paragraphs.get(i).getText().toLowerCase().split(" ");

                for (String para : splitParagraph) {
                    if (para.contains("select")) {
                        counter++;
                    }
                }
            }
            System.out.println(counter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //generate a map of all words (key) and how many times (value) they appear in the file
    //ignores words 2 characters or less
    public Map<String, Integer> countAllWords() {
        Map<String, Integer> wordTallies = new HashMap<>();

        try (FileInputStream FileInputStream = new FileInputStream(file.getAbsolutePath());) {
            XWPFDocument document = new XWPFDocument(FileInputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            //iterate through list of paragraphs
            for (int i = 0; i < paragraphs.size(); i++) {
                //split current paragraph into a String array of words
                String[] paragraphAsWords = paragraphs.get(i).getText().toLowerCase().split(" ");

                //iterate through words
                for (String word : paragraphAsWords) {
                    String lowercaseWord = word.toLowerCase();

                    //remove common punctuation from the end of a word
                    if(lowercaseWord.endsWith(",")||lowercaseWord.endsWith(".")||lowercaseWord.endsWith("?")||lowercaseWord.endsWith("!")){
                        lowercaseWord = lowercaseWord.substring(0,lowercaseWord.length()-1);
                    }

                    //if word is already in map, iterate the count (map value)
                    if (wordTallies.containsKey(lowercaseWord)) {
                        wordTallies.put(lowercaseWord, wordTallies.get(lowercaseWord)+1);
                    }
                    //otherwise, add word to map with count of 1 (ignore words 2 letters or less)
                    else if(lowercaseWord.length()>2){
                        wordTallies.put(lowercaseWord, 1);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordTallies;
    }



    public void printTopWords(Map<String, Integer> countedWords, int numToPrint){
        //sort Map<String word, Integer count> into a LinkedHashMap
        Map<String, Integer> sortedCountedWords = countedWords.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2)-> e1, LinkedHashMap::new));

        //use set and iterator to print values from linkedHashMap in order in a fori stopping at numToPrint
        Set entrySet = sortedCountedWords.entrySet();
        Iterator it = entrySet.iterator();
        for (int i = 0; i < numToPrint; i++) {
            System.out.println((i+1) + "): " + it.next());
        }
    }

}
