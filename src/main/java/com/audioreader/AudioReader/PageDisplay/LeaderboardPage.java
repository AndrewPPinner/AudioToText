package com.audioreader.AudioReader.PageDisplay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LeaderboardPage {

    @RequestMapping("/leaderboard_page")
    public String getPage(){
        return "leaderboardPage/leaderboard.html";
    }

}
