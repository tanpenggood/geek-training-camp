package com.itplh.projects.user.web.listener;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MBean注册器
 * META-INF/mbean.properties
 * <class pull path>=<object name>
 *
 * @author: tanpenggood
 * @date: 2021-03-15 21:00
 */
public class RegisterMBeanListener implements ServletContextListener {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            registerMBean();
            logger.info("register mbean success.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "register mbean exception.", e);
        }
    }

    /**
     * 注册自定义MBean
     *
     * @throws Exception
     */
    private void registerMBean() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        InputStream inputStream = classLoader.getResourceAsStream("META-INF/mbean.properties");
        properties.load(inputStream);
        for (String propertyName : properties.stringPropertyNames()) {
            Object mbean = classLoader.loadClass(propertyName).newInstance();
            ObjectName objectName = new ObjectName(properties.getProperty(propertyName));
            server.registerMBean(mbean, objectName);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
