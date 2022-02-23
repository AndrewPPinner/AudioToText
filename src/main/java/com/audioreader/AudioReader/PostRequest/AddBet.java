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

            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()){
                if(resultSet.getString("full_name").equalsIgnoreCase(parameters.get(0))) {
                    updateBet(parameters.get(0), Integer.parseInt(parameters.get(1)));
                    return "Your bet has been updated!";
                }
            }

            String sqlStatement = "INSERT INTO users (full_name, daily_bet) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, parameters.get(0));
            preparedStatement.setInt(2, Integer.parseInt(parameters.get(1)));
            int rowsAdded = preparedStatement.executeUpdate();



            resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()){
                this.fullName = resultSet.getString("full_name");
                this.bet = resultSet.getInt("daily_bet");
            }


            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection to Date base has failed");
            e.printStackTrace();
            return "There has been an error. Please try again later";
        }
        return "Your bet has been placed!";
    }

    public void updateBet(String fullName, int bet) {
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");

        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            String sqlStatement = "UPDATE users SET daily_bet = ? where full_name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, bet);
            preparedStatement.setString(2, fullName);
            int rowsAdded = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
