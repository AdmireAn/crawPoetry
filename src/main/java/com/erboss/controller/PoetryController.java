package com.erboss.controller;

import com.erboss.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyongan on 2020-04-05.
 */
@RestController
public class PoetryController {
    @Autowired
    private PoetryService poetryService;

    @RequestMapping(path = {"/poetry/craw"})
    public void crawPoetry() {
        poetryService.crawPoetry();
    }
}
