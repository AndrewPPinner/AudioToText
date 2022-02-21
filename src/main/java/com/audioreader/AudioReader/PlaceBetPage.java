package com.audioreader.AudioReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlaceBetPage {


    @RequestMapping("/betting")
    public String placeBetPage() {
        return "placeBetPage/place_bet.html";
    }
}
