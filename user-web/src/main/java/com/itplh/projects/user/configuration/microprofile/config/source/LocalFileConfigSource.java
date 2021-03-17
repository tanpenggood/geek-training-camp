package com.itplh.projects.user.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author: tanpenggood
 * @date: 2021-03-17 21:54
 */
public class LocalFileConfigSource implements ConfigSource {

    private Map<String, String> properties;

    public LocalFileConfigSource() throws IOException {
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties"));
        this.properties = new HashMap<>(properties.size() >> 2);
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            this.properties.put(entry.getKey().toString(), entry.getValue().toString());
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
        return "LocalFileConfigSource";
    }
}
