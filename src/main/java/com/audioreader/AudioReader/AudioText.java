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
        File file = new File("C:/Users/Student/Downloads/Document.docx");

        try(FileInputStream FileInputStream = new FileInputStream(file.getAbsolutePath());) {
            XWPFDocument document = new XWPFDocument(FileInputStream);
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph para : paragraphs) {
                String paragraphText = para.getText().toLowerCase();
                if(paragraphText.contains("right")) {
                    counter++;
                }
            }
            System.out.println(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
