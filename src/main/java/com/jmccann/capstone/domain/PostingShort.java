package com.jmccann.capstone.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jmccann.capstone.config.View;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public class PostingShort {

    @Id
    @Column(name = "postId")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userId")
    @JsonView(View.External.class)
    private User user;

    String message;

    @JsonFormat(pattern = "MM-dd-YYYY")
    Date createDate;
}
