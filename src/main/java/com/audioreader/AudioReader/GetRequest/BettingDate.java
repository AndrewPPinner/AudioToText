package com.audioreader.AudioReader.GetRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class BettingDate {
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    public static boolean isAfterNineAM(){
        LocalDate localDateToday = LocalDate.now();
        Timestamp todayNineAM = Timestamp.valueOf(dtf.format(localDateToday)+" 09:00:00");
        Timestamp todayNow = new Timestamp(System.currentTimeMillis());
        return todayNow.after(todayNineAM);
    }


    /**
     * @return Returns the current betting date as a LoaclDate object.
     * current betting date is Today BEFORE 9:00AM and Tomorrow AFTER 9:00AM
     */
    public static LocalDate currentBettingDate(){

        LocalDate localDateToday = LocalDate.now();
        if(isAfterNineAM()){
            return localDateToday.plusDays(1);
        }
        else return localDateToday;
    }


    //returns mm-dd-yyyy string format of this object
    @GetMapping("/betting_day")
    public static String toStringFormat(){
        DateTimeFormatter ddtf = DateTimeFormatter.ofPattern("MM-dd-uuuu");
        return ddtf.format(currentBettingDate());
    }

    public static Date sqlPreviousBettingDate() {
        //create sql formatted date object for previous betting day
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = BettingDate.previousBettingDate().format(format);
        return Date.valueOf(formatDateTime);
    }

    public static Date sqlCurrentBettingDate() {
        //create sql formatted date object for previous betting day
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatDateTime = BettingDate.currentBettingDate().format(format);
        return Date.valueOf(formatDateTime);
    }

    public static LocalDate previousBettingDate(){
        return currentBettingDate().minusDays(1);
    }



}
