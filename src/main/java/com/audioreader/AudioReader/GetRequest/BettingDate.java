package com.audioreader.AudioReader.GetRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
public class BettingDate {
    private static LocalDate currentBettingDate = LocalDate.now();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    private static LocalDate localDateToday = LocalDate.now();
    private static Timestamp todayNineAM = Timestamp.valueOf(dtf.format(localDateToday)+" 09:00:00");
    private static Timestamp todayNow = new Timestamp(System.currentTimeMillis());


    public static boolean afterNineAM(){
        return todayNow.after(todayNineAM);
    }


    /**
     * @return Returns the current betting date as a LoaclDate object.
     * current betting date is Today BEFORE 9:00AM and Tomorrow AFTER 9:00AM
     */
    public static LocalDate current(){
        if(afterNineAM()){
            return localDateToday.plusDays(1);
        }
        else return localDateToday;
    }


    //returns mm-dd-yyyy string format of this object
    @GetMapping("/betting_day")
    public static String toStringFormat(){
        DateTimeFormatter ddtf = DateTimeFormatter.ofPattern("MM-dd-uuuu");
        return ddtf.format(current());
    }

    public static LocalDate previousBettingDay(){
        return current().minusDays(1);
    }



}
