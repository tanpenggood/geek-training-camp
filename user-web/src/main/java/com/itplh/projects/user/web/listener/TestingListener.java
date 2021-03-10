package com.itplh.projects.user.web.listener;

import com.itplh.projects.user.domain.User;
import com.itplh.projects.user.orm.jpa.JpaDemo;
import com.itplh.projects.user.sql.DBConnectionManager;
import com.itplh.projects.user.sql.DBConnectionManagerFactory;
import com.itplh.web.context.ComponentContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * 测试通过ComponentContext进行依赖查找
 *
 * @author: tanpenggood
 * @date: 2021-03-10 01:49
 */
public class TestingListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // test get connection
        DBConnectionManager dbConnectionManager = DBConnectionManagerFactory.getInstance().getDBConnectionManager();
        dbConnectionManager.getConnection();
        // test jpa 操作数据库
        JpaDemo.testUser(dbConnectionManager.getEntityManager());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
