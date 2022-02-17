package com.audioreader.AudioReader;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AudioText {
    private int counter;


    public AudioText(String fileName) {


        File file = new File(fileName);

        try(FileInputStream FileInputStream = new FileInputStream(file.getAbsolutePath());) {
            XWPFDocument document = new XWPFDocument(FileInputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            for (int i = 0; i < paragraphs.size(); i++) {
                String[] splitParagraph = paragraphs.get(i).getText().toLowerCase().split(" ");

                for (String para : splitParagraph) {
                    if(para.contains("key")) {
                        counter++;
                    }
                }

            }

            System.out.println(counter);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
