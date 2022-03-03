package com.audioreader.AudioReader.PostRequest;

import com.audioreader.AudioReader.GetRequest.BettingDate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

@RestController
public class SetWordOfDay {

    @PostMapping("admin_page/set_daily_word")
    public void setWordOfTheDay(@RequestBody Map<String, String> wordOfTheDay) {
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
//Takes the word of the day and checks if a word for today is already in if so updates the word and if not inserts
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM daily_winner");
            boolean exist = false;
            while (resultSet.next()) {
                if (resultSet.getDate("date").toLocalDate().equals(BettingDate.current())) {
                    exist = true;
                    String sqlStatement = "UPDATE daily_winner SET word = ? WHERE date = ?;";
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                    preparedStatement.setString(1, wordOfTheDay.get("word"));
                    preparedStatement.setDate(2, Date.valueOf(BettingDate.current()));
                    preparedStatement.executeUpdate();
                }
            }
            if(!exist) {
                String sqlStatement = "INSERT INTO daily_winner (week_id, word, date) VALUES (1, ?, ?);";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setString(1, wordOfTheDay.get("word"));
                preparedStatement.setDate(2, Date.valueOf(BettingDate.current()));
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {

        }

    }


}
