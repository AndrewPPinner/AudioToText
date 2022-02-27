package com.audioreader.AudioReader.PostRequest;


import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;
import java.util.Map;

@RestController
public class AddBet {
    private String fullName;
    private int bet;



//This should be a post request
    @PostMapping("/daily_bet")
    public String addBet(@RequestBody List<String> parameters) {
        //domain.com/user_betting?parameters=<username>,<bet>
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        try {
            boolean userExists = false;
            int numUsersAdded = 0;
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();

            //get list of all previous users
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while(resultSet.next()){
                //if users already exists, check to see if they made any bets for today

                if(resultSet.getString("full_name").equalsIgnoreCase(parameters.get(0))) {
                    userExists = true;
                    String sqlStatement = "SELECT users.user_id, bet, date, full_name FROM user_daily_bets JOIN users on user_daily_bets.user_id = users.user_id WHERE date = current_date AND full_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                    preparedStatement.setString(1, parameters.get(0));
                    ResultSet matchingBetSet = preparedStatement.executeQuery();
                    //if user has a bet recorded for current day, update the bet
                    if (matchingBetSet.next()) {
                        int numBetsUpdated = updateBet(parameters.get(0), Integer.parseInt(parameters.get(1)));
                        return "Your bet has been updated! (" + numBetsUpdated + ") bets updated.";
                    }
                }
            }

            //if no matching users found, add user to user table before adding bet
            if(!userExists) {
                String sqlStatement = "INSERT INTO users (full_name) VALUES (?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setString(1, parameters.get(0));
                numUsersAdded = preparedStatement.executeUpdate();
            }

            //user has been added or already exists, but doesn't have a bet registered for today
            //register a new bet for the user
            String sqlStatement = "INSERT INTO user_daily_bets (user_id, bet, date) VALUES ((SELECT user_id FROM users WHERE full_name = ?),?,current_date);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, parameters.get(0));
            preparedStatement.setInt(2, Integer.parseInt(parameters.get(1)));
            int numBetsAdded = preparedStatement.executeUpdate();

//      from older version of method
//            resultSet = statement.executeQuery("SELECT * FROM users");
//            while(resultSet.next()){
//                this.fullName = resultSet.getString("full_name");
//                this.bet = resultSet.getInt("daily_bet");
//            }

            connection.close();
            return "Your bet has been placed! (" + numUsersAdded + ") users added, (" + numBetsAdded + ") bets added.";
        } catch (SQLException e) {
            System.out.println("Connection to Date base has failed");
            e.printStackTrace();
            return "There has been an error. Please try again later";
        }

    }



    //update bet for an existing user(only accessed through addBet which checks user/bet exist already)
    public int updateBet(String fullName, int bet) {
        System.out.println("updating bet");
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        int betsUpdated = 0;
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            String sqlStatement = "UPDATE user_daily_bets SET bet = ? where user_id = (SELECT user_id FROM users WHERE full_name = ?) and date = current_date;";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, bet);
            preparedStatement.setString(2, fullName);
            betsUpdated = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return betsUpdated;
    }

}
