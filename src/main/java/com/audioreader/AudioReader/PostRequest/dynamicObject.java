package com.audioreader.AudioReader.PostRequest;

import com.audioreader.AudioReader.SQLRequest;

import java.nio.charset.Charset;
import java.sql.*;
import java.util.Random;

public class dynamicObject {
    private String url;
    private boolean valid;

    dynamicObject(boolean valid) {
        this.valid = valid;
        generateUrl();

    }

    private void generateUrl() {
        SQLRequest sqlRequest = new SQLRequest();

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

        try {
            Connection connection = sqlRequest.getConnection();
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
        if(valid) {
            return url;
        }
        return "";
    }

    public boolean isValid() {
        return valid;
    }
}
