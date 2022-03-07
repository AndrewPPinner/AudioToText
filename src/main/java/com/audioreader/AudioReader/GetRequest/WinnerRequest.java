package com.audioreader.AudioReader.GetRequest;

import com.audioreader.AudioReader.SQLRequest;
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

    //returns the winners for previous betting day
    @GetMapping("/daily_winner")
    public List<Winners> dailyWinners(@RequestParam(value = "id") String id) {
        List<Winners> qualifiedBetUsers = dailyQualifyingWinners(id);
        List<Winners> actualWinners = new ArrayList<>();
        //start with bestGuess being the correct count
        int bestGuess = correctCount();

        for (int i = 0; i < qualifiedBetUsers.size(); i++) {
            //first add any users who had matching bet
            if (qualifiedBetUsers.get(i).getBet() == bestGuess) {
                actualWinners.add(qualifiedBetUsers.get(i));
            }
            //if there are one or more users with winning bets, return the list
            else if (actualWinners.size() > 0) {
                return actualWinners;
            }
            //add the next closest bet as the winner and set their guess as bestGuess to check for ties
            else {
                actualWinners.add(qualifiedBetUsers.get(i));
                bestGuess = qualifiedBetUsers.get(i).getBet();
            }
        }
        return actualWinners;
    }


    //returns a list of users with bets less than or equal to the correctCount()
    public List<Winners> dailyQualifyingWinners(@RequestParam(value = "id") String id) {
        winnerList = new ArrayList<>();
        SQLRequest sqlRequest = new SQLRequest();
        if (id.equals("123456789")) {

            try {
                Connection connection = sqlRequest.getConnection();
                String sqlStatement = "SELECT full_name, profile_picture, bet, times_won FROM user_daily_bets JOIN users on user_daily_bets.user_id = users.user_id WHERE user_daily_bets.bet <= ? AND date = ? ORDER BY bet DESC;";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(1, correctCount());
                preparedStatement.setDate(2, BettingDate.sqlPreviousBettingDate());
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    do {
                        winnerList.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("bet"), correctCount(), resultSet.getString("profile_picture"), resultSet.getInt("times_won")));
                    } while (resultSet.next());
                } else if (correctCount() == -99) {
                } else {
                    winnerList.add(new Winners(null, null, correctCount(), null, null));
                }
                connection.close();
            } catch (SQLException e) {

            }

            //log into sql database
//            String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
//            String dbUser = System.getenv("dbUser");
//            String dbPass = System.getenv("dbPass");
//            try {
//                //retrieve users with bets equal to or less than the correctCount() in order and save to list (closest to correct count @ index 0)
//                Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
//                Statement statement = connection.createStatement();
//                String sqlStatement = "SELECT full_name, profile_picture, bet, times_won FROM user_daily_bets JOIN users on user_daily_bets.user_id = users.user_id WHERE user_daily_bets.bet <= ? AND date = ? ORDER BY bet DESC;";
//                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
//                preparedStatement.setInt(1, correctCount());
//                preparedStatement.setDate(2, BettingDate.sqlPreviousBettingDate());
//                ResultSet resultSet = preparedStatement.executeQuery();
//
//                //add winners to list of winners
//                if (resultSet.next()) {
//                    do {
//                        winnerList.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("bet"), correctCount(), resultSet.getString("profile_picture"), resultSet.getInt("times_won")));
//                    } while (resultSet.next());
//                } else if (correctCount() == -99) {
//                } else {
//                    winnerList.add(new Winners(null, null, correctCount(), null, null));
//                }
//
//                connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
            return winnerList;
        }

        //return bad list without proper id in request
        winnerList.add(new Winners("You are not authorized", 404, -69, null, 0));
        return winnerList;
    }


    //returns correct count for previous betting day
    public int correctCount() {
        //log into sql database
        SQLRequest sqlRequest = new SQLRequest();
        int winningBet = -99;
        try {
            Connection connection = sqlRequest.getConnection();
            //retrieve winning bet for given date (date variable)
            Statement statement = connection.createStatement();
            String sqlStatement = "SELECT winning_bet FROM daily_winner WHERE date = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setDate(1, BettingDate.sqlPreviousBettingDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                winningBet = resultSet.getInt("winning_bet");
                if (winningBet == 0) {
                    winningBet = -99;
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return winningBet;
    }

}
