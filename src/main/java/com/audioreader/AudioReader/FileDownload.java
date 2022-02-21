package com.audioreader.AudioReader;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.nio.file.Paths;

public class FileDownload {
    //playwright(puppeteer)
    //my computer must record it save it to OneDrive server logins in and downloads it to its file system to read then name by date
    //call download at certain time of day then call audioText class on download complete

    public FileDownload() {
        download();
    }

    private void download() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM_dd_yyyy");
        String formatDateTime = now.format(format);


        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://onedrive.live.com/?id=root&cid=742E4615ADB490E1&qt=mru");
            page.waitForSelector("[type=email]");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, 100);
            }
            page.type("[type=email]", "andppinner@gmail.com");
            page.click("#idSIButton9");
            page.waitForSelector("[placeholder=Password]");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, 100);
            }
            page.type("[type=password]", "1992jza70R!");
            page.click("#idSIButton9");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, 100);
            }
            page.click("[type=button]");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, -100);
            }
            page.keyboard().press("Control+A");
            page.click("[name=Rename]");
            page.type("#ItemNameEditor-input", formatDateTime);
            page.keyboard().press("Enter");
            // wait for download to start
            Download download  = page.waitForDownload(() -> page.click("[name=Download]"));
            Path path = Paths.get("docxFiles/" + formatDateTime + ".docx");
            download.saveAs(path);
            AudioText audioText = new AudioText(path.toString());
            System.out.println("Download Complete");

        }
    }


}
