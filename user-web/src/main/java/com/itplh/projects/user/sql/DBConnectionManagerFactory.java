package com.itplh.projects.user.sql;

import com.itplh.web.context.JndiComponentContext;

/**
 * @author: tanpenggood
 * @date: 2021-03-03 20:54
 */
public class DBConnectionManagerFactory {

    private static final DBConnectionManagerFactory instance = new DBConnectionManagerFactory();

    private DBConnectionManagerFactory() {
    }

    public static DBConnectionManagerFactory getInstance() {
        return instance;
    }

    /**
     * 获取数据连接管理器
     *
     * @return
     */
    public DBConnectionManager getDBConnectionManager() {
        return JndiComponentContext.getInstance().getComponent(DBConnectionManager.class);
    }

}
