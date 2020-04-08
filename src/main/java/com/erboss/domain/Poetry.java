package com.erboss.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Created by wangyongan on 2020-04-05.
 */
@EntityScan
public class Poetry {

    private long id;

    private String content;

    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
