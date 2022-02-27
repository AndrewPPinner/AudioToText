package com.audioreader.AudioReader.GetRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class WinnerRequest {
    private List<Winners> winnerList;


    @GetMapping("/daily_winner")
    public List<Winners> dailyWinners(@RequestParam(value = "id") String id) {
        winnerList = new ArrayList<>();
        if (id.equals("123456789")) {

            //create sql formatted date object for today
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatDateTime = now.format(format);
            Date date = Date.valueOf(formatDateTime);

            //log into sql database
            String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
            String dbUser = System.getenv("dbUser");
            String dbPass = System.getenv("dbPass");
            try {
                int winningBet = -99;
                Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);

                //retrieve winning bet for given date (date variable)
                Statement statement = connection.createStatement();
                String sqlStatement = "SELECT winning_bet FROM daily_winner WHERE date = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setDate(1, date);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                   winningBet = resultSet.getInt("winning_bet");
                }

                //retrieve users with bets matching the winning bet for given date
                sqlStatement = "SELECT full_name, profile_picture, bet FROM user_daily_bets JOIN users on user_daily_bets.user_id = users.user_id WHERE user_daily_bets.bet = ? AND date = ?;";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(1, winningBet);
                preparedStatement.setDate(2, date);
                resultSet = preparedStatement.executeQuery();

                //add winners to list of winners
                while (resultSet.next()) {
                    winnerList.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("bet"), winningBet, resultSet.getString("profile_picture")));
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return winnerList;
        }

        //return bad list without proper id in request
        winnerList.add(new Winners("You are not authorized", 404, -69, null));
        return winnerList;


    }
}
