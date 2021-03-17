package com.itplh.projects.user.configuration.microprofile.config.source;

import com.itplh.web.context.ComponentContext;
import org.eclipse.microprofile.config.spi.ConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class ServletContextConfigSource implements ConfigSource {

    private Map<String, String> properties;

    public ServletContextConfigSource() {
        ServletContext servletContext = ComponentContext.getInstance().getServletContext();
        Enumeration<String> initParameterNames = servletContext.getInitParameterNames();
        this.properties = new HashMap<>();
        while (initParameterNames.hasMoreElements()) {
            String propertyName = initParameterNames.nextElement();
            this.properties.put(propertyName, servletContext.getInitParameter(propertyName));
        }
    }

    @Override
    public Set<String> getPropertyNames() {
        return this.properties.keySet();
    }

    @Override
    public String getValue(String propertyName) {
        return this.properties.get(propertyName);
    }

    @Override
    public String getName() {
        return "Operation System Properties";
    }
}
