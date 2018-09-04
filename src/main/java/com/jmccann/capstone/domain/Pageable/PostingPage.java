package com.jmccann.capstone.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmccann.capstone.domain.Posting;
import com.jmccann.capstone.domain.PostingShort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class PostingPage extends ResourceSupport implements Page<PostingShort> {

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
        Link link = new Link(path, rel);
        return link;
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    private void addPreviousLink() {
        if(postingPage.hasPrevious()) {
            Link link = buildPageLink(postingPage.getNumber()-1,
                    postingPage.getSize(),
                    Link.REL_PREVIOUS);
            super.add(link);
        }
    }

    private void addNextLink() {
        if(postingPage.hasNext()) {
            Link link = buildPageLink(postingPage.getNumber()+1,
                    postingPage.getSize(),
                    Link.REL_NEXT);
            super.add(link);
        }
    }

    private void addFirstLink() {
        if(!postingPage.isFirst()) {
            Link link = buildPageLink(0,
                    postingPage.getSize(),
                    Link.REL_FIRST);
            super.add(link);
        }
    }

    private void addLastLink() {
        if(!postingPage.isLast()) {
            Link link = buildPageLink(postingPage.getTotalPages()-1,
                    postingPage.getSize(),
                    Link.REL_LAST);
            super.add(link);
        }
    }

    private void addSelfLink() {
        Link link = buildPageLink(postingPage.getNumber(),
                    postingPage.getSize(),
                    Link.REL_SELF);
        super.add(link);
    }

    @JsonIgnore
    @Override
    public int getTotalPages() {
        return postingPage.getTotalPages();
    }

    @JsonIgnore
    @Override
    public long getTotalElements() {
        return postingPage.getTotalElements();
    }

    @JsonIgnore
    @Override
    public <U> Page<U> map(Function<? super PostingShort, ? extends U> function) {
        return postingPage.map(function);
    }

    @JsonIgnore
    @Override
    public int getNumber() {
        return postingPage.getNumber();
    }

    @JsonIgnore
    @Override
    public int getSize() {
        return postingPage.getSize();
    }

    @JsonIgnore
    @Override
    public int getNumberOfElements() {
        return postingPage.getNumberOfElements();
    }

    @Override
    public List<PostingShort> getContent() {
        return postingShortList;
    }

    @JsonIgnore
    @Override
    public boolean hasContent() {
        return postingPage.hasContent();
    }

    @JsonIgnore
    @Override
    public Sort getSort() {
        return postingPage.getSort();
    }

    @JsonIgnore
    @Override
    public boolean isFirst() {
        return postingPage.isFirst();
    }

    @JsonIgnore
    @Override
    public boolean isLast() {
        return postingPage.isLast();
    }

    @JsonIgnore
    @Override
    public boolean hasNext() {
        return postingPage.hasNext();
    }

    @JsonIgnore
    @Override
    public boolean hasPrevious() {
        return postingPage.hasPrevious();
    }

    @JsonIgnore
    @Override
    public Pageable nextPageable() {
        return postingPage.nextPageable();
    }

    @JsonIgnore
    @Override
    public Pageable previousPageable() {
        return postingPage.previousPageable();
    }

    @Override
    public Iterator<PostingShort> iterator() {
        return postingShortList.iterator();
    }
}
