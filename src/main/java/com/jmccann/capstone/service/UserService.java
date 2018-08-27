package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.Role;
import com.jmccann.capstone.domain.User;
import com.jmccann.capstone.exceptions.BadRequestException;
import com.jmccann.capstone.repository.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Log4j2
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User findUser(String username) {
        return userRepo.findByUsername(username);
    }

    public User createUser(User user){
        if(user.getUsername() == null || user.getPassword() == null || user.getEmail() == null) throw new BadRequestException("You cannot register with these values.");
        if(!user.getEmail().matches("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")) throw new BadRequestException("Email address does not appear valid");
        if(userRepo.findByUsername(user.getUsername()) != null) throw new BadRequestException("Username is already registered");
        if(userRepo.findByEmail(user.getEmail().toUpperCase()) != null) throw new BadRequestException("Email address is already registered");
        user.setEmail(user.getEmail().toUpperCase());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnabled(true);
        user.setJoinDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));
        user.setRole(Role.USER.name());
        return userRepo.save(user);
    }
}
