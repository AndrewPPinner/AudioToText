package com.audioreader.AudioReader;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.nio.file.Paths;

public class FileDownload {
    //playwright(puppeteer)
    //my computer must record it save it to OneDrive server logins in and downloads it to its file system to read then name by date
    //call download at certain time of day then call audioText class on download complete

    public FileDownload() {
        download();
    }

    private void download() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            Page page = browser.newPage();
            page.navigate("https://onedrive.live.com/edit.aspx?resid=742E4615ADB490E1!758&ithint=file%2cdocx");
            page.waitForSelector("[type=email]");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, 100);
            }
            page.type("[type=email]", "email@email.com");
            page.click("#idSIButton9");
            page.waitForSelector("[placeholder=Password]");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, 100);
            }
            page.type("[type=password]", "Password123");
            page.click("#idSIButton9");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, 100);
            }
            page.click("[type=button]");
            for (int i = 0; i < 500; i++) {
                page.mouse().wheel(0, -100);
            }
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")).setFullPage(true));
            System.out.println("Complete");

        }
    }


}
