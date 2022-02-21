package com.audioreader.AudioReader;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.List;
import java.util.Map;

@RestController
public class AddBet {
    private String fullName;
    private int bet;




    @GetMapping("/daily_bet")
    public String addBet(@RequestParam List<String> parameters) {
        //domain.com/user_betting?parameters=<username>,<bet>
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = "postgres";
        String dbPass = "postgres1";
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            String sqlStatement = "INSERT INTO users (full_name, bet) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, parameters.get(0));
            preparedStatement.setInt(2, Integer.parseInt(parameters.get(1)));
            int rowsAdded = preparedStatement.executeUpdate();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
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
        return fullName + " : " + bet;
    }

}
