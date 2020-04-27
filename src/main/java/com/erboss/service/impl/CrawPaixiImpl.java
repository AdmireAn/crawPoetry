package com.erboss.service.impl;

import com.erboss.service.CrawPaixi;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author yonganwang
 * @date 2020/04/26
 **/
@Service
public class CrawPaixiImpl implements CrawPaixi {

    @Override
    public String crawPaixi() {
        String url = "https://www.aipiaxi.com/Index/post/id/34871";
        Set<String> contentSet = Sets.newConcurrentHashSet();
        List<String> contens = Lists.newArrayList();
        try {
            Document document = Jsoup.parse(new URL(url), 30000);
            Elements elements = document.getElementsByClass("k_content").get(0).getElementsByClass("content").get(0).getElementsByTag("p");
            for (Element element : elements) {
                contens.add("@@@");
                Elements spans = element.getElementsByTag("span");
                if(CollectionUtils.isEmpty(spans)){
                    continue;
                }else{
                    for (Element span : spans) {
                        String text = span.text().replace("　", "").replace(" ","").replace("&nbsp;","");
                        if(contentSet.contains(text)){
                            continue;
                        }
                        contens.add(text);
                        contentSet.add(text);
                    }
                }
            }

            for (String s : contens) {
                if(s.equals("@@@")){
                    System.out.println();
                    continue;
                }
                System.out.println(s);
            }
        } catch (Exception e) {

        }

        return Arrays.toString(contens.toArray());
    }
}
