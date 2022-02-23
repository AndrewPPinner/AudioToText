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
        if(id.equals("123456789")) {


            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatDateTime = now.format(format);
            Date date= Date.valueOf(formatDateTime);
            String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
            String dbUser = System.getenv("dbUser");
            String dbPass = System.getenv("dbPass");
            try {
                Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);

                Statement statement = connection.createStatement();
                String sqlStatement = "SELECT full_name, daily_bet, profile_picture FROM users WHERE daily_bet = (SELECT winning_bet FROM daily_winner WHERE date = ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setDate(1, date);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    winnerList.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("daily_bet"), resultSet.getString("profile_picture")));
                }


                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return winnerList;
        }
        winnerList.add(new Winners("You are not authorized", 404, null));
        return winnerList;


    }
}
