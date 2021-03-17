package com.itplh.projects.user.logging.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 20:19
 */
public class UserWebLoggingConfigurationTest {

    private static final Logger logger = Logger.getLogger("com.itplh.projects.user.logging.demo");

    public UserWebLoggingConfigurationTest() throws UnsupportedEncodingException {
        System.out.println("UserWebLoggingConfigurationTest");
        Logger logger = Logger.getLogger("com.itplh.projects.user.logging.demo");
        // 通过代码的方式配置Handler
        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setEncoding("UTF-8");
        consoleHandler.setLevel(Level.WARNING);
        logger.addHandler(consoleHandler);
    }

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("META-INF/logging.properties")) {
            LogManager logManager = LogManager.getLogManager();
            // 读取日志配置
            logManager.readConfiguration(inputStream);
        }

        logger.fine("hello fine log.");
        logger.info("hello info log.");
        logger.warning("hello warning log.");
        logger.severe("hello severe log.");
    }

}
