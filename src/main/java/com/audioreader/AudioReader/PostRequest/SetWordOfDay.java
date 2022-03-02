package com.audioreader.AudioReader.PostRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.Map;

@RestController
public class SetWordOfDay {

    @PostMapping("admin_page/set_daily_word")
    public void setWordOfTheDay(@RequestBody Map<String, String> wordOfTheDay) {
        System.out.println(wordOfTheDay.get("word"));
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        try {
//checks if there is already a winning bet for the date. If so update it, if not insert a new one. (This is mostly for the time being while we manually update it, so we don't break the db)
//week 1 is currently hard coded and needs logic built to see what week we are in
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();
            String sqlStatement = "INSERT INTO daily_winner (week_id, word, date) VALUES (1, ?, current_date);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, wordOfTheDay.get("word"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

}
