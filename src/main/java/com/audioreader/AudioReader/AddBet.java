package com.audioreader.AudioReader;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddBet {




    @GetMapping("/user_betting")
    public String addBet(@RequestParam Map<String, String> parameters) {
        //domain.com/user_betting?username=<username>&bet=<bet>

        System.out.println(parameters.get("username"));
        System.out.println(parameters.get("bet"));


        return "Bet has been placed";
    }

}
