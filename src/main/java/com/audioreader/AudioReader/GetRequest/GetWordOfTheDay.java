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
        String wordOfTheDay = "404";
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();

            String sqlStatement = "SELECT word FROM daily_winner WHERE date = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setDate(1, Date.valueOf(BettingDate.current()));
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                wordOfTheDay = resultSet.getString("word");
            }

        } catch (SQLException e) {

        }

        return wordOfTheDay;
    }

    @GetMapping("/previous_word_of_the_day")
    public String getWordOfPreviousDay() {
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        String wordOfTheDay = "404";
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();

            String sqlStatement = "SELECT word FROM daily_winner WHERE date = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setDate(1, BettingDate.sqlPreviousBettingDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                wordOfTheDay = resultSet.getString("word");
            }

        } catch (SQLException e) {

        }

        return wordOfTheDay;
    }

}
