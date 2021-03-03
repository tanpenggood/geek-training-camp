package com.itplh.projects.user.sql;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tanpenggood
 * @date: 2021-03-03 20:54
 */
public class DBConnectionManagerFactory {

    /**
     * 默认数据源名称
     */
    public final String DEFAULT_DATASOURCE_NAME = "DEFAULT_DATASOURCE_NAME";

    /**
     * 数据源映射
     */
    private Map<String, DataSource> dataSourceMap = new HashMap<>();

    private static final DBConnectionManagerFactory instance = new DBConnectionManagerFactory();

    private DBConnectionManagerFactory() {
    }

    public static DBConnectionManagerFactory getInstance() {
        return instance;
    }

    /**
     * 添加默认数据源
     *
     * @param dataSource
     * @return
     */
    public DataSource addDataSource(DataSource dataSource) {
        return dataSourceMap.put(DEFAULT_DATASOURCE_NAME, dataSource);
    }

    /**
     * 添加数据源
     *
     * @param dataSourceName
     * @param dataSource
     * @return
     */
    public DataSource addDataSource(String dataSourceName, DataSource dataSource) {
        return dataSourceMap.put(dataSourceName, dataSource);
    }

    /**
     * 获取默认数据连接管理器
     *
     * @return
     */
    public DBConnectionManager getDBConnectionManager() {
        return getDBConnectionManager(DEFAULT_DATASOURCE_NAME);
    }

    /**
     * 获取数据连接管理器
     *
     * @param dataSourceName 数据源名称
     * @return
     */
    public DBConnectionManager getDBConnectionManager(String dataSourceName) {
        DataSource dataSource = dataSourceMap.get(dataSourceName);
        return getDBConnectionManager(dataSource);
    }

    /**
     * 获取数据连接管理器
     *
     * @param dataSource 数据源
     * @return
     */
    public DBConnectionManager getDBConnectionManager(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            DBConnectionManager dbConnectionManager = new DBConnectionManager();
            dbConnectionManager.setConnection(connection);
            return dbConnectionManager;
        } catch (SQLException e) {
            throw new RuntimeException(e.getCause());
        }
    }

}
