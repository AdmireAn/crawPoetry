package com.erboss.dao;

import com.erboss.domain.Poetry;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by wangyongan on 2020-04-05.
 * Email wangyongan@xueqiu.com
 */
@Mapper
public interface PoetryDao {

    void insert(Poetry poetry);

    Poetry selectById(int id);

    Poetry select(String content);
}
