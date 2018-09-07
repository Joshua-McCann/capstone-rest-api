package com.jmccann.capstone.service;

import com.jmccann.capstone.domain.Posting;
import com.jmccann.capstone.domain.Pageable.PostingPage;
import com.jmccann.capstone.domain.PostingShort;
import com.jmccann.capstone.exceptions.BadRequestException;
import com.jmccann.capstone.repository.PostingRepo;
import com.jmccann.capstone.repository.TopicRepo;
import com.jmccann.capstone.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class PostingService {

    private final PostingRepo postingRepo;
    private final TopicRepo topicRepo;
    private final UserRepo userRepo;

    @Autowired
    public PostingService(PostingRepo postingRepo, TopicRepo topicRepo, UserRepo userRepo) {
        this.postingRepo = postingRepo;
        this.topicRepo = topicRepo;
        this.userRepo = userRepo;
    }

    public PostingPage getPostings(int page, int perPage, UUID topicId) {
        Pageable pageable = PageRequest.of(page, perPage, new Sort(Sort.Direction.ASC, "createDate"));
        Page<Posting> postings = postingRepo.findAllByTopic(topicRepo.getOne(topicId), pageable);
        return new PostingPage(postings, topicId);
    }

    public PostingShort savePosting(Posting posting, UUID topicId) {
        if(!topicRepo.findById(topicId).isPresent()) throw new BadRequestException("Topic Id appears to be invalid.");
        if(!userRepo.findById(posting.getUser().getId()).isPresent()) throw new BadRequestException("User Credentials appear to be invalid");
        posting.setTopic(this.topicRepo.getOne(topicId));
        posting.setCreateDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(0))));
        postingRepo.save(posting);
        PostingShort pShort = new PostingShort();
        pShort.setMessage(posting.getMessage());
        pShort.setCreateDate(posting.getCreateDate());
        pShort.setId(posting.getId());
        pShort.setUser(posting.getUser());
        return pShort;
    }
}
