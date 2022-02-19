package com.audioreader.AudioReader;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class validCredentials {



        @GetMapping("/login_creds")
        public dynamicObject validateCred(@RequestParam(value = "login") String login, @RequestParam(value = "password") String pass) {

            //passing down randomized endpoint to front end to make secure connection admin page
            if(login.equals("Admin") && pass.equals("123456789")) {
                return new dynamicObject(true);
            }
            return new dynamicObject(false);
        }
}
