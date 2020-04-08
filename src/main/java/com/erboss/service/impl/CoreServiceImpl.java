package com.erboss.service.impl;

import com.erboss.domain.CoreResp;
import com.erboss.service.CoreService;
import com.erboss.service.PoetryService;
import com.erboss.service.StoryService;
import com.erboss.service.TodayHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangyongan on 2020-04-05.
 */
@Service
public class CoreServiceImpl implements CoreService {

    @Autowired
    private PoetryService poetryService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private TodayHistory todayHistory;

    @Override
    public CoreResp getData(int id){
        CoreResp coreResp = new CoreResp();
        coreResp.setPoetry(poetryService.getRandom());
        coreResp.setStory(storyService.crawStory(id));
        coreResp.setTodayHistory(todayHistory.todayHistory(id));
        return coreResp;
    }
}
