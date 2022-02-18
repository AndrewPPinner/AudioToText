package com.audioreader.AudioReader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AdminPanel {
    private List<Winners> winnerList;





    @GetMapping("/admin_panel/daily")
    public List<Winners> dailyWinners(@RequestParam(value = "id") String id) {
        winnerList = new ArrayList<>();
        if(id.equals("123456789")) {


            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MM_dd_yyyy");
            String formatDateTime = now.format(format);
            String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
            String dbUser = "postgres";
            String dbPass = "postgres1";
            try {
                Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("SELECT full_name, daily_bet FROM users WHERE daily_bet = (SELECT winning_bet FROM daily_winner WHERE date = '" + formatDateTime + "')");
                while (resultSet.next()) {
                    winnerList.add(new Winners(resultSet.getString("full_name"), resultSet.getInt("daily_bet")));
                }


                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return winnerList;
        }
        winnerList.add(new Winners("You are not authorized", 404));
        return winnerList;


    }
}
