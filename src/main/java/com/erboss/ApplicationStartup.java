package com.erboss;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by wangyongan on 2020-04-04.
 * Email wangyongan@xueqiu.com
 */
@EnableTransactionManagement//开启事务管理
@SpringBootApplication
@ComponentScan(basePackages="com.erboss.*")
@MapperScan("com.erboss.dao")
public class ApplicationStartup {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationStartup.class, args);
    }
}
