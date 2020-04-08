package com.erboss.service;

import com.erboss.domain.Poetry;

/**
 * Created by wangyongan on 2020-04-07.
 * Email wangyongan@xueqiu.com
 */
public interface PoetryService {

    /**
     * 随机返回一首诗
     * @return
     */
    Poetry getRandom();

    /**
     * 抓取
     * @return
     */
    String crawPoetry();
}
