package com.audioreader.AudioReader.TextReader;

import org.apache.poi.hssf.record.formula.functions.Int;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class AudioText {
    private String fileName;
    private File file;
    private List<String> topWordsList = new ArrayList<>();


    public AudioText(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }


//takes a word and looks for it then creates a new object of word: wordToCount, count: how many times the word was found
    public TopWord countSingleWord(String wordToCount) {
        int counter = 0;
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TopWord(wordToCount, counter);
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
        int i = 0;
        while (i < numToPrint) {
            String word = it.next().toString();
            String[] wordSplit = word.split("=");
            if (!(wordSplit[0].equals("the") || wordSplit[0].equals("you") || wordSplit[0].equals("and") || wordSplit[0].equals("that") || wordSplit[0].equals("this") || wordSplit[0].equals("have") || wordSplit[0].equals("going") || wordSplit[0].equals("i'm") || wordSplit[0].equals("it's") || wordSplit[0].equals("what") || wordSplit[0].equals("but"))) {
//                System.out.println((i + 1) + "): " + word);
                topWordsList.add(word);
                System.out.println(word);
                i++;
            }

        }
    }


    public List<String> getTopWordsList() {
        for (Object obj : topWordsList) {
            obj.toString();
        }
        return topWordsList;
    }

//ties all functions together to get the top word and how many times it is said and returns a new Top word object
    public TopWord getTopWord() {
        topWords(countAllWords(), 50);
        List<String> listOfWords = getTopWordsList();
        String[] word = listOfWords.get(0).split("=");
        int count = Integer.parseInt(word[1]);
        return new TopWord(word[0], count);
    }

}
