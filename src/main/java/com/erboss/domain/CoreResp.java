package com.erboss.domain;

import java.util.List;

/**
 * Created by wangyongan on 2020-04-05.
 */
public class CoreResp {
    Poetry poetry;

    List<String> todayHistory;

    Story story;

    public Poetry getPoetry() {
        return poetry;
    }

    public void setPoetry(Poetry poetry) {
        this.poetry = poetry;
    }

    public List<String> getTodayHistory() {
        return todayHistory;
    }

    public void setTodayHistory(List<String> todayHistory) {
        this.todayHistory = todayHistory;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
