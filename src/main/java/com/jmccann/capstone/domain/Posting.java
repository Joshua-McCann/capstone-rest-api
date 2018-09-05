package com.jmccann.capstone.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Posting extends PostingShort {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "topicId")
    private Topic topic;

}
