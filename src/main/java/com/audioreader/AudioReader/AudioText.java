package com.audioreader.AudioReader;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class AudioText {
    private int counter;


    public AudioText() {

        //get doc file from server downloads using file location and date.docx


        File file = new File("C:/Users/Student/Downloads/Document (3).docx");

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
