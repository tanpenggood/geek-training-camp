package com.itplh.projects.user.web.listener;

import com.itplh.projects.user.sql.DBConnectionManager;
import com.itplh.projects.user.sql.DBConnectionManagerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
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
            // JNDI 查找数据源
            Context ctx = new InitialContext();
            DataSource dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/UserPlatformDB");
            logger.info(String.format("使用JNDI读取数据源，完成 dataSource[%s]", dataSource.toString()));

            DBConnectionManagerFactory connectionManagerFactory = DBConnectionManagerFactory.getInstance();
            // 缓存数据源
            connectionManagerFactory.addDataSource(dataSource);
            logger.info("缓存数据源，完成");
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
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            Optional.ofNullable(dbConnectionManager).ifPresent(d -> d.releaseConnection());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
