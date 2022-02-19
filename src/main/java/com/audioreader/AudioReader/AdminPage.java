package com.audioreader.AudioReader;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class AdminPage {
    private String url;

    @RequestMapping("/admin_page")
    public String adminPage(@RequestParam(value = "key") String key){
        //check if secure key from front-end is same as the one on the date base

        String dbURL = "jdbc:postgresql://localhost:5432/BettingDB";
        String dbUser = "postgres";
        String dbPass = "postgres1";
        try {
            Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPass);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM password");
            while(resultSet.next()){
               this.url = resultSet.getString("pass");
            }
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection to Date base has failed");
            e.printStackTrace();
            return "There has been an error. Please try again later";
        }

        System.out.println(url);
        System.out.println(key);
        //if the secure key and url are same allow user to go to admin page
        if(key.equals(url)) {
            return "adminPage/admin_page.html";
        } else {
            return "failed_login.html";
        }
    }
}
