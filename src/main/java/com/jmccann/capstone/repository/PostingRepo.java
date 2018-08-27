package com.jmccann.capstone.repository;

import com.jmccann.capstone.domain.Posting;
import com.jmccann.capstone.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostingRepo extends JpaRepository<Posting, UUID> {

    Page<Posting> findAllByTopic(Topic topic, Pageable page);
}
