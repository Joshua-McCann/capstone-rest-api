package com.jmccann.capstone.repository;

import com.jmccann.capstone.domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TopicRepo extends JpaRepository<Topic, UUID> {
}
