package com.jmccann.capstone.controller;

import com.jmccann.capstone.domain.TopicPage;
import com.jmccann.capstone.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) { this.topicService = topicService;
    }

    @GetMapping()
    public TopicPage getTopics(@RequestParam int page, @RequestParam int perPage){
        return topicService.getTopics(page, perPage);
    }
}
