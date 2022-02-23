package com.audioreader.AudioReader.PageDisplay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminLogin {

//renders and presents admin login page
    @RequestMapping("/admin_login")
    public String adminLogin(){
        return "adminLogin/admin_login.html";
    }

}
