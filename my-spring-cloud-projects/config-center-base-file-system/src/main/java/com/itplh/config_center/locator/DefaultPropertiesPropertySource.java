package com.itplh.config_center.locator;

import org.springframework.core.env.EnumerablePropertySource;

public class DefaultPropertiesPropertySource extends EnumerablePropertySource<DynamicResourceMessageSource> {

    public DefaultPropertiesPropertySource(String name, DynamicResourceMessageSource source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return this.source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return this.source.getMessage(name);
    }
}
