package com.itplh.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * 初始化器标记接口
 *
 * @author: tanpenggood
 * @date: 2021-03-24 00:50
 */
public interface MyApplicationInitializer {

    /**
     * 应用启动时做一些其他动作
     *
     * @param servletContext
     */
    void onStartup(ServletContext servletContext) throws ServletException;
}
