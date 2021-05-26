package com.itplh.mybatis.config;

import com.itplh.mybatis.EnableMyBatis;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author: tanpenggood
 * @date: 2021-05-26 23:23
 */
@EnableMyBatis(
        dataSource = "dataSource",
        configLocation = "classpath:/mybatis/mybatis-config.xml",
        mapperLocations = {"classpath:/mybatis/mapper/*.xml"},
        environment = "development",
        plugins = {"com.github.pagehelper.PageInterceptor"},
        objectFactory = "com.itplh.mybatis.factory.MyObjectFactory",
        typeAliasesPackage = "com.itplh.mybatis.domain"
)
@MapperScan("com.itplh.mybatis.mapper")
@Configuration
public class MybatisConfig {
}
