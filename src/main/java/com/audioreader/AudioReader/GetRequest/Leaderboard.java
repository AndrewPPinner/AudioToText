package com.audioreader.AudioReader.GetRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Leaderboard {


    @GetMapping("/leaderboard")
    public List<Winners> getLeaderboard(){
        List<Winners> leaderboardList = new ArrayList<>();
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        try {
            //retrieve users with bets equal to or less than the correctCount() in order and save to list (closest to correct count @ index 0)
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT full_name, profile_picture, times_won FROM users WHERE times_won > 0 ORDER BY times_won DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                leaderboardList.add(new Winners(resultSet.getString("full_name"), null, 0, resultSet.getString("profile_picture"), resultSet.getInt("times_won")));
            }
            connection.close();
        } catch (SQLException e) {

        }
        

        return leaderboardList;
    }
}
