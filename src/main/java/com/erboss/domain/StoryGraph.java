package com.erboss.domain;

import java.util.List;

/**
 * Created by wangyongan on 2020-04-05.
 */
public class StoryGraph {
    private String title;

    private List<String> graph;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGraph() {
        return graph;
    }

    public void setGraph(List<String> graph) {
        this.graph = graph;
    }
}
