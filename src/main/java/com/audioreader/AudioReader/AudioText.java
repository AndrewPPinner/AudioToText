package com.audioreader.AudioReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class AudioText {


    public AudioText() {
        File file = null;
        WordExtractor extractor = null;
        try
        {

            file = new File("c:\\New.doc");
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            for (int i = 0; i < fileData.length; i++)
            {
                if (fileData[i] != null)
                    System.out.println(fileData[i]);
            }
        }
        catch (Exception exep)
        {
            exep.printStackTrace();
        }






//        File testDoc = new File("C:/Users/Student/Downloads/Document.txt");
//
//        try(Scanner fileReader = new Scanner(testDoc)) {
//            while(fileReader.hasNextLine()) {
//                System.out.println("test");
//                System.out.println(fileReader.nextLine());
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }


}
