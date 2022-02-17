package com.audioreader.AudioReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SpringBootApplication
public class AudioReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AudioReaderApplication.class, args);
		AudioText audioText = new AudioText();
		FileDownload fileDownload = new FileDownload();


	}

}
