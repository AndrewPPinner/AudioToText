package com.audioreader.AudioReader.GetRequest;

import com.audioreader.AudioReader.SQLRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Leaderboard {


    @GetMapping("/leaderboard")
    public List<Winners> getLeaderboard(){
        SQLRequest sqlRequest = new SQLRequest();
        List<Winners> leaderboardList = new ArrayList<>();
        try {
            ResultSet resultSet = sqlRequest.queryResults("SELECT full_name, profile_picture, times_won FROM users WHERE times_won > 0 ORDER BY times_won DESC");
            while (resultSet.next()) {
                leaderboardList.add(new Winners(resultSet.getString("full_name"), null, 0, resultSet.getString("profile_picture"), resultSet.getInt("times_won")));
            }
        }
        catch (SQLException e) {
        }

        return leaderboardList;
    }
}
