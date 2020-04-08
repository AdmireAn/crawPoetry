package com.erboss.service.impl;

import com.erboss.Constant;
import com.erboss.dao.PoetryDao;
import com.erboss.domain.Poetry;
import com.erboss.service.PoetryService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Random;

/**
 * Created by wangyongan on 2020-04-05.
 * Email wangyongan@xueqiu.com
 */
@Service
public class PoetryServiceImpl implements PoetryService {

    @Autowired
    private PoetryDao poetryDao;

    /**
     * 随机返回一首诗
     * @return
     */
    @Override
    public Poetry getRandom(){
        Random df = new Random();
        Poetry poetry = null;
        int id;
        while (poetry == null){
            id = df.nextInt(950);
            poetry = poetryDao.selectById(id);
        }
        return poetry;
    }

    public String crawPoetry() {
        int count = 0;
        Poetry poetry = new Poetry();
        Document document = null;
        for (int i = 1; i < 20; i++) {
            String url = Constant.POETRY_URL + i;
            try {
                document = Jsoup.parse(new URL(url), 30000);
                Elements elements = document.getElementsByClass("sons").get(0).getElementsByTag("a");
                int index = elements.size();
                System.out.println(elements.size() / 2);
                count += elements.size() / 2;
                while (index > 0) {
                    index--;
                    String title = elements.get(index).text();
                    index--;
                    String content = elements.get(index).text();

                    poetry.setContent(content);
                    poetry.setTitle(title);
                    if (poetryDao.select(content) == null) {
                        poetryDao.insert(poetry);
                    }
                }
            } catch (Exception e) {
                System.out.println("异常了");
            }
        }
        System.out.println("count:" + count);
        return "";
    }
}
