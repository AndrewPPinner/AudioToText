package com.audioreader.AudioReader;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;

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
    private List<Object> topWordsList = new ArrayList<>();


    public AudioText(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    //moved logic for counting a single word from constructor to separate method

    public void countSingleWord(String wordToCount) {
        List<String> listWordsInDoc = new ArrayList<>();
        try (FileInputStream FileInputStream = new FileInputStream(file.getAbsolutePath());) {
            XWPFDocument document = new XWPFDocument(FileInputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            List<XWPFTable> tables = document.getTables();
            for (XWPFTable table : tables) {

                String[] tableWords = table.getText().toLowerCase().split(" ");
                for (String word : tableWords) {
                    listWordsInDoc.add(word);
                }
            }
            for (int i = 0; i < paragraphs.size(); i++) {
                String[] splitParagraph = paragraphs.get(i).getText().toLowerCase().split(" ");

                for (String para : splitParagraph) {
                    listWordsInDoc.add(para);
                }
            }
            for (String word : listWordsInDoc) {
                if (word.contains(wordToCount)) {
                    counter++;
                }
            }
            System.out.println(wordToCount + ": " + counter);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //generate a map of all words (key) and how many times (value) they appear in the file
    //ignores words 2 characters or less
    public Map<String, Integer> countAllWords() {

        Map<String, Integer> wordTallies = new HashMap<>();
        List<String> allWordsInDocument = new ArrayList<>();

        try (FileInputStream FileInputStream = new FileInputStream(file.getAbsolutePath());) {
            XWPFDocument document = new XWPFDocument(FileInputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            List<XWPFTable> tables = document.getTables();

            for (XWPFTable table : tables) {

                String[] tableWords = table.getText().toLowerCase().split(" ");
                for (String word : tableWords) {
                    allWordsInDocument.add(word);
                }
            }


            //iterate through list of paragraphs
            for (int i = 0; i < paragraphs.size(); i++) {
                //split current paragraph into a String array of words
                String[] paragraphAsWords = paragraphs.get(i).getText().toLowerCase().split(" ");


                //iterate through words
                for (String word : paragraphAsWords) {
                    allWordsInDocument.add(word);
                }
            }
            for (String word : allWordsInDocument) {

                String lowercaseWord = word.toLowerCase();

                //remove common punctuation from the end of a word
                if (lowercaseWord.endsWith(",") || lowercaseWord.endsWith(".") || lowercaseWord.endsWith("?") || lowercaseWord.endsWith("!")) {
                    lowercaseWord = lowercaseWord.substring(0, lowercaseWord.length() - 1);
                }

                //if word is already in map, iterate the count (map value)
                if (wordTallies.containsKey(lowercaseWord)) {
                    wordTallies.put(lowercaseWord, wordTallies.get(lowercaseWord) + 1);
                }
                //otherwise, add word to map with count of 1 (ignore words 2 letters or less)
                else if (lowercaseWord.length() > 2) {
                    wordTallies.put(lowercaseWord, 1);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordTallies;
    }


    public void topWords(Map<String, Integer> countedWords, int numToPrint) {

        //sort Map<String word, Integer count> into a LinkedHashMap
        Map<String, Integer> sortedCountedWords = countedWords.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        //use set and iterator to print values from linkedHashMap in order in a fori stopping at numToPrint
        Set entrySet = sortedCountedWords.entrySet();
        Iterator it = entrySet.iterator();
        for (int i = 0; i < numToPrint; i++) {
            if (it.next().equals("the") || it.next().equals("you") || it.next().equals("and") || it.next().equals("that") || it.next().equals("this") || it.next().equals("have") || it.next().equals("going") || it.next().equals("i'm") || it.next().equals("it's") || it.next().equals("what")) {
                continue;
            }
            Object next = it.next();
            System.out.println((i + 1) + "): " + next);
            topWordsList.add(next);
        }
    }


    public List<Object> getTopWordsList() {
        for (Object obj : topWordsList) {
            obj.toString();
        }
        return topWordsList;
    }
}
