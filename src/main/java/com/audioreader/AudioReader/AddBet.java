package com.audioreader.AudioReader;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AddBet {




    @GetMapping("/user_betting")
    public String addBet(@RequestParam List<String> parameters) {
        //domain.com/user_betting?parameters=<username>,<bet>
        return "Bet placed";
    }

}
