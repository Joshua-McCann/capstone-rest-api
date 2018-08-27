package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.Topic;
import com.jmccann.capstone.domain.TopicPage;
import com.jmccann.capstone.repository.TopicRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class TopicService {

    private final TopicRepo topicRepo;

    @Autowired
    public TopicService(TopicRepo topicRepo) { this.topicRepo = topicRepo; }

    public TopicPage getTopics(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, new Sort(Sort.Direction.DESC, "createDate"));
        Page<Topic> topics = topicRepo.findAll(pageable);
        TopicPage topicPage = new TopicPage(topics);
        return topicPage;
    }
}
