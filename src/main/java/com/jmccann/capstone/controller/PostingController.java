package com.jmccann.capstone.controller;

import com.jmccann.capstone.domain.PostingPage;
import com.jmccann.capstone.domain.TopicPage;
import com.jmccann.capstone.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostingController {

    private final PostingService postingService;

    @Autowired
    public PostingController(PostingService postingService) { this.postingService = postingService; }

    @GetMapping()
    public PostingPage getPostings(@RequestParam int page, @RequestParam int perPage, @RequestParam UUID topicId){
        return postingService.getPostings(page, perPage, topicId);
    }
}
