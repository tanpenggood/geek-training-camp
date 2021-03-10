package com.itplh.projects.user.web.listener;

import com.itplh.projects.user.sql.DBConnectionManager;
import com.itplh.projects.user.sql.DBConnectionManagerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.logging.Logger;

import static com.itplh.projects.user.sql.DBConnectionManager.CREATE_USERS_TABLE_DDL_SQL;
import static com.itplh.projects.user.sql.DBConnectionManager.DROP_USERS_TABLE_DDL_SQL;

public class DBConnectionInitializerListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DBConnectionManager dbConnectionManager = null;
        try {
            DBConnectionManagerFactory connectionManagerFactory = DBConnectionManagerFactory.getInstance();
            // 初始化数据库
            dbConnectionManager = connectionManagerFactory.getDBConnectionManager();
            Statement statement = dbConnectionManager.getConnection().createStatement();
            try {
                statement.execute(DROP_USERS_TABLE_DDL_SQL);
            } catch (SQLException e) {
                // ignore
            }
            statement.execute(CREATE_USERS_TABLE_DDL_SQL);
            logger.info("初始化数据库，完成");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(dbConnectionManager).ifPresent(d -> d.releaseConnection());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
