package com.audioreader.AudioReader.TextReader;

import com.audioreader.AudioReader.GetRequest.WinnerRequest;
import com.audioreader.AudioReader.GetRequest.Winners;
import com.audioreader.AudioReader.SQLRequest;

import java.sql.*;
import java.util.List;

public class SetWinningBet {
    private Date date;
    private String word;
    private int count;

    public SetWinningBet(Date date, String word, int count) {
        this.date = date;
        this.word = word;
        this.count = count;
    }


    public void setWinningDailyBet() {
        WinnerRequest winnerRequest = new WinnerRequest();
        SQLRequest sqlRequest = new SQLRequest();
        try {
//checks if there is already a winning bet for the date. If so update it, if not insert a new one. (This is mostly for the time being while we manually update it, so we don't break the db)
//week 1 is currently hard coded and needs logic built to see what week we are in
            Connection connection = sqlRequest.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM daily_winner");

            if (resultSet.next()) {
                do {
                    if (resultSet.getDate("date").equals(this.date)) {
                        String sql = "UPDATE daily_winner SET week_id = 1, winning_bet = ?, date = current_date WHERE date = current_date";
                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, count);
                        preparedStatement.executeUpdate();
                    }
                } while (resultSet.next());
            } else {
                String sqlStatement = "INSERT INTO daily_winner (week_id, winning_bet, date) VALUES (1, ?, current_date);";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setInt(1, count);
                preparedStatement.executeUpdate();
            }

//updates everyone's times won with the correct winning bet to +1
            List<Winners> winnersList = winnerRequest.dailyWinners("123456789");
            for (Winners winner : winnersList) {
                String sqlStatement = "UPDATE users SET times_won = times_won + 1 WHERE full_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
                preparedStatement.setString(1, winner.getName());
                int rowsChanged = preparedStatement.executeUpdate();
            }



            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection to Date base has failed");
            e.printStackTrace();
        }
    }

}
