package com.audioreader.AudioReader.TextReader;

import com.audioreader.AudioReader.GetRequest.GetWordOfTheDay;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Path;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.nio.file.Paths;
import java.util.Map;

@RestController
public class FileDownload {
    //playwright(puppeteer)
    //my computer must record it save it to OneDrive server logins in and downloads it to its file system to read then name by date
    //call download at certain time of day then call audioText class on download complete


//Is now downloaded when button on admin_page is pressed. Returns top word for Admin to make sure it is a viable word
    @GetMapping("/admin_page/update")
    public TopWord download() {
        GetWordOfTheDay getWordOfTheDay = new GetWordOfTheDay();
        AudioText audioText = null;
        LocalDateTime now = LocalDateTime.now();
        Date sqlDate = Date.valueOf(LocalDate.now());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM_dd_yyyy");
        String formatDateTime = now.format(format);
        File fileCheck = new File("docxFiles/" + formatDateTime + ".docx");
        String wordToCount = getWordOfTheDay.getWordOfDay();

//Checks if file exists for current date. If not will download it then get count
        if(!fileCheck.exists()){
            try (Playwright playwright = Playwright.create()) {
                Browser browser = playwright.chromium().launch();
                Page page = browser.newPage();
                page.navigate("https://onedrive.live.com/?id=root&cid=742E4615ADB490E1&qt=mru");
                page.waitForSelector("[type=email]");
                for (int i = 0; i < 500; i++) {
                    page.mouse().wheel(0, 100);
                }
                page.type("[type=email]", System.getenv("wordEmail"));
                page.click("#idSIButton9");
                page.waitForSelector("[placeholder=Password]");
                for (int i = 0; i < 500; i++) {
                    page.mouse().wheel(0, 100);
                }
                page.type("[type=password]", System.getenv("wordPass"));
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
                audioText = new AudioText(path.toString());
                System.out.println("Download Complete");
                String word = audioText.countSingleWord(wordToCount).getWord();
                int count = audioText.countSingleWord(wordToCount).getCount();
                SetWinningBet setWinningBet = new SetWinningBet(sqlDate, word, count);
                setWinningBet.setWinningDailyBet();
            }

        } else {
//If file does exist just read file and get count
            audioText = new AudioText("docxFiles/" + formatDateTime + ".docx");
            String word = audioText.countSingleWord(wordToCount).getWord();
            int count = audioText.countSingleWord(wordToCount).getCount();
            SetWinningBet setWinningBet = new SetWinningBet(sqlDate, word, count);
            setWinningBet.setWinningDailyBet();
        }


        return audioText.countSingleWord(wordToCount);

    }


}
