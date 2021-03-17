package com.itplh.projects.user.configuration.microprofile.config.source;

import com.itplh.web.context.ComponentContext;
import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class JndiConfigSource implements ConfigSource {

    private Map<String, String> properties;

    public JndiConfigSource() {
        ComponentContext componentContext = ComponentContext.getInstance();
        List<String> propertyNames = componentContext.listAllComponentNames(String.class);
        this.properties = new HashMap<>(propertyNames.size() >> 2);
        propertyNames.stream().forEach(propertyName -> this.properties.put(propertyName, componentContext.lookupComponent(propertyName)));
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
        return "JndiConfigSource";
    }
}
