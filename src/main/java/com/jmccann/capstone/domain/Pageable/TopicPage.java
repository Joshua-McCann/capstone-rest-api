package com.jmccann.capstone.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmccann.capstone.domain.Topic;
import com.jmccann.capstone.domain.TopicShort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class TopicPage extends ResourceSupport implements Page<TopicShort> {

    private final Page<Topic> topicPage;
    private final List<TopicShort> topicShortList;

    public TopicPage(Page<Topic> topicPage) {
        this.topicPage = topicPage;
        this.topicShortList = new ArrayList<>();
        topicPage.forEach(topic -> {
            TopicShort topicShort = new TopicShort();
            topicShort.setCreateDate(topic.getCreateDate());
            topicShort.setId(topic.getId());
            topicShort.setTopicName(topic.getTopicName());
            topicShort.setUser(topic.getUser());
            topicShortList.add(topicShort);
        });
        addPreviousLink();
        addNextLink();
        addFirstLink();
        addLastLink();
        addSelfLink();
    }

    private Link buildPageLink(int page, int size, String rel) {
        String path = createBuilder()
                .queryParam("page", page)
                .queryParam("perPage", size)
                .build()
                .toUriString();
        return new Link(path, rel);
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    private void addPreviousLink() {
        if(topicPage.hasPrevious()) {
            Link link = buildPageLink(topicPage.getNumber()-1,
                    topicPage.getSize(),
                    Link.REL_PREVIOUS);
            super.add(link);
        }
    }

    private void addNextLink() {
        if(topicPage.hasNext()) {
            Link link = buildPageLink(topicPage.getNumber()+1,
                    topicPage.getSize(),
                    Link.REL_NEXT);
            super.add(link);
        }
    }

    private void addFirstLink() {
        if(!topicPage.isFirst()) {
            Link link = buildPageLink(0,
                    topicPage.getSize(),
                    Link.REL_FIRST);
            super.add(link);
        }
    }

    private void addLastLink() {
        if(!topicPage.isLast()) {
            Link link = buildPageLink(topicPage.getTotalPages()-1,
                    topicPage.getSize(),
                    Link.REL_LAST);
            super.add(link);
        }
    }

    private void addSelfLink() {
        Link link = buildPageLink(topicPage.getNumber(),
                topicPage.getSize(),
                Link.REL_SELF);
        super.add(link);
    }

    @JsonIgnore
    @Override
    public int getTotalPages() {
        return topicPage.getTotalPages();
    }

    @JsonIgnore
    @Override
    public long getTotalElements() {
        return topicPage.getTotalElements();
    }

    @JsonIgnore
    @Override
    @NotNull
    public <U> Page<U> map(@NotNull Function<? super TopicShort, ? extends U> function) {
        return topicPage.map(function);
    }

    @JsonIgnore
    @Override
    public int getNumber() {
        return topicPage.getNumber();
    }

    @JsonIgnore
    @Override
    public int getSize() {
        return topicPage.getSize();
    }

    @JsonIgnore
    @Override
    public int getNumberOfElements() {
        return topicPage.getNumberOfElements();
    }

    @Override
    @NotNull
    public List<TopicShort> getContent() {
        return topicShortList;
    }

    @JsonIgnore
    @Override
    public boolean hasContent() {
        return topicPage.hasContent();
    }

    @JsonIgnore
    @Override
    @NotNull
    public Sort getSort() {
        return topicPage.getSort();
    }

    @JsonIgnore
    @Override
    public boolean isFirst() {
        return topicPage.isFirst();
    }

    @JsonIgnore
    @Override
    public boolean isLast() {
        return topicPage.isLast();
    }

    @JsonIgnore
    @Override
    public boolean hasNext() {
        return topicPage.hasNext();
    }

    @JsonIgnore
    @Override
    public boolean hasPrevious() {
        return topicPage.hasPrevious();
    }

    @JsonIgnore
    @Override
    @NotNull
    public Pageable nextPageable() {
        return topicPage.nextPageable();
    }

    @JsonIgnore
    @Override
    @NotNull
    public Pageable previousPageable() {
        return topicPage.previousPageable();
    }

    @Override
    @NotNull
    public Iterator<TopicShort> iterator() {
        return topicShortList.iterator();
    }

}
