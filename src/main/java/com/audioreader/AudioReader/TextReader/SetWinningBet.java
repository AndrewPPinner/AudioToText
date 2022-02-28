package com.audioreader.AudioReader.TextReader;

import java.sql.*;

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
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        try {
//checks if there is already a winning bet for the date. If so update it, if not insert a new one. (This is mostly for the time being while we manually update it, so we don't break the db)
//week 1 is currently hard coded and needs logic built to see what week we are in
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
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
            String sqlStatement = "UPDATE users SET times_won = times_won + 1 WHERE user_id IN (SELECT user_id FROM user_daily_bets WHERE user_daily_bets.bet = ? AND date = ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, count);
            preparedStatement.setDate(2, date);
            int rowsChanged = preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection to Date base has failed");
            e.printStackTrace();
        }
    }

}
