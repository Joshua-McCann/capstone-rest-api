package com.jmccann.capstone.controller;

import com.jmccann.capstone.config.View;
import com.jmccann.capstone.domain.User;
import com.jmccann.capstone.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @JsonView(View.External.class)
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return "OK";
    }
}
