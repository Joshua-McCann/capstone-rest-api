package com.jmccann.capstone.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class TopicPage implements Page<TopicShort> {

    private Page<Topic> topicPage;
    private List<TopicShort> topicShortList;

    public TopicPage(Page<Topic> topicPage) {
        this.topicPage = topicPage;
        topicShortList = new ArrayList<>();
        topicPage.forEach(topic -> {
            TopicShort topicShort = new TopicShort();
            topicShort.setCreateDate(topic.getCreateDate());
            topicShort.setId(topic.getId());
            topicShort.setTopicName(topic.getTopicName());
            topicShort.setUser(topic.getUser());
            topicShortList.add(topicShort);
        });
    }

    @Override
    public int getTotalPages() {
        return topicPage.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return topicPage.getTotalElements();
    }

    @Override
    public <U> Page<U> map(Function<? super TopicShort, ? extends U> function) {
        return topicPage.map(function);
    }

    @Override
    public int getNumber() {
        return topicPage.getNumber();
    }

    @Override
    public int getSize() {
        return topicPage.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return topicPage.getNumberOfElements();
    }

    @Override
    public List<TopicShort> getContent() {
        return topicShortList;
    }

    @Override
    public boolean hasContent() {
        return topicPage.hasContent();
    }

    @Override
    public Sort getSort() {
        return topicPage.getSort();
    }

    @Override
    public boolean isFirst() {
        return topicPage.isFirst();
    }

    @Override
    public boolean isLast() {
        return topicPage.isLast();
    }

    @Override
    public boolean hasNext() {
        return topicPage.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return topicPage.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return topicPage.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return topicPage.previousPageable();
    }

    @Override
    public Iterator<TopicShort> iterator() {
        return topicShortList.iterator();
    }
}
