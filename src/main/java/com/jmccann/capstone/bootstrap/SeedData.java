package com.jmccann.capstone.bootstrap;

import com.jmccann.capstone.domain.*;
import com.jmccann.capstone.repository.PostingRepo;
import com.jmccann.capstone.repository.TopicRepo;
import com.jmccann.capstone.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Profile("seed")
@Component
public class SeedData {

    private final UserRepo userRepo;
    private final TopicRepo topicRepo;
    private final PostingRepo postingRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public SeedData(UserRepo userRepo, TopicRepo topicRepo, PostingRepo postingRepo){
        this.userRepo = userRepo;
        this.topicRepo = topicRepo;
        this.postingRepo = postingRepo;
        addUsers();
    }

    private void addUsers(){
        User user = new User();
        user.setUsername("McCanicle");
        user.setPassword(passwordEncoder.encode("TempPassword"));
        user.setEmail("jmcsquiggle@gmail.com");
        user.setEnabled(true);
        user.setJoinDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));
        user.setRole(Role.ADMIN.name());

        userRepo.save(user);
        addTopics(user);
    }

    private void addTopics(User user) {
        Topic topic;
        for(int i = 1; i < 101; i++){
            System.out.println("saving " + i);
            topic = new Topic();
            topic.setTopicName(String.format("Test Topic #%d", i));
            topic.setUser(user);
            topic.setCreateDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));

            topicRepo.save(topic);

            getFivePosts(topic, user);
        }
    }

    private void getFivePosts(Topic topic, User user) {
        Posting posting;

        for(int i = 1; i < 6; i++) {
            posting = new Posting();
            posting.setMessage(String.format("%s, posting %d", topic.getTopicName(), i));
            posting.setUser(user);
            posting.setCreateDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));
            posting.setTopic(topic);
            postingRepo.save(posting);
        }
    }
}
