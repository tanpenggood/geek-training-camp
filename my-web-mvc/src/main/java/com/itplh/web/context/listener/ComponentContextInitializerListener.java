package com.itplh.web.context.listener;

import com.itplh.web.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * {@link ComponentContext} 初始化器
 * 类比Spring中ContextLoaderListener
 *
 * @author: tanpenggood
 * @date: 2021-03-10 01:39
 */
public class ComponentContextInitializerListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ComponentContext componentContext = new ComponentContext();
        componentContext.init(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
