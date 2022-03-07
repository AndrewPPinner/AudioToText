package com.audioreader.AudioReader.PageDisplay;

import com.audioreader.AudioReader.SQLRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;

@Controller
public class AdminPage {
    private String url;


    @RequestMapping("/admin_page")
    public String adminPage(@RequestParam(value = "key") String key){
        SQLRequest sqlRequest = new SQLRequest();
        //check if secure key from front-end is same as the one on the date base

        try {
            Connection connection = sqlRequest.getConnection();
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
