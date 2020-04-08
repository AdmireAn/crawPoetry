package com.erboss.service.impl;

import com.erboss.Constant;
import com.erboss.service.TodayHistory;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

/**
 * Created by wangyongan on 2020-04-07.
 */
@Service
public class TodayHistoryImpl implements TodayHistory {
    @Override
    public List<String> todayHistory(int id) {
        List<String> result = Lists.newArrayList();
        int start = 96;
        String dayday = null;
        String url = String.format("%s%s%s", Constant.TODAY_URL, start + id, ".html");
        try {
            Document document = Jsoup.parse(new URL(url), 30000);
            Elements elements = document.getElementsByClass("f14 l150").get(0).getElementsByTag("a");
            int index = 0;
            while (index < elements.size() - 1) {
                String year = elements.get(index).text();
                index++;
                dayday = elements.get(index).text();
                index++;
                String event = elements.get(index).text();
                index++;
                result.add(year + " " + event);
            }
        } catch (Exception e) {

        }
        result.add(dayday);
        return result;
    }
}
