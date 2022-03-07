package com.audioreader.AudioReader.GetRequest;

import com.audioreader.AudioReader.SQLRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class GetAllBets {



    @GetMapping("/all_bets")
    public List<Winners> getAllBets() {
        SQLRequest sqlRequest = new SQLRequest();
        List<Winners> allBets = new ArrayList<>();
        try {
            ResultSet resultSet = sqlRequest.queryResults("select full_name, bet from users join user_daily_bets on users.user_id = user_daily_bets.user_id where date = current_date;");
            while (resultSet.next()) {
                allBets.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("bet"), 0, "", 0));
            }
        }
        catch (SQLException e) {
        }

        return allBets;
    }

}
