package com.itplh.projects.user.web.listener;

import com.itplh.projects.user.sql.DBConnectionManager;
import com.itplh.web.context.ComponentContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 测试通过ComponentContext进行依赖查找
 *
 * @author: tanpenggood
 * @date: 2021-03-10 01:49
 */
public class TestingListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DBConnectionManager dbConnectionManager = ComponentContext.getInstance().getComponent("bean/DBConnectionManager");
        dbConnectionManager.getConnection();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
