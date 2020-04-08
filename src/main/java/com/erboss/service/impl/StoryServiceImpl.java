package com.erboss.service.impl;

import com.erboss.Constant;
import com.erboss.domain.Story;
import com.erboss.domain.StoryGraph;
import com.erboss.service.StoryService;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

/**
 * Created by wangyongan on 2020-04-05.
 * Email wangyongan@xueqiu.com
 */
@Service
public class StoryServiceImpl implements StoryService {

    @Override
    public Story crawStory(int i) {
        List<String> includeGrapg = Lists.newArrayList("原典", "注释", "释义");
        boolean hasStory = false;
        Story story = new Story();
        int first = 1602;
        String url = String.format("%s%s%s", Constant.URL_36ji, first - i, ".html");
        Document document = null;
        List<StoryGraph> storyGraphs = Lists.newArrayList();
        String briefIntroduction = null;
        StoryGraph storyGraph = null;
        List<String> graphs = null;
        try {
            document = Jsoup.parse(new URL(url), 30000);
            Elements titleElements = document.getElementsByClass("title");
            String title = titleElements.get(1).getElementsByTag("h2").text();
            Elements elements = document.getElementsByClass("content").get(0).getElementsByTag("p");

            for (Element element : elements) {
                String text = element.text().replace("　", "").replace(" ","");

                if (StringUtils.isBlank(text)) {
                    continue;
                }

                if (!StringUtils.isBlank(text) && StringUtils.isBlank(briefIntroduction)) {
                    briefIntroduction = text;
                    continue;
                }
                if (text.contains("】")) {
                    if (storyGraph != null) {
                        storyGraph.setGraph(graphs);
                        storyGraphs.add(storyGraph);
                    }
                    storyGraph = new StoryGraph();
                    graphs = Lists.newArrayList();
                    storyGraph.setTitle(text.replace("【", "").replace("】", "").replace(" ", ""));
                    continue;
                }
                graphs.add(text);
            }

            storyGraph.setGraph(graphs);
            storyGraphs.add(storyGraph);

            List<StoryGraph> storyGraphsResult = Lists.newArrayList();
            for (StoryGraph graph : storyGraphs) {
                if((graph.getTitle().contains("故事")) && !hasStory){
                    hasStory = true;
                    storyGraphsResult.add(graph);
                }else {
                    if(includeGrapg.contains(graph.getTitle())) {
                        storyGraphsResult.add(graph);
                    }
                }
            }
            story.setTitle("[三十六计]"+title);
            story.setBriefIntroduction(briefIntroduction);
            story.setStoryGraphs(storyGraphsResult);
        } catch (Exception e) {
            System.out.println(e);
        }

        return story;
    }
}
