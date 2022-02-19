package com.audioreader.AudioReader;

import java.nio.charset.Charset;
import java.sql.*;
import java.util.Random;

public class dynamicObject {
    private String url;
    private boolean valid;

    dynamicObject(boolean valid) {
        this.valid = valid;
        if(valid) {
            generateUrl();
        }
    }

    private void generateUrl() {

        //generating randomized endpoint
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        this.url = generatedString;

        //passing the randomized endpoint to the db
        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = "postgres";
        String dbPass = "postgres1";
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            String sqlStatement = "UPDATE password SET pass = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setString(1, url);
            Statement statement = connection.createStatement();
            int rowsAdded = preparedStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection to Date base has failed");
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public boolean isValid() {
        return valid;
    }
}
