package com.jmccann.capstone.repository;

import com.jmccann.capstone.domain.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QPageRequest;

import java.util.UUID;

public interface TopicRepo extends JpaRepository<Topic, UUID> {
}
