package com.audioreader.AudioReader;

import com.audioreader.AudioReader.GetRequest.Winners;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLRequest {




    public Connection getConnection() throws SQLException {
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        return DriverManager.getConnection(dbURL, dbUser, dbPass);
    }



    public ResultSet queryResults(String sql) {
        ResultSet resultSet = null;
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = System.getenv("dbUser");
        String dbPass = System.getenv("dbPass");
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            connection.close();
        } catch (SQLException e) {
        }
    return resultSet;
    }
}
