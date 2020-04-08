package com.erboss.domain;

import java.util.List;

/**
 * Created by wangyongan on 2020-04-05.
 */
public class Story {
    String title;

    String briefIntroduction;

    List<StoryGraph> storyGraphs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    public List<StoryGraph> getStoryGraphs() {
        return storyGraphs;
    }

    public void setStoryGraphs(List<StoryGraph> storyGraphs) {
        this.storyGraphs = storyGraphs;
    }
}
