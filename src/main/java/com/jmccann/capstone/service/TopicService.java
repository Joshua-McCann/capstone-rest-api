package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.Topic;
import com.jmccann.capstone.domain.Pageable.TopicPage;
import com.jmccann.capstone.domain.TopicShort;
import com.jmccann.capstone.exceptions.BadRequestException;
import com.jmccann.capstone.repository.PostingRepo;
import com.jmccann.capstone.repository.TopicRepo;
import com.jmccann.capstone.repository.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@Log4j2
public class TopicService {

    private final TopicRepo topicRepo;
    private final PostingRepo postingRepo;
    private final UserRepo userRepo;

    @Autowired
    public TopicService(TopicRepo topicRepo, PostingRepo postingRepo, UserRepo userRepo) {
        this.topicRepo = topicRepo;
        this.postingRepo = postingRepo;
        this.userRepo = userRepo;
    }

    public TopicPage getTopics(int page, int perPage) {
        Pageable pageable = PageRequest.of(page, perPage, new Sort(Sort.Direction.DESC, "createDate"));
        Page<Topic> topics = topicRepo.findAll(pageable);
        return new TopicPage(topics);
    }

    public TopicShort saveTopic(Topic topic){
        if(topic.getTopicName().isEmpty() || topic.getTopicName() == null) throw new BadRequestException("Topic can not be submitted without a subject.");
        if(!userRepo.findById(topic.getUser().getId()).isPresent()) throw new BadRequestException("User credentials appear to be invalid");
        topic.setCreateDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));
        topic.getPostingList().get(0).setCreateDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));
        topicRepo.save(topic);
        topic.getPostingList().get(0).setTopic(topic);
        postingRepo.save(topic.getPostingList().get(0));
        TopicShort sTopic = new TopicShort();
        sTopic.setUser(topic.getUser());
        sTopic.setTopicName(topic.getTopicName());
        sTopic.setId(topic.getId());
        sTopic.setCreateDate(topic.getCreateDate());
        return sTopic;
    }
}
