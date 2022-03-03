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

            //create sql formatted date object for previous betting day
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatDateTime = BettingDate.previousBettingDay().format(format);
            Date previousBettingDate = Date.valueOf(formatDateTime);

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
                preparedStatement.setDate(1, previousBettingDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                   winningBet = resultSet.getInt("winning_bet");
                   if(winningBet == 0) {
                       winningBet = -99;
                   }
                }

                //retrieve users with bets equal to or less than the correct count in order and save to list (closest to correct count @ index 0)
                sqlStatement = "SELECT full_name, profile_picture, bet, times_won FROM user_daily_bets JOIN users on user_daily_bets.user_id = users.user_id WHERE user_daily_bets.bet <= ? AND date = ? ORDER BY bet DESC;";
                preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(1, winningBet);
                preparedStatement.setDate(2, previousBettingDate);
                resultSet = preparedStatement.executeQuery();

                //add winners to list of winners
                if(resultSet.next()) {
                    do{
                        winnerList.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("bet"), winningBet, resultSet.getString("profile_picture"), resultSet.getInt("times_won")));
                    } while (resultSet.next());
                } else if(winningBet == -99){
                } else{
                    winnerList.add(new Winners(null, null, winningBet, null, null));
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return winnerList;
        }

        //return bad list without proper id in request
        winnerList.add(new Winners("You are not authorized", 404, -69, null, 0));
        return winnerList;


    }
}
