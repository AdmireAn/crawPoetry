package com.erboss.controller;

import com.erboss.service.CrawPaixi;
import com.erboss.service.PoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangyongan on 2020-04-05.
 */
@RestController
public class CrawController {
    @Autowired
    private PoetryService poetryService;

    @Autowired
    private CrawPaixi crawPaixi;

    @RequestMapping(path = {"/poetry/craw"})
    public void crawPoetry() {
        poetryService.crawPoetry();
    }

    @RequestMapping(path = {"/paixi/craw"})
    public String crawPaixi() {
        return crawPaixi.crawPaixi();
    }
}
