package com.audioreader.AudioReader.GetRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;

@RestController
public class GetWordOfTheDay {

    @GetMapping("/word_of_the_day")
    public String getWordOfDay() {
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        String wordOfTheDay = "";
        try {
            boolean userExists = false;
            int numUsersAdded = 0;
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();
            //get list of all previous users
            ResultSet resultSet = statement.executeQuery("SELECT word FROM daily_winner WHERE date = current_date");
            while(resultSet.next()) {
                wordOfTheDay = resultSet.getString("word");
            }

        } catch (SQLException e) {

        }

        return wordOfTheDay;
    }
}
