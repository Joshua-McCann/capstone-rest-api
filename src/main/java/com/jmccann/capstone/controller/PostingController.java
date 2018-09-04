package com.jmccann.capstone.controller;

import com.jmccann.capstone.domain.Posting;
import com.jmccann.capstone.domain.Pageable.PostingPage;
import com.jmccann.capstone.domain.PostingShort;
import com.jmccann.capstone.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping()
    public PostingShort savePosting(@RequestParam UUID topicId, @RequestBody Posting posting) {
        return postingService.savePosting(posting, topicId);
    }
}
