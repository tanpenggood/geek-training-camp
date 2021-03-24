package com.itplh.projects.user.configuration.microprofile.config.source.servlet;

import com.itplh.projects.user.configuration.microprofile.config.source.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class ServletContextConfigSource extends MapBasedConfigSource {

    public ServletContextConfigSource() {
        super("Servlet Context Config", 400);
    }

    @Override
    protected void prepareConfigData(Map configData) throws Throwable {
        ServletContext servletContext = ServletConfigInitializer.servletContext;
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        while (initParameterNames.hasMoreElements()) {
            String propertyName = initParameterNames.nextElement();
            configData.put(propertyName, servletContext.getInitParameter(propertyName));
        }
    }

}
