package com.jmccann.capstone.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PostingPage implements Page<PostingShort> {

    private Page<Posting> postingPage;
    private List<PostingShort> postingShortList;

    public PostingPage(Page<Posting> postingPage) {
        this.postingPage = postingPage;
        postingShortList = new ArrayList<>();
        postingPage.forEach(posting -> {
            PostingShort postingShort = new PostingShort();
            postingShort.setCreateDate(posting.getCreateDate());
            postingShort.setId(posting.getId());
            postingShort.setMessage(posting.getMessage());
            postingShort.setUser(posting.getUser());
            postingShortList.add(postingShort);
        });
    }

    @Override
    public int getTotalPages() {
        return postingPage.getTotalPages();
    }

    @Override
    public long getTotalElements() {
        return postingPage.getTotalElements();
    }

    @Override
    public <U> Page<U> map(Function<? super PostingShort, ? extends U> function) {
        return postingPage.map(function);
    }

    @Override
    public int getNumber() {
        return postingPage.getNumber();
    }

    @Override
    public int getSize() {
        return postingPage.getSize();
    }

    @Override
    public int getNumberOfElements() {
        return postingPage.getNumberOfElements();
    }

    @Override
    public List<PostingShort> getContent() {
        return postingShortList;
    }

    @Override
    public boolean hasContent() {
        return postingPage.hasContent();
    }

    @Override
    public Sort getSort() {
        return postingPage.getSort();
    }

    @Override
    public boolean isFirst() {
        return postingPage.isFirst();
    }

    @Override
    public boolean isLast() {
        return postingPage.isLast();
    }

    @Override
    public boolean hasNext() {
        return postingPage.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return postingPage.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return postingPage.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return postingPage.previousPageable();
    }

    @Override
    public Iterator<PostingShort> iterator() {
        return postingShortList.iterator();
    }
}
