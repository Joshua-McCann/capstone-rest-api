package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.Posting;
import com.jmccann.capstone.domain.PostingPage;
import com.jmccann.capstone.repository.PostingRepo;
import com.jmccann.capstone.repository.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostingService {

    private final PostingRepo postingRepo;
    private final TopicRepo topicRepo;

    @Autowired
    public PostingService(PostingRepo postingRepo, TopicRepo topicRepo) {
        this.postingRepo = postingRepo;
        this.topicRepo = topicRepo;
    }

    public PostingPage getPostings(int page, int perPage, UUID topicId) {
        Pageable pageable = PageRequest.of(page, perPage, new Sort(Sort.Direction.ASC, "createDate"));
        Page<Posting> postings = postingRepo.findAllByTopic(topicRepo.getOne(topicId), pageable);
        PostingPage postingPage = new PostingPage(postings);
        return postingPage;
    }
}
