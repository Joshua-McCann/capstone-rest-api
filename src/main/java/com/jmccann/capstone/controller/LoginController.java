package com.jmccann.capstone.controller;

import com.jmccann.capstone.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    public LoginController(){}

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return user.getUsername();
    }
}
