package com.itplh.projects.user.configuration.microprofile.config.source.servlet;

import com.itplh.initializer.MyApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author: tanpenggood
 * @date: 2021-03-21 00:26
 */
public class ServletConfigInitializer implements MyApplicationInitializer {

    public static ServletContext servletContext;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        ServletConfigInitializer.servletContext = servletContext;
    }
}
