package com.jmccann.capstone.controller;

import com.jmccann.capstone.domain.PasswordUpdate;
import com.jmccann.capstone.domain.User;
import com.jmccann.capstone.service.TokenAuthenticationService;
import com.jmccann.capstone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public User getOne(@RequestParam()String username) {
        return userService.findUser(username);
    }

    @PutMapping()
    public User updateOne(@RequestParam()UUID userId, @RequestBody() PasswordUpdate passwordUpdate) {
        return userService.updateUser(userId, passwordUpdate);
    }

    @PostMapping()
    public User createUser(@RequestBody()User user, HttpServletResponse res){
        userService.createUser(user);
        TokenAuthenticationService.addAuthentication(res, user);
        return user;
    }
}
