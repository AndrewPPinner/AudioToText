package com.audioreader.AudioReader.PostRequest;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class validCredentials {


//should be a post request
        @PostMapping("/login_creds")
        public dynamicObject validateCred(@RequestBody List<String> loginCred) {

            //passing down randomized endpoint to front end to make secure connection admin page
            if(loginCred.get(0).equals("Admin") && loginCred.get(1).equals("123456789")) {
                return new dynamicObject(true);
            }
            return new dynamicObject(false);
        }
}
